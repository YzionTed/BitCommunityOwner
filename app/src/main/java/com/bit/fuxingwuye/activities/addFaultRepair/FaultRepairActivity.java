package com.BIT.fuxingwuye.activities.addFaultRepair;

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
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.FaultCommitSuccessActivity;
import com.BIT.fuxingwuye.activities.chooseHouse.ChooseHouseActivity;
import com.BIT.fuxingwuye.activities.faultType.FaultTypeActivity;
import com.BIT.fuxingwuye.activities.myRepairList.MyRepairsActivity;
import com.BIT.fuxingwuye.adapter.GridViewAddImgesAdpter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.MenuItem;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityFaultRepairBinding;
import com.BIT.fuxingwuye.http.ProgressCancelListener;
import com.BIT.fuxingwuye.http.ProgressDialogHandler;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.FileStorage;
import com.BIT.fuxingwuye.views.BottomMenuFragment;
import com.BIT.fuxingwuye.views.MenuItemOnClickListener;

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

public class FaultRepairActivity extends BaseActivity<FaultRepairPresenterImpl> implements FaultRepairContract.View,
        View.OnClickListener, EasyPermissions.PermissionCallbacks, ProgressCancelListener {

    private ActivityFaultRepairBinding mBinding;
    private RepairBean repairBean1, repairBean2;
    private static final int PERSONAL_IV_PHOTO = 1;
    private static final int PERSONAL_IV_GALLERY = 2;
    private static final int PUBLIC_IV_PHOTO = 3;
    private static final int PUBLIC_IV_GALLERY = 4;
    private List<Map<String, Object>> personal_datas;
    private GridViewAddImgesAdpter personal_Adpter;
    private List<Map<String, Object>> public_datas;
    private GridViewAddImgesAdpter public_Adpter;
    private List<File> files;
    String[] camera = {Manifest.permission.CAMERA};
    private static final int RC_CAMERA = 125;
    private ProgressDialogHandler mProgressDialogHandler;
    String[] floorids;
    String[] floornames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fault_repair);
        mBinding.toolbar.actionBarTitle.setText("故障报修");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_record);
        mProgressDialogHandler = new ProgressDialogHandler(this, this, true, true);
        personal_datas = new ArrayList<>();
        public_datas = new ArrayList<>();
        files = new ArrayList<>();
        personal_Adpter = new GridViewAddImgesAdpter(personal_datas, this);
        public_Adpter = new GridViewAddImgesAdpter(public_datas, this);
        mBinding.personalGw.setAdapter(personal_Adpter);
        mBinding.publicGw.setAdapter(public_Adpter);
        mBinding.personalGw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                addBitmap(1);
            }
        });
        mBinding.publicGw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addBitmap(2);
            }
        });
    }

    @Override
    protected void setupVM() {
        repairBean1 = new RepairBean();
        repairBean1.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        repairBean1.setReportMan(ACache.get(this).getAsString(HttpConstants.USERNAME));
        repairBean1.setReportPhone(ACache.get(this).getAsString(HttpConstants.MOBILE));
        repairBean1.setRepairType(AppConstants.FAULT_PERSONAL);
        repairBean1.setFaultType(AppConstants.REPAIR_TYPE_WATER);
        repairBean2 = new RepairBean();
        repairBean2.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        repairBean2.setReportMan(ACache.get(this).getAsString(HttpConstants.USERNAME));
        repairBean2.setReportPhone(ACache.get(this).getAsString(HttpConstants.MOBILE));
        repairBean2.setRepairType(AppConstants.FAULT_PUBLIC);
        repairBean2.setFaultType(AppConstants.REPAIR_TYPE_ELEVATOR);

        mBinding.setRepairBean1(repairBean1);
        mBinding.setRepairBean2(repairBean2);

        if(null!=ACache.get(this).getAsObject(HttpConstants.USER)){
            List<UserBean.FloorMap> maps = ((UserBean)ACache.get(this).getAsObject(HttpConstants.USER)).getFloorInfo();

            if (maps.size()>0){
                floornames = new String[maps.size()];
                floorids = new String[maps.size()];
                for (int i=0;i<maps.size();i++){
                    floorids[i] = maps.get(i).getFloorId();
                    floornames[i] = maps.get(i).getAddress();
                }
                mBinding.tvPersonalAddress.setText(maps.get(0).getAddress());
                mBinding.getRepairBean1().setRepairAddress(floornames[0]);
                mBinding.getRepairBean1().setRepairAddressId(floorids[0]);
            }else{
                Toast.makeText(FaultRepairActivity.this,"请先绑定住房",Toast.LENGTH_SHORT).show();
            }

        }else{
            mBinding.tvPersonalAddress.setText("请先选择住房");
        }
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.personalAddress.setOnClickListener(this);
        mBinding.llPersonalType.setOnClickListener(this);
        mBinding.llPublicType.setOnClickListener(this);
        mBinding.btnCommit.setOnClickListener(this);
        mBinding.personalAddress.setOnClickListener(this);
        mBinding.toolbar.ivRightActionBar.setOnClickListener(this);

        mBinding.faultType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.fault_personal) {
                    mBinding.faultPart1.setVisibility(View.VISIBLE);
                    mBinding.faultPart2.setVisibility(View.GONE);
                } else if (checkedId == R.id.fault_public) {
                    mBinding.faultPart1.setVisibility(View.GONE);
                    mBinding.faultPart2.setVisibility(View.VISIBLE);
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
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess() {
        for (File file:files){
            file.delete();
        }
        startActivity(new Intent(FaultRepairActivity.this, FaultCommitSuccessActivity.class));
        finish();
    }

    @Override
    public void upload(List<String> urls) {
        List<String> imageBeanList = new ArrayList<>();
        for (String str : urls) {
            imageBeanList.add(str);
        }
        if (mBinding.faultPersonal.isChecked()) {
            repairBean1.setImgIds(imageBeanList);
            mPresenter.addFault(repairBean1);
        } else if (mBinding.faultPublic.isChecked()) {
            repairBean2.setImgIds(imageBeanList);
            mPresenter.addFault(repairBean2);
        }

    }

    @Override
    public void showFloors(UserBean userBean) {

        List<UserBean.FloorMap> maps = userBean.getFloorInfo();
        floornames = new String[maps.size()];
        floorids = new String[maps.size()];
        for (int i=0;i<maps.size();i++){
            floorids[i] = maps.get(i).getFloorId();
            floornames[i] = maps.get(i).getAddress();
        }
        mBinding.tvPersonalAddress.setText(maps.get(0).getAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_right_action_bar:
                startActivity(new Intent(FaultRepairActivity.this,MyRepairsActivity.class));
                break;
            case R.id.personal_address:
                startActivityForResult(
                        new Intent(FaultRepairActivity.this, ChooseHouseActivity.class)
                        , AppConstants.REQ_CHOOSE_HOUSE);
                break;
            case R.id.ll_personal_type:
                startActivityForResult(
                        new Intent(FaultRepairActivity.this, FaultTypeActivity.class)
                                .putExtra("type",AppConstants.FAULT_PERSONAL)
                        .putExtra("faulttype",mBinding.getRepairBean1().getFaultType())
                        ,AppConstants.REQ_PERSONAL_REPAIR);
                break;
            case R.id.ll_public_type:
                startActivityForResult(
                        new Intent(FaultRepairActivity.this, FaultTypeActivity.class)
                                .putExtra("type",AppConstants.FAULT_PUBLIC)
                                .putExtra("faulttype",mBinding.getRepairBean2().getFaultType())
                        ,AppConstants.REQ_PUBLIC_REPAIR);
                break;
            case R.id.btn_commit:
                if (mBinding.faultPersonal.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getRepairBean1().getRepairDescribe())) {
                        Toast.makeText(FaultRepairActivity.this, "请描述故障情况", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(mBinding.getRepairBean1().getRepairAddress())) {
                        Toast.makeText(FaultRepairActivity.this, "请先选择住房", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (personal_Adpter.getDatas().size() != 0) {
                        Observable.create(new Observable.OnSubscribe<List<File>>() {
                            @Override
                            public void call(Subscriber<? super List<File>> subscriber) {
                                List<Bitmap> list = personal_Adpter.getDatas();
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
                    } else {
                        mPresenter.addFault(repairBean1);
                    }

                } else if (mBinding.faultPublic.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getRepairBean2().getRepairAddress())) {
                        Toast.makeText(FaultRepairActivity.this, "请填写故障地点", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(mBinding.getRepairBean2().getRepairDescribe())) {
                        Toast.makeText(FaultRepairActivity.this, "请描述故障情况", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (public_Adpter.getDatas().size() != 0) {
                        Observable.create(new Observable.OnSubscribe<List<File>>() {
                            @Override
                            public void call(Subscriber<? super List<File>> subscriber) {
                                List<Bitmap> list = public_Adpter.getDatas();
                                files.clear();
                                for (Bitmap bitmap : list) {
                                    files.add(FileStorage.compressImage(bitmap));
                                }
                                subscriber.onNext(files);
                                subscriber.onCompleted();
                            }
                        })
                                .subscribeOn(Schedulers.io())
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
                    } else {
                        mPresenter.addFault(repairBean2);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConstants.RES_CHOOSE_HOUSE) {
            if (mBinding.faultPersonal.isChecked()) {
                mBinding.getRepairBean1().setRepairAddressId(data.getStringExtra("houseid"));
                mBinding.getRepairBean1().setRepairAddress(data.getStringExtra("housename"));
                mBinding.tvPersonalAddress.setText(data.getStringExtra("housename"));
            }
        }else if(resultCode == AppConstants.RES_PERSONAL_REPAIR){
            mBinding.getRepairBean1().setFaultType(data.getStringExtra("faulttype"));
            mBinding.personalFaultType.setText(mBinding.getRepairBean1().getFaultType());
        }else if(resultCode == AppConstants.RES_PUBLIC_REPAIR){
            mBinding.getRepairBean2().setFaultType(data.getStringExtra("faulttype"));
            mBinding.publicFaultType.setText(mBinding.getRepairBean2().getFaultType());
        } else if (requestCode == PERSONAL_IV_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Map<String, Object> map = new HashMap<>();
            map.put("path", imageBitmap);
            personal_datas.add(map);
            personal_Adpter.notifyDataSetChanged();
        } else if (requestCode == PERSONAL_IV_GALLERY && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(data, 1);
            } else {
                handleImageBeforeKitKat(data, 1);
            }
        } else if (requestCode == PUBLIC_IV_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Map<String, Object> map = new HashMap<>();
            map.put("path", imageBitmap);
            public_datas.add(map);
            public_Adpter.notifyDataSetChanged();
        } else if (requestCode == PUBLIC_IV_GALLERY && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(data, 2);
            } else {
                handleImageBeforeKitKat(data, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * --------------------------------------添加图片-----------------------------------------------
     **/
    private void addBitmap(final int code) {
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("拍照");
        menuItem1.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem1) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!BaseApplication.getInstance().isFileEnalbe()) {
                        BaseApplication.getInstance().checkWriteReadEnable(FaultRepairActivity.this);
                    } else {
                        if (code == 1) {
                            takeCamera(PERSONAL_IV_PHOTO);
                        } else if (code == 2) {
                            takeCamera(PUBLIC_IV_PHOTO);
                        }
                    }
                } else {
                    if (code == 1) {
                        takeCamera(PERSONAL_IV_PHOTO);
                    } else if (code == 2) {
                        takeCamera(PUBLIC_IV_PHOTO);
                    }
                }
            }
        });
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setText("从相册选择");
        menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                if (code == 1) {
                    fromGallery(PERSONAL_IV_GALLERY);
                } else if (code == 2) {
                    fromGallery(PUBLIC_IV_GALLERY);
                }
            }
        });
        menuItemList.add(menuItem1);
        menuItemList.add(menuItem2);
        bottomMenuFragment.setMenuItems(menuItemList, "添加图片");

        bottomMenuFragment.show(this.getFragmentManager(), "BottomMenuFragment");
    }

    private void takeCamera(int code) {
        if (!EasyPermissions.hasPermissions(this, camera)) {
            EasyPermissions.requestPermissions(this, "需要获取相机权限", RC_CAMERA, camera);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, code);
            }
        }
    }

    private void fromGallery(int code) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, code);
    }

    private void handleImageBeforeKitKat(Intent data, int code) {
        String imagePath = null;
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath, code);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, int code) {
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
        displayImage(imagePath, code);
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

    private void displayImage(String Path, int code) {
        Bitmap bm = BitmapFactory.decodeFile(Path);
        Map<String, Object> map = new HashMap<>();
        map.put("path", bm);
        if (code == 1) {
            personal_datas.add(map);
            personal_Adpter.notifyDataSetChanged();
        } else if (code == 2) {
            public_datas.add(map);
            public_Adpter.notifyDataSetChanged();
        }
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

    @Override
    public void onCancelProgress() {

    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**--------------------------------------添加图片-----------------------------------------------**/
}
