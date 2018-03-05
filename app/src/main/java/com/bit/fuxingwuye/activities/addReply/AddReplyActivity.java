package com.bit.fuxingwuye.activities.addReply;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.addFaultRepair.FaultRepairActivity;
import com.bit.fuxingwuye.activities.community.CommunityActivity;
import com.bit.fuxingwuye.adapter.GridViewAddImgesAdpter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.MenuItem;
import com.bit.fuxingwuye.bean.ReplyBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityAddReplyBinding;
import com.bit.fuxingwuye.http.ProgressCancelListener;
import com.bit.fuxingwuye.http.ProgressDialogHandler;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.FileStorage;
import com.bit.fuxingwuye.views.BottomMenuFragment;
import com.bit.fuxingwuye.views.MenuItemOnClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddReplyActivity extends BaseActivity<AddReplyPresenterImpl> implements AddReplyContract.View,
        EasyPermissions.PermissionCallbacks, ProgressCancelListener {

    private ActivityAddReplyBinding mBinding;

    private List<Map<String, Object>> mdatas;
    private GridViewAddImgesAdpter mAdpter;
    private List<File> files;
    String[] camera = {Manifest.permission.CAMERA};
    private static final int RC_CAMERA = 126;
    private ProgressDialogHandler mProgressDialogHandler;
    ReplyBean replyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_reply);
        replyBean = new ReplyBean();
        replyBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
    }

    @Override
    protected void setupVM() {
        mBinding.toolbar.actionBarTitle.setText("发布新动态");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProgressDialogHandler = new ProgressDialogHandler(this, this, true, true);
        mdatas = new ArrayList<>();
        files = new ArrayList<>();
        mAdpter = new GridViewAddImgesAdpter(mdatas,this);
        mBinding.replyGv.setAdapter(mAdpter);
        mBinding.replyGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addBitmap();
            }
        });
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.etReplyContent.getText().toString().trim())) {
                    Toast.makeText(AddReplyActivity.this, "请描述故障情况", Toast.LENGTH_SHORT).show();
                    return;
                }
                replyBean.setContent(mBinding.etReplyContent.getText().toString().trim());
                if (mAdpter.getDatas().size()!=0){
                    Observable.create(new Observable.OnSubscribe<List<File>>() {
                        @Override
                        public void call(Subscriber<? super List<File>> subscriber) {
                            List<Bitmap> list = mAdpter.getDatas();
                            files.clear();
                            for (Bitmap bitmap : list) {
                                files.add(FileStorage.compressImage(bitmap));
                            }
                            subscriber.onNext(files);
                            subscriber.onCompleted();
                        }
                    })
                            .subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<List<File>>() {

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    if (mProgressDialogHandler != null) {
                                        mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
                                    }
                                }

                                @Override
                                public void onCompleted() {
                                    dismissProgressDialog();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    dismissProgressDialog();
                                }

                                @Override
                                public void onNext(List<File> files) {
                                    mPresenter.upload(files);
                                }
                            });
                }else {
                    mPresenter.addFault(replyBean);
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess() {
        for (File file:files){
            file.delete();
        }
        Intent it = new Intent();
        setResult(AppConstants.RES_REFRESH_INFO,it);
        finish();
    }

    @Override
    public void upload(List<String> urls) {
        List<String> imageBeanList = new ArrayList<>();
        for (String str : urls) {
            imageBeanList.add(str);
        }
            replyBean.setImgIds(imageBeanList);
            mPresenter.addFault(replyBean);

    }


    private void addBitmap() {
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("拍照");
        menuItem1.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem1) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!BaseApplication.getInstance().isFileEnalbe()) {
                        BaseApplication.getInstance().checkWriteReadEnable(AddReplyActivity.this);
                    } else {
                        takeCamera();
                    }
                } else {
                    takeCamera();
                }
            }
        });
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setText("从相册选择");
        menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                fromGallery();
            }
        });
        menuItemList.add(menuItem1);
        menuItemList.add(menuItem2);
        bottomMenuFragment.setMenuItems(menuItemList, "添加图片");

        bottomMenuFragment.show(this.getFragmentManager(), "BottomMenuFragment");

    }

    private void fromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    private void takeCamera() {
        if (!EasyPermissions.hasPermissions(this, camera)) {
            EasyPermissions.requestPermissions(this, "需要获取相机权限", RC_CAMERA, camera);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Map<String, Object> map = new HashMap<>();
            map.put("path", imageBitmap);
            mdatas.add(map);
            mAdpter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(data);
            } else {
                handleImageBeforeKitKat(data);
            }
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.provider.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String Path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return Path;
    }

    private void displayImage(String Path) {
        Bitmap bm = BitmapFactory.decodeFile(Path);
        Map<String, Object> map = new HashMap<>();
        map.put("path", bm);
        mdatas.add(map);
        mAdpter.notifyDataSetChanged();
    }

    @Override
    public void onCancelProgress() {

    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
