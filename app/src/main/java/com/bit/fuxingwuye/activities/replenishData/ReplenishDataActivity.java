package com.bit.fuxingwuye.activities.replenishData;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.activities.ProvincePickerActivity;
import com.bit.fuxingwuye.activities.roomPicker.RoomPickerActivity;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.EvenBusConstants;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.bean.HouseBean;
import com.bit.fuxingwuye.bean.ReplenishBean;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityReplenishDataBinding;
import com.bit.fuxingwuye.http.DialogUltis;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.CommonUtils;
import com.bit.fuxingwuye.utils.GsonUtil;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.bigkoo.pickerview.OptionsPickerView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReplenishDataActivity extends BaseActivity<RDPresenterImpl> implements RDContract.View{

    private ActivityReplenishDataBinding mBinding;
    private String name;
    private ACache mCache;
    ReplenishBean bean=new ReplenishBean();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    TimePickerDialog mDialogYearMonthDay;
    long tenYears = 100L * 365 * 1000 * 60 * 60 * 24L;
    Intent intent;
    int gopage=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            gopage=bundle.getInt("gohousing");
        }
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_replenish_data);
        mCache = ACache.get(this);
       /* ReplenishBean replenishBean = new ReplenishBean();
        replenishBean.setSex(AppConstants.MALE);
        replenishBean.setOwner(AppConstants.HOUSE_OWNER);
        replenishBean.setPolitics("群众");
        replenishBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        mBinding.setBean(replenishBean);*/
        mBinding.toolbar.actionBarTitle.setText("房产认证");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
       // mBinding.tvMobile.setText(ACache.get(this).getAsString(HttpConstants.MOBILE));
    }

    @Override
    protected void setupHandlers() {
        mBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mBinding.houselist.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"请选择住房",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mBinding.idcare.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"请选择个人身份",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mBinding.name.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"用户名为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CommonUtils.isLegalId(mBinding.identityCard.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"请检查身份证",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mBinding.tvAccount.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"请选择户口所在地",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CommonUtils.verifyPhone(mBinding.tvMobile.getText().toString().trim())){
                    Toast.makeText(ReplenishDataActivity.this,"请填写正确的电话号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mBinding.idcare.getText().toString().trim().equals("业主")){
                    initdate(1);
                    commint(bean);
                   /* mPresenter.commitData(bean);*/
                }else if(mBinding.idcare.getText().toString().trim().equals("家属")){
                    initdate(2);
                    commitMember(bean);
                }else{
                    initdate(3);
                    commitMember(bean);
                }
                LogUtil.e(Tag.tag, GsonUtil.toJson(bean));

            }
        });
        mBinding.area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mBinding.area.setText(s);
                        mBinding.area.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mBinding.area.setText(s);
                    mBinding.area.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mBinding.area.setText(s.subSequence(0, 1));
                        mBinding.area.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.llBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(mBinding.tvBirthday);
            }
        });
        mBinding.llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ReplenishDataActivity.this,ProvincePickerActivity.class),100);
            }
        });
        mBinding.houselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ReplenishDataActivity.this,RoomPickerActivity.class),100);
            }
        });
        mBinding.idcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> name=new ArrayList<>();
                name.add("业主");
                name.add("家属");
                name.add("租客");
                //条件选择器
                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(ReplenishDataActivity.this, new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        mBinding.idcare.setText(name.get(options1));
                        if(options1==0){
                            mBinding.lyInfo.setVisibility(View.VISIBLE);
                            mBinding.lyOtherInfo.setVisibility(View.VISIBLE);

                        }else{
                            mBinding.lyInfo.setVisibility(View.GONE);
                            mBinding.lyOtherInfo.setVisibility(View.GONE);
                        }

                    }
                }).build();
                pvOptions.setPicker(name);
                pvOptions.show();
            }
        });
        mBinding.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogYearMonthDay = new TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH_DAY)
                        .setTitleStringId("")
                        .setToolBarTextColor(Color.parseColor("#a69e9d"))
                        .setThemeColor(Color.parseColor("#dddddd"))
                        .setMinMillseconds(-tenYears)
                        .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                        .setCallBack(new OnDateSetListener() {
                            @Override
                            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                                mBinding.data.setText(getDateToString(millseconds));
                            }
                        })
                        .build();
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");

            }
        });

        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rgPolitics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_1:

                        break;
                    case R.id.rb_2:

                        break;
                    case R.id.rb_3:

                        break;
                    case R.id.rb_4:

                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void initdate(int i){
        bean.setDataStatus(1);
        bean.setProprietorId(mCache.getAsString(HttpConstants.USERID));
        bean.setUserId(mCache.getAsString(HttpConstants.USERID));
        bean.setCommunityId(mCache.getAsString(HttpConstants.COMMUNIYID));
        bean.setRoomId(mCache.getAsString(HttpConstants.ROOMID));
        bean.setBuildingId(mCache.getAsString(HttpConstants.BUILDINGID));
        bean.setRoomName(mBinding.houselist.getText().toString().trim());
        bean.setRelationship(i);
        bean.setRoomLocation(mBinding.houselist.getText().toString().trim());
        bean.setName(mBinding.name.getText().toString().trim());
        bean.setIdentityCard(mBinding.identityCard.getText().toString().trim());
        bean.setHouseholdAddress(mBinding.tvAccount.getText().toString().trim());
        bean.setContractPhone(mBinding.tvMobile.getText().toString().trim());
        if(i==1){
            bean.setCurrentAddress(mBinding.currentaddress.getText().toString().trim());
            bean.setWorkUnit(mBinding.workunit.getText().toString().trim());
            //bean.setArea(mBinding.area.getText().toString().trim());
            bean.setArea(11*100);//写死了
            bean.setContract(mBinding.contract.getText().toString().trim());
            bean.setCheckInTime(mBinding.data.getText().toString().trim());
            bean.setTelPhone(mBinding.telphone.getText().toString().trim());
            if(mBinding.rb1.isChecked()){
                bean.setPoliticsStatus("1");
            }else if(mBinding.rb2.isChecked()){
                bean.setPoliticsStatus("2");
            }else if(mBinding.rb3.isChecked()){
                bean.setPoliticsStatus("3");
            }else if(mBinding.rb4.isChecked()){
                bean.setPoliticsStatus("4");
            }
        }
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
    public void commitSuccess(HouseBean str) {
        mCache.put(HttpConstants.OWNER,AppConstants.HOUSE_OWNER);
        mCache.put(HttpConstants.STATUS,AppConstants.HOUSE_RELATIONSHIP);
       // mCache.put(HttpConstants.USERNAME,name);
        if(gopage==1){
            Intent intent=new Intent(ReplenishDataActivity.this, MainTabActivity.class);
            startActivity(intent);
        }else{
            EvenBusMessage message=new EvenBusMessage();
            message.setEvent(EvenBusConstants.HouseManagerAcitvity);
            EventBus.getDefault().post(message);
            finish();
        }

    }

    @Override
    public void getMemberSuccess(HouseBean str) {
        LogUtil.e(Tag.tag,"添加家属和租客成功");
        if(gopage==1){
            Intent intent=new Intent(ReplenishDataActivity.this, MainTabActivity.class);
            startActivity(intent);
        }else{
            EvenBusMessage message=new EvenBusMessage();
            message.setEvent(EvenBusConstants.HouseManagerAcitvity);
            EventBus.getDefault().post(message);
            finish();
        }
    }

    private void showDatePicker(final TextView tv1) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                tv1.setText(DateFormat.format("yyy-MM-dd", c));

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 200:
                bean.setHouseholdAddress(data.getStringExtra("account"));
                mBinding.tvAccount.setText(data.getStringExtra("account"));
                break;
            case 300:
                mBinding.houselist.setText(data.getStringExtra("room"));
                break;
            default:
                break;
        }
    }
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
     private void commint(ReplenishBean replenishBean){
         Api.Replanish(replenishBean, new ResponseCallBack<HouseBean>() {
             @Override
             public void onSuccess(HouseBean data) {
                 commitSuccess(data);
                 Toast.makeText(ReplenishDataActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                 super.onSuccess(data);
             }

             @Override
             public void onFailure(ServiceException e) {
                 Toast.makeText(ReplenishDataActivity.this,e.getMsg(),Toast.LENGTH_LONG).show();
                 super.onFailure(e);
             }
         });
     }
     private void commitMember(ReplenishBean replenishBean){
         DialogUltis.showDialog(getSupportFragmentManager(),"提交中");
         Api.commitMember(replenishBean, new ResponseCallBack<HouseBean>() {
             @Override
             public void onSuccess(HouseBean data) {
                 getMemberSuccess(data);
                 DialogUltis.closeDialog();
                 super.onSuccess(data);
             }

             @Override
             public void onFailure(ServiceException e) {
                 DialogUltis.closeDialog();
                 Toast.makeText(ReplenishDataActivity.this,e.getMsg(),Toast.LENGTH_LONG).show();
                 super.onFailure(e);
             }
         });
     }
}
