package com.bit.fuxingwuye.activities.message;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.NoticeBean;
import com.bit.fuxingwuye.databinding.ActivityMessageBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageActivity extends BaseActivity<MsgPresenterImpl> implements MsgContract.View {

    private ActivityMessageBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_message);
        mBinding.toolbar.actionBarTitle.setText("物业通知");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setupVM() {
        String id = getIntent().getStringExtra("id");
        CommonBean commonBean = new CommonBean();
        commonBean.setId(id);
        mPresenter.getNotice(commonBean);
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
    public void showMsg(NoticeBean notice) {
        notice.setContent("\t\t\t\t"+notice.getContent());
        mBinding.setBean(notice);
        Date date = new Date(notice.getPublishTime());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        mBinding.tvTime.setText(time);
    }
}
