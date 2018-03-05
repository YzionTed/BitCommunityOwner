package com.BIT.fuxingwuye.activities.householdManager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.addNew.AddNewActivity;
import com.BIT.fuxingwuye.adapter.HouseholdAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.HouseholdBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityHouseholdManagerBinding;
import com.BIT.fuxingwuye.utils.ACache;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.List;

public class HouseholdManagerActivity extends BaseActivity<HHMPresenterImpl> implements HHMContract.View{

    private ActivityHouseholdManagerBinding mBinding;
    private HouseholdAdapter householdAdapter;
    private CommonBean commonBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_household_manager);
        mBinding.toolbar.actionBarTitle.setText("住户管理");
        mBinding.tvCommunity.setText(getIntent().getStringExtra("plotname"));
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.btnAddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HouseholdManagerActivity.this,AddNewActivity.class);
                it.putExtra("floorId",getIntent().getStringExtra("houseid"));
                it.putExtra("household",true);
                startActivityForResult(it, AppConstants.REQ_ADD_HOUSEHOLD);
            }
        });
    }

    @Override
    protected void setupVM() {

        commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        commonBean.setFloorId(getIntent().getStringExtra("houseid"));
        mPresenter.getUserAff(commonBean);

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
    public void showUserAff(final List<HouseholdBean> householdBeen) {
        mBinding.llNoidentified.setVisibility(View.GONE);
        mBinding.rlData.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        mBinding.recyclerView.setFocusable(false);

        householdAdapter = new HouseholdAdapter(householdBeen);
        mBinding.recyclerView.setAdapter(householdAdapter);
        householdAdapter.setOnItemClickListener(new HouseholdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                LemonHello.getWarningHello("提示","是否删除该住户")
                        .setContent("是否删除该住户")
                        .addAction(new LemonHelloAction("删除", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                                lemonHelloView.hide();
                                CommonBean commonBean = new CommonBean();
                                commonBean.setUserId(householdBeen.get(position).getId());
                                commonBean.setFloorId(getIntent().getStringExtra("houseid"));
                                mPresenter.deleteUser(commonBean);

                            }
                        }))
                        .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                                lemonHelloView.hide();
                            }
                        }))
                        .show(HouseholdManagerActivity.this);
            }
        });
    }

    @Override
    public void deleteSuccess() {
        commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        commonBean.setFloorId(getIntent().getStringExtra("houseid"));
        mPresenter.getUserAff(commonBean);
    }

    @Override
    public void showNo() {
        mBinding.llNoidentified.setVisibility(View.VISIBLE);
        mBinding.rlData.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConstants.RES_ADD_HOUSEHOLD:
                mPresenter.getUserAff(commonBean);
                break;
        }
    }
}
