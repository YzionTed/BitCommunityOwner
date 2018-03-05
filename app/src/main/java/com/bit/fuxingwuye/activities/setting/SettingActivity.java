package com.bit.fuxingwuye.activities.setting;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.resetPwd.ResetPwdActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity<SettingPresenterImpl> implements SettingContract.View{

    private ActivitySettingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
        mBinding.toolbar.actionBarTitle.setText("设置");
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
        mBinding.rlChangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,ResetPwdActivity.class));
            }
        });
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.getInstance().exitApp();
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {

    }
}
