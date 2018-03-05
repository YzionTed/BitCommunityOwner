package com.BIT.fuxingwuye.activities.personalEdit;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.BIT.communityOwner.model.OssToken;
import com.BIT.communityOwner.model.UserInfo;
import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.communityOwner.util.LogUtil;
import com.BIT.communityOwner.util.UploadUtils;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.base.BaseHandler;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.EditUserBean;
import com.BIT.fuxingwuye.bean.MenuItem;
import com.BIT.fuxingwuye.bean.TokenBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityPersonalEditBinding;
import com.BIT.fuxingwuye.http.ProgressCancelListener;
import com.BIT.fuxingwuye.http.ProgressDialogHandler;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.CommonUtils;
import com.BIT.fuxingwuye.utils.FileStorage;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;
import com.BIT.fuxingwuye.views.BottomMenuFragment;
import com.BIT.fuxingwuye.views.MenuItemOnClickListener;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PersonalEditActivity extends BaseActivity<PersonalEditPresenterImpl> implements PersonalEditContract.View,
        EasyPermissions.PermissionCallbacks, ProgressCancelListener {

    private static final String TAG = PersonalEditActivity.class.getSimpleName();

    private ActivityPersonalEditBinding mBinding;
    private TokenBean userBean;
    private EditUserBean editUserBean = new EditUserBean();
    private ACache mCache;
    private Timer timer;                  // 计时器
    private TimerTask timerTask;
    private int count = 60;                // 计时倒数 60s

    String[] camera = {Manifest.permission.CAMERA};
    private static final int RC_CAMERA = 126;
    private ProgressDialogHandler mProgressDialogHandler;
    private Bitmap bit_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_edit);
        mBinding.toolbar.actionBarTitle.setText("修改资料");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mCache = ACache.get(this);
        userBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
        if (null != userBean.getHeadImg() && !TextUtils.isEmpty(userBean.getHeadImg())) {
            ImageLoaderUtil.setImageWithCache(userBean.getHeadImg(), mBinding.ivHead);
        }
        editUserBean.setId(userBean.getId());
        switch (getIntent().getIntExtra("style", 1)) {
            case AppConstants.EDIT_PHOTO:
                mBinding.toolbar.actionBarTitle.setText("修改头像");
                mBinding.llName.setVisibility(View.GONE);
                mBinding.llSex.setVisibility(View.GONE);
                mBinding.llPhone.setVisibility(View.GONE);
                mBinding.llHead.setVisibility(View.VISIBLE);
                break;
            case AppConstants.EDIT_NAME:
                mBinding.toolbar.actionBarTitle.setText("修改姓名");
                mBinding.etName.setText(userBean.getName());
                mBinding.llName.setVisibility(View.VISIBLE);
                mBinding.llSex.setVisibility(View.GONE);
                mBinding.llPhone.setVisibility(View.GONE);
                mBinding.llHead.setVisibility(View.GONE);
                break;
            case AppConstants.EDIT_PHONE:
                mBinding.toolbar.actionBarTitle.setText("修改手机号");
                mBinding.llName.setVisibility(View.GONE);
                mBinding.llSex.setVisibility(View.GONE);
                mBinding.llPhone.setVisibility(View.VISIBLE);
                mBinding.llHead.setVisibility(View.GONE);
                break;
            case AppConstants.EDIT_SEX:
                mBinding.toolbar.actionBarTitle.setText("修改性别");
                if ("1".equals(userBean.getSex())) {
                    mBinding.rbMale.setChecked(true);
                    editUserBean.setSex(AppConstants.MALE + "");
                } else {
                    mBinding.rbFemale.setChecked(true);
                    editUserBean.setSex(AppConstants.FAMALE + "");
                }
                mBinding.llName.setVisibility(View.GONE);
                mBinding.llSex.setVisibility(View.VISIBLE);
                mBinding.llPhone.setVisibility(View.GONE);
                mBinding.llHead.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_male) {
                    editUserBean.setSex(AppConstants.MALE + "");
                } else if (checkedId == R.id.rb_female) {
                    editUserBean.setSex(AppConstants.FAMALE + "");
                }
            }
        });

        mBinding.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });

        mBinding.btnGetMobileVericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getInstance().checkPhoneEnable(PersonalEditActivity.this);
                if (CommonUtils.verifyPhone(mBinding.etMobile.getText().toString().trim())) {
                    mPresenter.getCode(new CodeBean(mBinding.etMobile.getText().toString().trim(), "2", 1));
                } else {
                    Toast.makeText(PersonalEditActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("style", 0) == AppConstants.EDIT_PHOTO) {
                    uploadImage();
                } else if (getIntent().getIntExtra("style", 0) == AppConstants.EDIT_NAME) {
                    if (TextUtils.isEmpty(mBinding.etName.getText().toString().trim())) {
                        Toast.makeText(PersonalEditActivity.this, "请输入新姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        editUserBean.setUserName(mBinding.etName.getText().toString().trim());
                    }
                    editUsername(editUserBean.getUserName());
                } else if (getIntent().getIntExtra("style", 0) == AppConstants.EDIT_PHONE) {
                    if (!CommonUtils.verifyPhone(mBinding.etMobile.getText().toString().trim())) {
                        Toast.makeText(PersonalEditActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(mBinding.etMobile.getText().toString().trim()) || TextUtils.isEmpty(mBinding.etCode.getText().toString()
                            .trim()) ||
                            TextUtils.isEmpty(mBinding.etPwd.getText().toString().trim())) {
                        Toast.makeText(PersonalEditActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editUserBean.setMobilePhone(mBinding.etMobile.getText().toString().trim());
                    editUserBean.setCode(mBinding.etCode.getText().toString().trim());
                    editUserBean.setPassword(CommonUtils.encryptData(mBinding.etPwd.getText().toString().trim()));
                    mPresenter.editUser(editUserBean);
                } else if (getIntent().getIntExtra("style", 0) == AppConstants.EDIT_SEX) {
//                    mPresenter.editUser(editUserBean);
                    if (mBinding.rbMale.isChecked()) {
                        editUserSex(1);
                    } else {
                        editUserSex(0);
                    }
                }
            }
        });
    }

    private void editUserSex(final int sex) {
        Api.editUserSex(sex, new ResponseCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                editUserSexSuccess(sex);
            }

            @Override
            public void onFailure(ServiceException e) {
                toastMsg(e.getMsg());
            }
        });
    }


    private void editUsername(final String name) {
        Api.editUsername(name, new ResponseCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                toastMsg("修改成功");
                editNameSuccess(name);
            }

            @Override
            public void onFailure(ServiceException e) {
                toastMsg(e.getMsg());
            }
        });
    }

    private void editUserSexSuccess(int sex) {
        TokenBean tokenBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
        tokenBean.setSex(String.valueOf(sex));
        mCache.put(HttpConstants.TOKENBEAN, tokenBean);
        Intent it = new Intent();
        it.setAction("com.data.refreshUser");
        sendBroadcast(it);
        setResult(AppConstants.RES_REFRESH_INFO, it);
        finish();
    }

    private void editNameSuccess(String name) {
        TokenBean tokenBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
        tokenBean.setName(name);
        mCache.put(HttpConstants.TOKENBEAN, tokenBean);
        Intent it = new Intent();
        it.setAction("com.data.refreshUser");
        sendBroadcast(it);
        setResult(AppConstants.RES_REFRESH_INFO, it);
        finish();
    }

    String imageUrl;

    private void uploadImage() {
        if (bit_head == null) {
            Toast.makeText(PersonalEditActivity.this, "请先选择头像", Toast.LENGTH_SHORT).show();
        } else {
            final File file = FileStorage.compressImage(bit_head);
            Api.ossToken(new ResponseCallBack<OssToken>() {
                @Override
                public void onSuccess(final OssToken data) {
                    UploadUtils.uploadFileToAliYun(data, file.getPath(), new OSSProgressCallback<PutObjectRequest>() {
                        @Override
                        public void onProgress(PutObjectRequest putObjectRequest, long p, long t) {
                            if (t == p) {
                                imageUrl = data.getPath();
                                LogUtil.d(TAG, "upload finished imageUrl:" + imageUrl);
                                if (imageUrl != null) {
                                    uploadImageInfo();
                                }
                            }
                        }
                    });
                }

                @Override
                public void onFailure(ServiceException e) {
                    toastMsg(e.getMsg());
                }
            });

//            Observable.create(new Observable.OnSubscribe<File>() {
//                @Override
//                public void call(Subscriber<? super File> subscriber) {
//                    File file = FileStorage.compressImage(bit_head);
//                    subscriber.onNext(file);
//                    subscriber.onCompleted();
//                }
//            })
//                    .subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<File>() {
//
//                        @Override
//                        public void onStart() {
//                            super.onStart();
//                            if (mProgressDialogHandler != null) {
//                                mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//                            }
//                        }
//
//                        @Override
//                        public void onCompleted() {
//                            dismissProgressDialog();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            dismissProgressDialog();
//                        }
//
//                        @Override
//                        public void onNext(File file) {
//                            mPresenter.upload(file);
//                        }
//                    });
        }
    }

    private void uploadImageInfo() {
        Api.editHeadUrl(imageUrl, new ResponseCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                toastMsg("修改成功");
                editHeadImageSuccess(imageUrl);
            }

            @Override
            public void onFailure(ServiceException e) {
                toastMsg(e.getMsg());
            }
        });
    }


    private void addPhoto() {
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("拍照");
        menuItem1.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem1) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!BaseApplication.getInstance().isFileEnalbe()) {
                        BaseApplication.getInstance().checkWriteReadEnable(PersonalEditActivity.this);
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
        bottomMenuFragment.setMenuItems(menuItemList, "选择头像");

        bottomMenuFragment.show(this.getFragmentManager(), "BottomMenuFragment");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }


    private void editHeadImageSuccess(String imageUrl) {
        TokenBean tokenBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
        tokenBean.setHeadImg(imageUrl);
        mCache.put(HttpConstants.TOKENBEAN, tokenBean);
        Intent it = new Intent();
        it.setAction("com.data.refreshUser");
        sendBroadcast(it);
        setResult(AppConstants.RES_REFRESH_INFO, it);
        finish();
    }

    @Override
    public void editSuccess(UserBean userBean) {
        mCache.put(HttpConstants.USER, userBean);
        Intent it = new Intent();
        it.setAction("com.data.refreshUser");
        sendBroadcast(it);
        setResult(AppConstants.RES_REFRESH_INFO, it);
        finish();
    }

    @Override
    public void runTimerTask() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.schedule(timerTask, 200, 1000);
    }

    @Override
    public void unableTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        String name = "<html><body><u>" + "<font  color='#fc4633'>" + "重新获取"
                + "</u></font>" + "</body></html>";
        mBinding.btnGetMobileVericode.setText(Html.fromHtml(name));
        mBinding.btnGetMobileVericode.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnGetMobileVericode.setClickable(true);
        count = 60;
    }

    @Override
    public void upload(List<String> urls) {
        editUserBean.setImgId(urls.get(0));
        mPresenter.editUser(editUserBean);
    }

    @Override
    protected void onDestroy() {
        unableTimerTask();
        super.onDestroy();
    }

    /**
     * 计时任务对象
     */
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mHandler != null)
                mHandler.sendEmptyMessage(0);
        }
    }

    private Handler mHandler = new BaseHandler(this) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PersonalEditActivity activity = (PersonalEditActivity) act.get();
            if (activity.count == 0) {
                unableTimerTask();                  // 取消计时任务
            } else {
                // 设置重新获取验证码为计时状态
                mBinding.btnGetMobileVericode.setTextColor(getResources().getColor(R.color.colorP9));
                mBinding.btnGetMobileVericode.setText(activity.count + "S后重新获取");
                mBinding.btnGetMobileVericode.setClickable(false);
                activity.count--;
            }
        }
    };

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
            bit_head = imageBitmap;
            mBinding.ivHead.setImageBitmap(imageBitmap);
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
        bit_head = bm;
        mBinding.ivHead.setImageBitmap(bm);
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
}
