package com.BIT.fuxingwuye.activities.commitSuccess;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.MainTabActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.databinding.ActivityCommitSuccessBinding;

public class CommitSuccessActivity extends BaseActivity<CSPresenterImpl> implements CSContract.View{

    private ActivityCommitSuccessBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_commit_success);
        mBinding.setActionhandler(mPresenter);
        mBinding.toolbar.actionBarTitle.setText("提交成功");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void continue_replenish() {
        startActivity(new Intent(this,MainTabActivity.class));
        finish();
    }

    @Override
    public void complete() {
        startActivity(new Intent(this,MainTabActivity.class));
        finish();
    }
}
