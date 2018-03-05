package com.bit.fuxingwuye.activities.aboutBit;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.databinding.ActivityAboutBitBinding;
import com.bit.fuxingwuye.utils.CommonUtils;

public class AboutBitActivity extends BaseActivity<AboutPresenterImpl> implements AboutContract.View {

    private ActivityAboutBitBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_bit);
        mBinding.toolbar.actionBarTitle.setText("关于");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
//        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_share);
        try {
            mBinding.tvVersion.setText("V" + CommonUtils.getVersionCode(AboutBitActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setupVM() {
//        Bitmap bitmap = CommonUtils.createQRImage("http://a.app.qq.com/o/simple.jsp?pkgname=com.bit.fuxingwuye",
//                400,400);
//        mBinding.ivQrcode.setImageBitmap(bitmap);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.ivRightActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
                share_intent.setType("text/plain");//设置分享内容的类型
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "府兴物业");//添加分享内容标题
                share_intent.putExtra(Intent.EXTRA_TEXT, "http://a.app.qq.com/o/simple.jsp?pkgname=com.bit.fuxingwuye");//添加分享内容
                share_intent = Intent.createChooser(share_intent, "分享府兴物业");
                startActivity(share_intent);
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

    @Override
    public void showQr(String path) {

    }
}
