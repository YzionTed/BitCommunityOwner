package com.bit.fuxingwuye.activities.payContent;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.databinding.ActivityPayContentBinding;

public class PayContentActivity extends BaseActivity<PayContentPresenterImpl> implements PayContentContract.View {

    private ActivityPayContentBinding mBinding;
    private String expensesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_pay_content);
        if (getIntent().getIntExtra("type",1)==1){
            mBinding.toolbar.actionBarTitle.setText("待缴物业费明细");
            mBinding.llUnpay.setVisibility(View.VISIBLE);
        }else if (getIntent().getIntExtra("type",1)==2){
            mBinding.toolbar.actionBarTitle.setText("缴费清单");
            mBinding.llPayed.setVisibility(View.VISIBLE);
        }

        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        expensesId = getIntent().getStringExtra("id");
        CommonBean commonBean = new CommonBean();
        commonBean.setExpensesId(expensesId);
        mPresenter.getPayContent(commonBean);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
