package com.bit.fuxingwuye.activities.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bit.communityOwner.KeyString;
import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.activities.register.RegisterActivity;
import com.bit.fuxingwuye.activities.resetPwd.ResetPwdActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseHandler;
import com.bit.fuxingwuye.bean.CodeBean;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.LoginBean;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.VersionBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityLoginBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.CommonUtils;
import com.bit.fuxingwuye.utils.OssManager;
import com.bit.fuxingwuye.utils.SPUtils;
import com.ddclient.configuration.DongConfiguration;
import com.ddclient.dongsdk.PushInfo;
import com.ddclient.jnisdk.InfoUser;
import com.ddclient.push.DongPushMsgManager;
import com.gViewerX.util.LogUtils;
import com.smarthome.yunintercom.sdk.AbstractIntercomCallbackProxy;
import com.smarthome.yunintercom.sdk.IntercomSDK;
import com.smarthome.yunintercom.sdk.IntercomSDKProxy;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.qqtheme.framework.AppConfig;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.View,
        EasyPermissions.PermissionCallbacks {
    ACache aCache;
    private ActivityLoginBinding mBinding;
    private ProgressDialog pdialog;
    private ACache mCache;
    private Timer timer;                  // 计时器
    private TimerTask timerTask;
    private int count = 60;                // 计时倒数 60s
    private static final int RC_READ_WRITE = 120;
    private static final int RC_PHONE = 121;
    String[] readwrite = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] phone = {Manifest.permission.READ_PHONE_STATE};
    private LoginActivityIntercomAccountProxy mIntercomAccountProxy = new LoginActivityIntercomAccountProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EasyPermissions.hasPermissions(this, readwrite)) {
            EasyPermissions.requestPermissions(this, "需要获取文件读写权限", RC_READ_WRITE, readwrite);
        }
        mCache.remove(HttpConstants.TOKEN);
        mCache.remove(HttpConstants.USERID);
        //初始化推送设置
        IntercomSDK.initializePush(this, DongPushMsgManager.PUSH_TYPE_GETUI);
        //IntercomSDK.initializePush(this, DongPushMsgManager.PUSH_TYPE_JG);

        //云对讲
        boolean initIntercomAccount = IntercomSDKProxy.initCompleteIntercomAccount();
        if (!initIntercomAccount)
            IntercomSDKProxy.initIntercomAccount(mIntercomAccountProxy);
    }

    /**
     *
     */
    @Override
    protected void initEventAndData() {
        aCache = ACache.get(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.toolbar.btnBack.setVisibility(View.GONE);
        mCache = ACache.get(this);
        LoginBean loginBean = new LoginBean();
        loginBean.setVerityType("1");

        loginBean.setPhone(SPUtils.getInstance().getString(HttpConstants.PHONE,""));
        loginBean.setPwd(SPUtils.getInstance().getString(HttpConstants.PASSWORD,""));
        mBinding.setBean(loginBean);
        mBinding.toolbar.actionBarTitle.setText("登录");
    }


    @Override
    protected void setupVM() {
        CommonBean commonBean = new CommonBean();
        try {
            commonBean.setVersion(CommonUtils.getVersionName(LoginActivity.this) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mPresenter.checkUpgrade(commonBean);
    }

    @Override
    protected void setupHandlers() {
        mBinding.setActionHandler(mPresenter);
        mBinding.rgLoginType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_pwd) {
                    mBinding.tvForgetPwd.setVisibility(View.VISIBLE);
                    mBinding.llPwd.setVisibility(View.VISIBLE);
                    mBinding.rlVericode.setVisibility(View.GONE);
                    mBinding.getBean().setVerityType("1");
                } else if (checkedId == R.id.rb_code) {
                    mBinding.tvForgetPwd.setVisibility(View.GONE);
                    mBinding.llPwd.setVisibility(View.GONE);
                    mBinding.rlVericode.setVisibility(View.VISIBLE);
                    mBinding.getBean().setVerityType("0");
                }
            }
        });
        mBinding.btnGetMobileVericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(LoginActivity.this, phone)) {
                    EasyPermissions.requestPermissions(LoginActivity.this, "需要获取短信权限", RC_PHONE, phone);
                } else {
                    if (CommonUtils.verifyPhone(mBinding.getBean().getPhone())) {
                        mPresenter.getCode(new CodeBean(mBinding.getBean().getPhone(), "1", 2));
                    } else {
                        Toast.makeText(LoginActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mBinding.btnLoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.verifyPhone(mBinding.getBean().getPhone())) {
                    Toast.makeText(LoginActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBinding.rbPwd.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getBean().getPhone()) || TextUtils.isEmpty(mBinding.getBean().getPwd())) {
                        Toast.makeText(LoginActivity.this, "请填写账号密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mBinding.getBean().setPwd(mBinding.getBean().getPwd());
                } else if (mBinding.rbCode.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getBean().getPhone()) || TextUtils.isEmpty(mBinding.getBean().getCode())) {
                        Toast.makeText(LoginActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                JPushInterface.init(getApplicationContext());
                mPresenter.login(mBinding.getBean(), LoginActivity.this);

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

    /**
     * @param tokenBean
     * 登录成功返回的数据
     */
    @Override
    public void loginSuccess(TokenBean tokenBean) {
        mCache.put(HttpConstants.TOKENBEAN, tokenBean);
        if(mBinding.getBean().getPhone()!=null){
            mCache.put(HttpConstants.MOBILE, mBinding.getBean().getPhone());
        }
        if(tokenBean.getToken()!=null){
            mCache.put(HttpConstants.TOKEN, tokenBean.getToken());
        }
        if(tokenBean.getId()!=null){
            mCache.put(HttpConstants.USERID, tokenBean.getId());
        }
        if(tokenBean.getNickName()!=null){
            mCache.put(HttpConstants.NICKNAME, tokenBean.getNickName());
        }
        if(tokenBean.getPhone()!=null){
            mCache.put(HttpConstants.PHONE, tokenBean.getPhone());
        }
       if(tokenBean.getName()!=null){
           mCache.put(HttpConstants.USERNAME, tokenBean.getName());
       }

        mCache.put(HttpConstants.STATUS, "3");
        SPUtils.getInstance().put(HttpConstants.PHONE,mBinding.etMobile.getText().toString());
        SPUtils.getInstance().put(HttpConstants.PASSWORD,mBinding.etPwd.getText().toString());
        SPUtils.getInstance().put(HttpConstants.USERID,tokenBean.getId());
        initOssToken();
        Intent intent = null;
        if (aCache.getAsString(HttpConstants.village) != null && !"".equals(aCache.getAsString(HttpConstants.village))) {
            intent = new Intent(LoginActivity.this, MainTabActivity.class);
        } else {
            intent = new Intent(this, Housing.class);
        }
        if(getIntent().getBundleExtra(KeyString.EXTRA_BUNDLE) != null){
            intent.putExtra(KeyString.EXTRA_BUNDLE, getIntent().getBundleExtra(KeyString.EXTRA_BUNDLE));
        }
        startActivity(intent);
        finish();

    }

    private void initOssToken() {
        Api.ossToken(new ResponseCallBack<OssToken>() {
            @Override
            public void onSuccess(OssToken data) {
                super.onSuccess(data);
                if (data!=null){
                    OssManager.getInstance().init(BaseApplication.getInstance(), data);
                    aCache.put(HttpConstants.OSSTOKEN, data);
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });
    }

    @Override
    public void hasUpgrade(final VersionBean versionBean) {
        BaseApplication.getInstance().checkWriteReadEnable(LoginActivity.this);
        if (versionBean.getUpdateType().equals("1")) {
            new LemonHelloInfo().setTitle("发现新版本")
                    .setContent("是否下载最新版本？")
                    .addAction(new LemonHelloAction("暂不更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    }))
                    .addAction(new LemonHelloAction("马上更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                            mPresenter.downLoad(LoginActivity.this, versionBean.getUrl() + "?versionId=" + versionBean.getVersionId());
                        }
                    }))
                    .show(LoginActivity.this);
        } else if (versionBean.getUpdateType().equals("2")) {
            new LemonHelloInfo().setTitle("发现新版本")
                    .setContent("此次版本为强制更新？")
                    .addAction(new LemonHelloAction("马上更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                            mPresenter.downLoad(LoginActivity.this, versionBean.getUrl() + "?versionId=" + versionBean.getVersionId());
                        }
                    }))
                    .show(LoginActivity.this);
        }
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
    public void initProgressDialog() {
        pdialog = new ProgressDialog(this);
        pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pdialog.setCancelable(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setTitle("提示");
        pdialog.setMessage("正在下载中，请耐心等待...");
        pdialog.setMax(100);
        pdialog.show();
    }

    /**
     * 下载进度条
     */
    @Override
    public void showProgressDialog(int i) {
        pdialog.setProgress(i);
        if (i == 100) {
            pdialog.dismiss();
        }
    }

    @Override
    public void gotoRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void gotoReset() {
        startActivity(new Intent(this, ResetPwdActivity.class));
    }

    @Override
    public void emchat() {

    }

    @Override
    protected void onDestroy() {
        unableTimerTask();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case RC_PHONE:
                if (CommonUtils.verifyPhone(mBinding.getBean().getPhone())) {
                    mPresenter.getCode(new CodeBean(mBinding.getBean().getPhone(), "1", 2));
                } else {
                    Toast.makeText(LoginActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case RC_READ_WRITE:
                Log.e("read", "granted");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent=  new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
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
            LoginActivity activity = (LoginActivity) act.get();
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

    private class LoginActivityIntercomAccountProxy extends AbstractIntercomCallbackProxy.IntercomAccountCallbackImp {

        @Override
        public int onAuthenticate(InfoUser tInfo) {
            IntercomSDKProxy.requestSetPushInfo(3);
            DongConfiguration.mUserInfo = tInfo;
            LogUtils.e("LoginActivity.clazz--->>>onAuthenticate........tInfo:"
                    + tInfo);
            IntercomSDKProxy.requestSetPushInfo(PushInfo.PUSHTYPE_FORCE_ADD);
            IntercomSDKProxy.requestGetDeviceListFromPlatform();
         /*   startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
            LoginActivity.this.finish();*/
            return 0;
        }

        @Override
        public int onUserError(int nErrNo) {
            LogUtils.e("LoginActivity.clazz--->>>onUserError........nErrNo:"
                    + nErrNo);
            Toast.makeText(LoginActivity.this, "登录对讲系统失败", Toast.LENGTH_SHORT).show();
//            IntercomSDK.reInitIntercomSDK();
            return 0;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntercomSDKProxy.registerIntercomAccountCallback(mIntercomAccountProxy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IntercomSDKProxy.unRegisterIntercomAccountCallback(mIntercomAccountProxy);
    }
}
