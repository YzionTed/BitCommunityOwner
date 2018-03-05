package com.BIT.fuxingwuye.activities.register;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.TipsActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.base.BaseHandler;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.RegisterBean;
import com.BIT.fuxingwuye.bean.TokenBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityRegisterBinding;
import com.BIT.fuxingwuye.http.DialogUltis;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity<RegisterPresenterImpl> implements RegisterContract.View{

    private ActivityRegisterBinding mBinding;
    private Timer timer;                  // 计时器
    private TimerTask timerTask;
    private int count = 60;                // 计时倒数 60s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        RegisterBean registerBean = new RegisterBean();
        mBinding.setBean(registerBean);
        mBinding.toolbar.actionBarTitle.setText("注册");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupHandlers() {
        mBinding.btnGetMobileVericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getInstance().checkPhoneEnable(RegisterActivity.this);
                if(CommonUtils.verifyPhone(mBinding.getBean().getMobilePhone())){
                   // mPresenter.getCode(new CodeBean(mBinding.getBean().getMobilePhone(),"0",1));
                    DialogUltis.showDialog(getSupportFragmentManager(),"获取验证码");
                    Api.getVerfriyCode(mBinding.getBean().getMobilePhone(), 1, new ResponseCallBack<String>() {
                        @Override
                        public void onSuccess(String data) {
                            DialogUltis.closeDialog();
                            super.onSuccess(data);
                        }

                        @Override
                        public void onFailure(ServiceException e) {
                            DialogUltis.closeDialog();
                            super.onFailure(e);
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this,"请检查手机号",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(RegisterActivity.this,ReplenishDataActivity.class));
                if(TextUtils.isEmpty(mBinding.getBean().getMobilePhone())||TextUtils.isEmpty(mBinding.getBean().getCode())){
                    Toast.makeText(RegisterActivity.this,"请填写基本信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mBinding.getBean().getPassword())){
                    mBinding.getBean().setVerityType("0");
                }else{
                    mBinding.getBean().setVerityType("1");
                    mBinding.getBean().setPassword(mBinding.getBean().getPassword());
                }

                mBinding.getBean().setPassword(CommonUtils.encryptData(mBinding.getBean().getPassword()));
                mPresenter.register(mBinding.getBean());
            }
        });
        mBinding.btnGotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rbTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,TipsActivity.class));
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
    public void registerSuccess(TokenBean tokenBean) {

        Toast.makeText(RegisterActivity.this,"操作成功",Toast.LENGTH_LONG).show();
        ACache mCache = ACache.get(this);
        mCache.clear();
        mCache.put(HttpConstants.TOKENBEAN, tokenBean);
        mCache.put(HttpConstants.MOBILE,mBinding.getBean().getMobilePhone());
        mCache.put(HttpConstants.USERID,tokenBean.getId());
        mCache.put(HttpConstants.TOKEN,tokenBean.getToken());
        //设置极光别名
//        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//        tagAliasBean.action = 2;
//        sequence++;
//        tagAliasBean.alias = commonBean.getUserId();
//        tagAliasBean.isAliasAction = true;
//        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);

        startActivity(new Intent(RegisterActivity.this,RegisterSuccess.class));
        finish();
    }

    @Override
    public void runTimerTask() {
        InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null){
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
        // 设置重新获取验证码为可点击状态
//        String name = "<html><body><u>" + "<font  color='#FFFFFF'>" + "重新获取"
//                + "</u></font>" + "</body></html>";
        mBinding.btnGetMobileVericode.setText("重新获取");
        mBinding.btnGetMobileVericode.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnGetMobileVericode.setClickable(true);
        count = 60;
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
            RegisterActivity activity = (RegisterActivity) act.get();
            if (activity.count == 0) {
                unableTimerTask();                  // 取消计时任务
            } else {
                // 设置重新获取验证码为计时状态
                mBinding.btnGetMobileVericode.setTextColor(getResources().getColor(R.color.white));
                mBinding.btnGetMobileVericode.setText(activity.count + "S");
                mBinding.btnGetMobileVericode.setClickable(false);

                activity.count--;
            }
        }
    };
}
