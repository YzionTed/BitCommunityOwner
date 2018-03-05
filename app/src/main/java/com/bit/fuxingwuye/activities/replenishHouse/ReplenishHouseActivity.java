package com.BIT.fuxingwuye.activities.replenishHouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.roomPicker.RoomPickerActivity;
import com.BIT.fuxingwuye.activities.addNew.AddNewActivity;
import com.BIT.fuxingwuye.activities.replenishCar.ReplenishCarActivity;
import com.BIT.fuxingwuye.adapter.HouseholdAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.HouseholdBean;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.bean.ReplenishHouseBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityReplenishHouseBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReplenishHouseActivity extends BaseActivity<RHPresenterImpl> implements RHContract.View {

    private ActivityReplenishHouseBinding mBinding;
    private ACache mCache;
    private List<HouseholdBean> householdBeanList = new ArrayList<HouseholdBean>();
    private HouseholdAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_replenish_house);
        mCache = ACache.get(this);
        ReplenishHouseBean replenishBean = new ReplenishHouseBean();
        replenishBean.setUserId(mCache.getAsString(HttpConstants.USERID));
        Long today = System.currentTimeMillis();
        replenishBean.setRegisterTime(today);
        mBinding.tvHouseday.setText(DateFormat.format("yyyy-MM-dd",today));
        mBinding.setBean(replenishBean);
        mBinding.toolbar.actionBarTitle.setText("完善个人信息");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        mBinding.recyclerView.setFocusable(false);
    }

    @Override
    protected void setupVM() {
        if (getIntent().getIntExtra("type", 0) == AppConstants.COME_FROM_REPLENISHDATA) {  //完善个人信息
            mBinding.toolbar.actionBarTitle.setText("完善个人信息");
            mBinding.tvType.setText("未认证");
        } else if (getIntent().getIntExtra("type", 0) == AppConstants.COME_FROM_HOUSEMANAGER){ //新增住房
            mBinding.toolbar.actionBarTitle.setText("新增住房");
            mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
            mBinding.ivTitle.setVisibility(View.GONE);
            mBinding.llTitle.setVisibility(View.GONE);
            mBinding.btnCommit.setText("提交");
        }
        mBinding.tvName.setText(getIntent().getStringExtra("username"));
        mBinding.tvPhone.setText("(" + CommonUtils.replaceStar(getIntent().getStringExtra("phone"), 3, 6) + ")");
    }

    @Override
    protected void setupHandlers() {
        mBinding.llHouseday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(mBinding.tvHouseday);
            }
        });
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.getBean().getFloorId())
                        || TextUtils.isEmpty(mBinding.getBean().getArea())
                        || TextUtils.isEmpty(mBinding.getBean().getRegisterTime() + "")) {
                    Toast.makeText(ReplenishHouseActivity.this, "请填写完整数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(null!=mBinding.getBean().getContractNo()){
                    mBinding.getBean().setContractNo(CommonUtils.encryptData(mBinding.getBean().getContractNo()));
                }
                mBinding.getBean().setAddress(CommonUtils.encryptData(mBinding.getBean().getAddress()));
                mPresenter.commitData(mBinding.getBean());
            }
        });
        mBinding.btnAddHousehold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mBinding.getBean().getFloorId()) {
                    mPresenter.addHousehold();
                } else {
                    Toast.makeText(ReplenishHouseActivity.this, "请先选择住房", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.llHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ReplenishHouseActivity.this, RoomPickerActivity.class).putExtra("limit",3)
                        , AppConstants.REQ_PICK_ROOM);
            }
        });
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDatePicker(final TextView tv1) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                mBinding.getBean().setRegisterTime(c.getTimeInMillis());
                tv1.setText(DateFormat.format("yyyy-MM-dd", c));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
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
    public void commitSuccess(String str) {
        if (householdBeanList.size() > 0) {
            HouseholdsBean householdBeans = new HouseholdsBean();
            householdBeans.setUserId(mBinding.getBean().getUserId());
            householdBeans.setFloorId(mBinding.getBean().getFloorId());
            householdBeans.setList(householdBeanList);
            mPresenter.commitHousehold(householdBeans);
        } else {
            commithhSuccess(str);
        }
    }

    @Override
    public void commithhSuccess(String str) {
        if (getIntent().getIntExtra("type", 0) == AppConstants.COME_FROM_REPLENISHDATA) {  //完善个人信息
            startActivity(new Intent(ReplenishHouseActivity.this, ReplenishCarActivity.class)
                    .putExtra("type", AppConstants.COME_FROM_REPLENISHDATA));
            finish();
        } else if (getIntent().getIntExtra("type", 0) == AppConstants.COME_FROM_HOUSEMANAGER){                                    //新增住房
            Intent it = new Intent();
            setResult(AppConstants.RES_ADD_HOUSE, it);
            finish();
        }
    }

    @Override
    public void addHousehold() {
        Intent it = new Intent(ReplenishHouseActivity.this, AddNewActivity.class);
        it.putExtra("floorId", mBinding.getBean().getFloorId());
        it.putExtra("household", false);
        startActivityForResult(it, AppConstants.REQ_ADD_HOUSEHOLD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case AppConstants.RES_PICK_ROOM:
                mBinding.getBean().setFloorId(data.getStringExtra("floorid"));
                mBinding.getBean().setAddress(data.getStringExtra("house"));
                mBinding.tvHouse.setText(data.getStringExtra("house"));
                break;
            case AppConstants.RES_ADD_HOUSEHOLD:
                HouseholdBean householdBean = (HouseholdBean) data.getSerializableExtra("household");
                householdBeanList.add(householdBean);
                mAdapter = new HouseholdAdapter(householdBeanList);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
                mBinding.recyclerView.setAdapter(mAdapter);
                break;
            default:
                break;
        }
    }
}
