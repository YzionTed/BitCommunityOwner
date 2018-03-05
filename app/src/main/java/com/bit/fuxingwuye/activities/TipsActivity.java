package com.BIT.fuxingwuye.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.databinding.ActivityTipsBinding;

public class TipsActivity extends AppCompatActivity {

    private ActivityTipsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_tips);
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.actionBarTitle.setText("用户协议及隐私条款");
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
