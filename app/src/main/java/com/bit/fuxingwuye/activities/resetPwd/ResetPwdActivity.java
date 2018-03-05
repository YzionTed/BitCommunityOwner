package com.BIT.fuxingwuye.activities.resetPwd;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.base.BaseHandler;
import com.BIT.fuxingwuye.bean.ResetPwdBean;
import com.BIT.fuxingwuye.databinding.ActivityResetPwdBinding;
import com.BIT.fuxingwuye.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPwdActivity extends BaseActivity<ResetPwdPresenterImpl> implements ResetPwdContract.View {

    private ActivityResetPwdBinding mBinding;
    private Timer timer;                  // 计时器
    private TimerTask timerTask;
    private int count = 60;                // 计时倒数 60s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd);
        ResetPwdBean resetPwdBean = new ResetPwdBean();
        resetPwdBean.setChangeType("0");
        mBinding.setBean(resetPwdBean);
        mBinding.toolbar.actionBarTitle.setText("重置密码");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rgResetType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mBinding.getBean().setPassword("");
                mBinding.getBean().setOld_password("");
                mBinding.getBean().setCode("");
                if (checkedId == R.id.add_pwd) {
                    mBinding.rlCode.setVisibility(View.VISIBLE);
                    mBinding.llOldpwd.setVisibility(View.GONE);
                    mBinding.getBean().setChangeType("0");
                } else if (checkedId == R.id.reset_code) {
                    mBinding.rlCode.setVisibility(View.GONE);
                    mBinding.llOldpwd.setVisibility(View.VISIBLE);
                    mBinding.getBean().setChangeType("1");
                }
            }
        });

        mBinding.btnGetMobileVericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getInstance().checkPhoneEnable(ResetPwdActivity.this);
                if (CommonUtils.verifyPhone(mBinding.getBean().getMobilePhone())) {
//                    mPresenter.getCode(new CodeBean(mBinding.getBean().getMobilePhone(), "2", 1));
                    getCode();
                } else {
                    Toast.makeText(ResetPwdActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.verifyPhone(mBinding.getBean().getMobilePhone())) {
                    Toast.makeText(ResetPwdActivity.this, "请检查手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBinding.addPwd.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getBean().getMobilePhone()) || TextUtils.isEmpty(mBinding.getBean().getCode()) ||
                            TextUtils.isEmpty(mBinding.getBean().getPassword())) {
                        Toast.makeText(ResetPwdActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    mBinding.getBean().setPassword(CommonUtils.encryptData(mBinding.getBean().getPassword()));
                } else if (mBinding.resetCode.isChecked()) {
                    if (TextUtils.isEmpty(mBinding.getBean().getMobilePhone()) || TextUtils.isEmpty(mBinding.getBean().getOld_password()) ||
                            TextUtils.isEmpty(mBinding.getBean().getPassword())) {
                        Toast.makeText(ResetPwdActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mBinding.getBean().setPassword(mBinding.getBean().getPassword());
                    mBinding.getBean().setOld_password(mBinding.getBean().getOld_password());
                }
//                mPresenter.resetPwd(mBinding.getBean());
                resetPassword(mBinding.getBean());
            }
        });
    }

    private void resetPassword(ResetPwdBean bean) {
        Api.resetPassword(bean.getMobilePhone(), bean.getCode(), bean.getPassword(), new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                toastMsg("重置密码成功");
                onBackPressed();
            }

            @Override
            public void onFailure(ServiceException e) {
                toastMsg(e.getMsg());
            }
        });
    }

    private void getCode() {
        Api.getVerfriyCode(mBinding.getBean().getMobilePhone(), 3, new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                toastMsg("发送成功");
                runTimerTask();
            }

            @Override
            public void onFailure(ServiceException e) {
                toastMsg(e.getMsg());
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
    public void resetSuccess(String s) {
        Toast.makeText(this, s + "", Toast.LENGTH_SHORT).show();
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
            ResetPwdActivity activity = (ResetPwdActivity) act.get();
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
