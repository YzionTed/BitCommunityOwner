package com.BIT.fuxingwuye.activities.addNew;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.ProvincePickerActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.HouseholdBean;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.constant.AppConstants;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityAddNewBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewActivity extends BaseActivity<AddNewPresenterImpl> implements AddNewContract.View {

    private ActivityAddNewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_new);
        mBinding.toolbar.actionBarTitle.setText("新增住户");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HouseholdBean householdBean = new HouseholdBean();
        householdBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        householdBean.setFloorId(getIntent().getStringExtra("floorId"));
        householdBean.setSex(AppConstants.MALE);
        householdBean.setOwner(AppConstants.HOUSE_RELATIONSHIP);
        mBinding.setBean(householdBean);
    }

    @Override
    protected void setupHandlers() {
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBinding.getBean().getUserName())){
                    Toast.makeText(AddNewActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CommonUtils.isLegalId(mBinding.getBean().getIdentity())){
                    Toast.makeText(AddNewActivity.this,"请检查身份证",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CommonUtils.verifyPhone(mBinding.getBean().getMobilePhone())){
                    Toast.makeText(AddNewActivity.this,"请检查手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mBinding.getBean().getAccount())||TextUtils.isEmpty(mBinding.getBean().getBirthday()+"")){
                    Toast.makeText(AddNewActivity.this,"请填写完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                mBinding.getBean().setUserName(CommonUtils.encryptData(mBinding.getBean().getUserName()));
                mBinding.getBean().setMobilePhone(CommonUtils.encryptData(mBinding.getBean().getMobilePhone()));
                mBinding.getBean().setIdentity(CommonUtils.encryptData(mBinding.getBean().getIdentity()));
                mBinding.getBean().setAccount(CommonUtils.encryptData(mBinding.getBean().getAccount()));
                mBinding.btnCommit.setClickable(false);
                if(getIntent().getBooleanExtra("household",false)){//从住户管理来
                    List<HouseholdBean> householdBeanList = new ArrayList<HouseholdBean>();
                    householdBeanList.add(mBinding.getBean());
                    HouseholdsBean householdsBean = new HouseholdsBean();
                    householdsBean.setUserId(mBinding.getBean().getUserId());
                    householdsBean.setFloorId(mBinding.getBean().getFloorId());
                    householdsBean.setList(householdBeanList);
                    mPresenter.addNew(householdsBean);
                }else{                                            //从添加住房来
                    Intent it = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("household",mBinding.getBean());
                    it.putExtras(bundle);
                    setResult(AppConstants.RES_ADD_HOUSEHOLD,it);
                    finish();
                }
            }
        });

        mBinding.llBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(mBinding.tvBirthday);
            }
        });
        mBinding.rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_male){
                    mBinding.getBean().setSex(AppConstants.MALE);
                }else if(checkedId==R.id.rb_female){
                    mBinding.getBean().setSex(AppConstants.FAMALE);
                }
            }
        });
        mBinding.rgRelation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_1){
                    mBinding.getBean().setOwner(AppConstants.HOUSE_RELATIONSHIP);
                }else if(checkedId==R.id.rb_2){
                    mBinding.getBean().setOwner(AppConstants.HOUSE_RENTER);
                }
            }
        });
        mBinding.llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddNewActivity.this,ProvincePickerActivity.class),AppConstants.REQ_PICK_PROVINCE);
            }
        });
    }

    private void showDatePicker(final TextView tv1) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                mBinding.getBean().setBirthday(c.getTimeInMillis());
                tv1.setText(DateFormat.format("yyy-MM-dd", c));
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
    public void addSuccess() {
        Intent it = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("household",mBinding.getBean());
        it.putExtras(bundle);
        setResult(AppConstants.RES_ADD_HOUSEHOLD,it);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case AppConstants.RES_PICK_PROVINCE:
                Log.e("account",data.getStringExtra("account"));
                mBinding.getBean().setAccount(data.getStringExtra("account"));
                mBinding.tvAccount.setText(data.getStringExtra("account"));
                break;
            default:
                break;
        }
    }
}
