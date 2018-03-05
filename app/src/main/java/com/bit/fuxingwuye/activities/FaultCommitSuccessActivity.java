package com.BIT.fuxingwuye.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.myRepairList.MyRepairsActivity;
import com.BIT.fuxingwuye.databinding.ActivityFaultCommitSuccessBinding;

public class FaultCommitSuccessActivity extends AppCompatActivity {

    private ActivityFaultCommitSuccessBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_fault_commit_success);
        mBinding.toolbar.actionBarTitle.setText("提交成功");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.btnCommitSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaultCommitSuccessActivity.this, MyRepairsActivity.class));
                finish();
            }
        });
    }
}
