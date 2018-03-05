package com.BIT.fuxingwuye.activities.feedback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.GetFeedbackBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityFeedbackBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.LogUtil;

public class FeedbackActivity extends BaseActivity<FeedbackPresenterImpl> implements FeedbackContract.View {

    private static final String TAG = FeedbackActivity.class.getSimpleName();
    private ActivityFeedbackBinding mBinding;
    private CommonBean commonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        mBinding.setBean(commonBean);
        mBinding.toolbar.actionBarTitle.setText("反馈与建议");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setupHandlers() {
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.getBean().getMessage().length() < 3) {
                    Toast.makeText(FeedbackActivity.this, "请输入至少3个字", Toast.LENGTH_SHORT).show();
                    return;
                }
//                mPresenter.feedback(mBinding.getBean());
                Api.feedback(mBinding.etContent.getText().toString(), new ResponseCallBack<GetFeedbackBean>() {
                    @Override
                    public void onSuccess(GetFeedbackBean data) {
                        LogUtil.d(TAG, "data:" + data);
                        toastMsg("反馈成功");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(ServiceException e) {
                        toastMsg(e.getMsg());
                    }

                });
            }
        });

        mBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int a = s.length();
                mBinding.tvCount.setText(a + "");
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedbackSuccess() {
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
