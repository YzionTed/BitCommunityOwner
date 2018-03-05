package com.bit.fuxingwuye.activities.goods;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.GoodsBean;
import com.bit.fuxingwuye.bean.MerchantBean;
import com.bit.fuxingwuye.databinding.ActivityGoodsBinding;
import com.bit.fuxingwuye.utils.ImageLoaderUtil;

public class GoodsActivity extends BaseActivity<GoodsPresenterImpl> implements GoodsContract.View {

    private ActivityGoodsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_goods);
    }

    @Override
    protected void setupVM() {
//        GoodsBean goodsBean = new GoodsBean();
//        goodsBean.setmId(getIntent().getStringExtra("mid"));
//        mPresenter.getGoods(goodsBean);

        mBinding.toolbar.actionBarTitle.setText("商户详情");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MerchantBean merchantBean = (MerchantBean)getIntent().getSerializableExtra("merchant");
        ImageLoaderUtil.setImageNotCache(merchantBean.getPhoto(),mBinding.ivMerchantIcon);
        mBinding.tvMerchantName.setText(merchantBean.getMerchantName());
        mBinding.tvMerchantType.setText(merchantBean.getOperateType());
        mBinding.tvMerchantAddress.setText(merchantBean.getAddress());
        mBinding.tvMerchantPhone.setText(merchantBean.getTelPhone());
        mBinding.tvMerchantIntroduce.setText(merchantBean.getIntroduce());
        mBinding.tvServiceIntroduce.setText(merchantBean.getServe());
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {

    }

    @Override
    public void showGoods() {

    }
}
