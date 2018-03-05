package com.bit.fuxingwuye.activities.payment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.databinding.ActivityPaymentBinding;
import com.bit.fuxingwuye.utils.ScannerUtils;

public class PaymentActivity extends BaseActivity<PaymentPresenterImpl> implements PaymentContract.View{

    private ActivityPaymentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_payment);
        mBinding.toolbar.actionBarTitle.setText("物业缴费");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager c = (android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText("610044134");
                Toast.makeText(PaymentActivity.this,"账号已复制",Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.ivQr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.qr);
                ScannerUtils.saveImageToGallery(PaymentActivity.this,bitmap);
                Toast.makeText(PaymentActivity.this,"二维码已保存到相册",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
