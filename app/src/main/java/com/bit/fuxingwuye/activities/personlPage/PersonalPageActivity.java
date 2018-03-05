package com.bit.fuxingwuye.activities.personlPage;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.setting.SettingActivity;
import com.bit.fuxingwuye.activities.aboutBit.AboutBitActivity;
import com.bit.fuxingwuye.activities.carManager.CarManagerActivity;
import com.bit.fuxingwuye.activities.editPersonal.EditPersonalActivity;
import com.bit.fuxingwuye.activities.feedback.FeedbackActivity;
import com.bit.fuxingwuye.activities.houseManager.HouseManagerActivity;
import com.bit.fuxingwuye.activities.householdManager.HouseholdManagerActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.VersionBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityPersonalPageBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.CommonUtils;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

public class PersonalPageActivity extends BaseActivity<PPPresenterImpl> implements PPContract.View{

    private ActivityPersonalPageBinding mBinding;
    private ProgressDialog pdialog;
    private ACache mCache;
    private UserBean mUserBean;
    mBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new mBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.data.refreshdata");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_personal_page);
        mCache = ACache.get(this);
        mBinding.toolbar.actionBarTitle.setText("个人中心");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivRightActionBar.setImageResource(R.mipmap.icon_setting);
        mBinding.toolbar.ivRightActionBar.setPadding(20,20,20,20);
        mPresenter.findPersonal(new FindBean(mCache.getAsString("mobilePhone")));
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.setActionhandler(mPresenter);
        mBinding.toolbar.ivRightActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalPageActivity.this,SettingActivity.class));
            }
        });
        mBinding.llCheckversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonBean commonBean = new CommonBean();
                try {
                    commonBean.setVersion(CommonUtils.getVersionName(PersonalPageActivity.this)+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.checkUpgrade(commonBean);
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
    public void findPersonal(UserBean userBean) {
        mUserBean = userBean;
        mCache.put(HttpConstants.USER,userBean);
        mCache.put(HttpConstants.USERID,userBean.getId());
        if(TextUtils.isEmpty(userBean.getOwner())){
            mBinding.tvUsername.setText("请设置个人信息");
        }else{
            mBinding.tvUsername.setText(userBean.getUserName());
        }
        switch (userBean.getIdentityStatus()){
            case 1:
                mBinding.ivReview.setImageResource(R.mipmap.icon_pending_review);
                break;
            case 2:
                mBinding.ivReview.setImageResource(R.mipmap.icon_pending_review);
                break;
            case 3:
                mBinding.ivReview.setImageResource(R.mipmap.icon_been_reviewed);
                break;
        }
    }

    @Override
    public void editPersonalPage() {
        if(TextUtils.isEmpty(mUserBean.getOwner())){
            startActivity(new Intent(PersonalPageActivity.this,ReplenishDataActivity.class));
        }else{
            startActivity(new Intent(PersonalPageActivity.this,EditPersonalActivity.class));
        }
    }

    @Override
    public void houseManager() {
        startActivity(new Intent(this,HouseManagerActivity.class));
    }

    @Override
    public void householdManager() {
        startActivity(new Intent(this,HouseholdManagerActivity.class));
    }

    @Override
    public void carManager() {
        startActivity(new Intent(this,CarManagerActivity.class));
    }

    @Override
    public void aboutBit() {
        startActivity(new Intent(this,AboutBitActivity.class));
    }

    @Override
    public void feedback() {
        startActivity(new Intent(this,FeedbackActivity.class));
    }


    @Override
    public void hasUpgrade(final VersionBean versionBean) {
        BaseApplication.getInstance().checkWriteReadEnable(PersonalPageActivity.this);
        if(versionBean.getUpdateType().equals("1")){
            new LemonHelloInfo().setTitle("发现新版本")
                    .setContent("是否下载最新版本？")
                    .addAction(new LemonHelloAction("暂不更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    }))
                    .addAction(new LemonHelloAction("马上更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                            mPresenter.downLoad(PersonalPageActivity.this,versionBean.getUrl()+"?versionId="+versionBean.getVersionId());
                        }
                    })).show(PersonalPageActivity.this);
        }else if(versionBean.getUpdateType().equals("2")){
            new LemonHelloInfo().setTitle("发现新版本")
                    .setContent("此次版本为强制更新")
                    .addAction(new LemonHelloAction("马上更新", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                            mPresenter.downLoad(PersonalPageActivity.this,versionBean.getUrl()+"?versionId="+versionBean.getVersionId());
                        }
                    })).show(PersonalPageActivity.this);
        }
    }

    @Override
    public void initProgressDialog() {
        pdialog = new ProgressDialog(this);
        pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pdialog.setCancelable(true);
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setTitle("提示");
        pdialog.setMessage("正在下载中，请耐心等待...");
        pdialog.setMax(100);
        pdialog.show();
    }

    @Override
    public void showProgressDialog(int i) {
        pdialog.setProgress(i);
        if(i==100){
            pdialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class mBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mBinding.tvUsername.setText(intent.getStringExtra("username"));
            mUserBean.setOwner("1");
            mUserBean.setUserName(intent.getStringExtra("username"));
        }
    }
}
