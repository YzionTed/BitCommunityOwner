package com.bit.fuxingwuye.activities.editPersonal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bit.communityOwner.util.AppUtil;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.personalEdit.PersonalEditActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityEditPersonalBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.ImageLoaderUtil;

public class EditPersonalActivity extends BaseActivity<EpPresenterImpl> implements EpContract.View, View.OnClickListener {

    private ActivityEditPersonalBinding mBinding;
    private ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_personal);
        mBinding.toolbar.actionBarTitle.setText("个人信息");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setupVM() {
        mCache = ACache.get(this);
        TokenBean userBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
//        userBean.setPhone(CommonUtils.replaceStar(userBean.getPhone(), 3, 6));
//        userBean.setIdentityCard(CommonUtils.replaceStar(userBean.getIdentityCard(), 3, 13));
        mBinding.setBean(userBean);
        if ("1".equals(mBinding.getBean().getSex())) {
            mBinding.tvSex.setText("男");
        } else {
            mBinding.tvSex.setText("女");
        }
        if (!TextUtils.isEmpty(userBean.getHeadImg())) {
            ImageLoaderUtil.setImageWithCache(userBean.getHeadImg(), mBinding.ivHeadimg);
        }

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        mBinding.tvShengri.setText(simpleDateFormat.format(new Date(userBean.get)));

        mBinding.tvLanya.setText(AppUtil.getBtAddress());
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rlPhoto.setOnClickListener(this);
        mBinding.rlName.setOnClickListener(this);
        mBinding.rlPhone.setOnClickListener(this);
        mBinding.rlSex.setOnClickListener(this);
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
    public void onClick(View v) {
        Intent it = new Intent(EditPersonalActivity.this, PersonalEditActivity.class);
        switch (v.getId()) {
            case R.id.rl_photo:
                it.putExtra("style", AppConstants.EDIT_PHOTO);
                startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
                break;
            case R.id.rl_name:
                it.putExtra("style", AppConstants.EDIT_NAME);
                startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
                break;
            case R.id.rl_phone:
                it.putExtra("style", AppConstants.EDIT_PHONE);
                startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
                break;
            case R.id.rl_sex:
//                it.putExtra("style", AppConstants.EDIT_SEX);
//                startActivityForResult(it, AppConstants.REQ_REFRESH_INFO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case AppConstants.RES_REFRESH_INFO:
                setupVM();
//                TokenBean userBean = (TokenBean) mCache.getAsObject(HttpConstants.TOKENBEAN);
//                userBean.setPhone(CommonUtils.replaceStar(userBean.getPhone(), 3, 6));
//                userBean.setIdentityCard(CommonUtils.replaceStar(userBean.getIdentityCard(), 3, 13));
//                mBinding.setBean(userBean);
//                if ("1".equals(mBinding.getBean().getSex())) {
//                    mBinding.tvSex.setText("男");
//                } else {
//                    mBinding.tvSex.setText("女");
//                }
//                if (null != userBean.getHeadImg() && !TextUtils.isEmpty(userBean.getHeadImg())) {
//                    ImageLoaderUtil.setImageWithCache(userBean.getHeadImg(), mBinding.ivHeadimg);
//                }
//                break;
        }
    }
}
