package com.BIT.fuxingwuye.activities.carManager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.replenishCar.ReplenishCarActivity;
import com.BIT.fuxingwuye.adapter.CarAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.ParkBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityCarManagerBinding;
import com.BIT.fuxingwuye.utils.ACache;

import java.util.List;

public class CarManagerActivity extends BaseActivity<CarManagerPresenterImpl> implements CarManagerContract.View{

    private ActivityCarManagerBinding mBinding;
    private CarAdapter carAdapter;
    private CommonBean commonBean;
    private ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_car_manager);
        mBinding.toolbar.actionBarTitle.setText("车位管理");
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
        mBinding.btnAddcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CarManagerActivity.this,ReplenishCarActivity.class)
                        .putExtra("type",AppConstants.COME_FROM_CARMANAGER), AppConstants.REQ_ADD_CAR);
            }
        });
    }

    @Override
    protected void setupVM() {
        mCache = ACache.get(this);
        commonBean = new CommonBean();
        commonBean.setUserId(mCache.getAsString(HttpConstants.USERID));
//        mPresenter.getParks(commonBean);
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
    public void showParks(List<ParkBean> parkBeen) {
        mBinding.llNocar.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        mBinding.recyclerView.setFocusable(false);

        carAdapter = new CarAdapter(parkBeen);
        mBinding.recyclerView.setAdapter(carAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case AppConstants.RES_ADD_CAR:
                mPresenter.getParks(commonBean);
                break;
        }
    }
}
