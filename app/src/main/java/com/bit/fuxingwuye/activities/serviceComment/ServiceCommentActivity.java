package com.BIT.fuxingwuye.activities.serviceComment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.EvaluationBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityServiceCommentBinding;
import com.BIT.fuxingwuye.utils.ACache;

public class ServiceCommentActivity extends BaseActivity<SCPresenterImpl> implements SCContract.View {

    private ActivityServiceCommentBinding mBinding;
    private EvaluationBean evaluationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_service_comment);
        mBinding.toolbar.actionBarTitle.setText("服务评价");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        evaluationBean = new EvaluationBean();
        evaluationBean.setEvaluationNo(getIntent().getStringExtra("id"));
        evaluationBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        evaluationBean.setAnonymous("1");

        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBinding.etServiceComment.getText().toString().trim())){
                    Toast.makeText(ServiceCommentActivity.this,"请填写评价内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                evaluationBean.setGrade(mBinding.ratingbar.getStarCount()+"");
                evaluationBean.setContent(mBinding.etServiceComment.getText().toString().trim());
                mPresenter.comment(evaluationBean);
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

    @Override
    public void conmmentSuccess() {
        Toast.makeText(this,"评价成功",Toast.LENGTH_SHORT).show();
        finish();
    }
}
