package com.BIT.fuxingwuye.activities.brake;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.databinding.ActivityBrakeBinding;

public class BrakeActivity extends BaseActivity<BrakePresenterImpl> implements BrakeContract.View, View.OnClickListener {

    private ActivityBrakeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brake);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_brake);
        mBinding.toolbar.actionBarTitle.setText("智能车闸");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.llBrake.setOnClickListener(this);
        mBinding.llCarcode.setOnClickListener(this);
        mBinding.btnOpen.setOnClickListener(this);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {

    }

    @Override
    public void openSuccess() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_brake:

                break;
            case R.id.ll_carcode:

                break;
            case R.id.btn_open:
                mPresenter.open();
                break;
        }
    }
}
