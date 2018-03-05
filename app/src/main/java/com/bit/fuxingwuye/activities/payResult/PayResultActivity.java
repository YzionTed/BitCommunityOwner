package com.BIT.fuxingwuye.activities.payResult;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.payment.PaymentActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.databinding.ActivityPayResultBinding;

public class PayResultActivity extends BaseActivity<PayResultPresenterImpl> implements PayResultContract.View {

    private ActivityPayResultBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_pay_result);
        mBinding.toolbar.actionBarTitle.setText("缴费结果");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupHandlers() {
        mBinding.btnPaySuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.btnRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PayResultActivity.this,PaymentActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void setupVM() {
        if(TextUtils.isEmpty(getIntent().getStringExtra("money"))){
            mBinding.ivPayResult.setImageResource(R.mipmap.u318);
            mBinding.tvPayResult.setText("缴费失败");
            mBinding.tvPayResason.setText("");
            mBinding.tvPayAmount.setText("");
            mBinding.llPayFail.setVisibility(View.VISIBLE);
            mBinding.btnPaySuccess.setVisibility(View.GONE);
        }else{
            mBinding.ivPayResult.setImageResource(R.mipmap.u350);
            mBinding.tvPayResult.setText("缴费成功");
            mBinding.tvPayResason.setText("成功缴纳物业管理费");
            mBinding.tvPayAmount.setText("￥"+getIntent().getStringExtra("money"));
            mBinding.llPayFail.setVisibility(View.GONE);
        }
    }

    @Override
    public void toastMsg(String msg) {

    }
}
