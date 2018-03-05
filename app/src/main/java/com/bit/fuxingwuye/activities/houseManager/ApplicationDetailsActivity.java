package com.bit.fuxingwuye.activities.houseManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.base.EvenBusConstants;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.GsonUtil;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;
import com.google.gson.Gson;

import net.vidageek.mirror.bean.Bean;

import org.greenrobot.eventbus.EventBus;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by 23 on 2018/3/1.
 */

public class ApplicationDetailsActivity extends BaseActivity<ApplicationDetailsImpl> implements ApplicationDetailsContract.View{
    TextView agreenapplicationtv,Dismissapplicationtv;
    Intent intent;
    String id;
    int auditstatus=1;
    ImageView btn_back;
    LinearLayout stautly;
    TextView name,sex,birthday,contractPhone,identityCard,roomLocation,relationship,createAt,title;
    Gson gson=new Gson();
    @Override
    public void toastMsg(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);
        agreenapplicationtv= (TextView) findViewById(R.id.agreenapplication);
        Dismissapplicationtv= (TextView) findViewById(R.id.Dismissapplication);
        name= (TextView) findViewById(R.id.name);
        sex= (TextView) findViewById(R.id.sex);
        birthday= (TextView) findViewById(R.id.birthday);
        contractPhone= (TextView) findViewById(R.id.contractPhone);
        identityCard= (TextView) findViewById(R.id.identityCard);
        roomLocation= (TextView) findViewById(R.id.roomLocation);
        relationship= (TextView) findViewById(R.id.relationship);
        createAt= (TextView) findViewById(R.id.createAt);
        title= (TextView) findViewById(R.id.action_bar_title);
        stautly= (LinearLayout) findViewById(R.id.staut);
        btn_back= (ImageView) findViewById(R.id.btn_back);
        intent=getIntent();
        Bundle bundle=intent.getExtras();
        title.setText("住户申请详情");
        ProprietorBean.RecordsBean bean = null;
        if(bundle!=null){
            id=bundle.getString(HttpConstants.ID);
            auditstatus=bundle.getInt(HttpConstants.auditstatus);
            bean= gson.fromJson(bundle.getString(HttpConstants.USER),ProprietorBean.RecordsBean.class);
        }
        LogUtil.e(Tag.tag,"解析数据："+GsonUtil.toJson(bean));
        if(bean!=null){
            name.setText(""+bean.getName());
            if(bean.getSex()==1){
                sex.setText("男");
            }else{
                sex.setText("女");
            }
            birthday.setText(""+bean.getBirthday());
            contractPhone.setText(""+bean.getContractPhone());
            identityCard.setText(""+bean.getIdentityCard());
            roomLocation.setText(""+bean.getRoomLocation());
            if(bean.getRelationship()==1){
                relationship.setText("业主");
            }else  if(bean.getRelationship()==2){
                relationship.setText("家属");
            }else  if(bean.getRelationship()==3){
                relationship.setText("租客");
            }else{
                relationship.setText("未知");
            }
            createAt.setText(getFormatTime(bean.getCreateAt()));
        }
        if(auditstatus==0){
            stautly.setVisibility(View.VISIBLE);
        }else{
            stautly.setVisibility(View.GONE);
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        agreenapplicationtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==null||"".equals(id)){
                    Toast.makeText(ApplicationDetailsActivity.this,"缺少重要参数，请返回重新再试",Toast.LENGTH_LONG).show();
                }else{
                    ProprietorBean.RecordsBean bean=new ProprietorBean.RecordsBean();
                    bean.setId(id);
                    bean.setAuditStatus(1);
                    mPresenter.GetApplication(bean);
                }

            }
        });
        Dismissapplicationtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==null||"".equals(id)){
                    Toast.makeText(ApplicationDetailsActivity.this,"缺少重要参数，请返回重新再试",Toast.LENGTH_LONG).show();
                }else{
                    ProprietorBean.RecordsBean bean=new ProprietorBean.RecordsBean();
                    bean.setId(id);
                    bean.setAuditStatus(-1);
                    mPresenter.DismissApplication(bean);
                }

            }
        });
    }

    @Override
    public void ShowApplication() {
         Toast.makeText(this,"申请成功",Toast.LENGTH_LONG).show();
        EvenBusMessage message=new EvenBusMessage();
        message.setEvent(EvenBusConstants.ApplicationRecordActivity);
        EventBus.getDefault().post(message);
        finish();
    }

    @Override
    public void ShowDismissApplication() {
        EvenBusMessage message=new EvenBusMessage();
        message.setEvent(EvenBusConstants.ApplicationRecordActivity);
        EventBus.getDefault().post(message);
        Toast.makeText(this,"驳回成功",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void initEventAndData() {

    }
    public static String getFormatTime(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(time);

        String formatTime = format.format(date);

        return formatTime;

    }









    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
