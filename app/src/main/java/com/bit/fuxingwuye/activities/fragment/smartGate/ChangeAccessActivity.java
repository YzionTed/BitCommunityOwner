package com.bit.fuxingwuye.activities.fragment.smartGate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bit.communityOwner.BaseActivity;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.DoorMILiBeanList;
import com.bit.fuxingwuye.bean.DoorMiLiRequestBean;
import com.bit.fuxingwuye.constant.PreferenceConst;
import com.bit.fuxingwuye.databinding.ActivityAddReplyBinding;
import com.bit.fuxingwuye.utils.CommonAdapter;
import com.bit.fuxingwuye.utils.PreferenceUtils;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.utils.ViewHolder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ChangeAccessActivity extends BaseActivity implements View.OnClickListener {

    String Tag = "ChangeAccessActivity";
    private ListView lv_list;
    private CommonAdapter commonAdapter;
    private DoorMILiBean doorMiLiBean;
    private TextView actionBarTitle;
    private TextView btnRightActionBar;
    private ImageView btnBack;
    private ImageView ivRightActionBar;
    private ProgressBar pbLoaingActionBar;
    private RelativeLayout actionBar;

    private ActivityAddReplyBinding mBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_access);
        initView();
        initDate();
    }


    protected void initView() {
        doorMiLiBean = (DoorMILiBean) getIntent().getSerializableExtra("doorMILiBean");
        actionBarTitle = (TextView) findViewById(R.id.action_bar_title);
        btnRightActionBar = (TextView) findViewById(R.id.btn_right_action_bar);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        ivRightActionBar = (ImageView) findViewById(R.id.iv_right_action_bar);
        pbLoaingActionBar = (ProgressBar) findViewById(R.id.pb_loaing_action_bar);
        actionBar = (RelativeLayout) findViewById(R.id.action_bar);
        lv_list = (ListView) findViewById(R.id.lv_list);
        btnBack.setOnClickListener(this);
        actionBarTitle.setText("切换门禁");
    }


    protected void initDate() {

        commonAdapter = new CommonAdapter<DoorMILiBean>(this, R.layout.item_door_access) {
            @Override
            public void convert(final ViewHolder holder, final DoorMILiBean doorMILiBean, int position, View convertView) {
                itemDo(holder, doorMILiBean, position);
            }
        };
        lv_list.setAdapter(commonAdapter);

        setCashDate();

        getMenJinMiLi();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }


    //放入缓存数据
    private void setCashDate() {

        String prefString = PreferenceUtils.getPrefString(this, PreferenceConst.PRE_NAME, PreferenceConst.MILIDOORMAC, "");
        if (prefString != null) {
            DoorMILiBeanList doorMILiBeans = new Gson().fromJson(prefString, DoorMILiBeanList.class);
            if (doorMILiBeans != null) {
                DoorMILiBean doorMILiBean = new DoorMILiBean();
                doorMILiBean.setFirst(true);
                doorMILiBean.setName("一键开门");
                if (doorMILiBeans.getDoorMILiBeans() != null) {
                    if (doorMILiBeans.getDoorMILiBeans().size() > 0) {
                        if (!doorMILiBeans.getDoorMILiBeans().get(0).isFirst()) {
                            doorMILiBeans.getDoorMILiBeans().add(0, doorMILiBean);
                        }
                    } else {
                        doorMILiBeans.getDoorMILiBeans().add(0, doorMILiBean);
                    }
                } else {
                    List<DoorMILiBean> doorMILiBeans1 = new ArrayList<>();
                    doorMILiBeans1.add(doorMILiBean);
                    doorMILiBeans.setDoorMILiBeans(doorMILiBeans1);
                }
            } else {
                doorMILiBeans = new DoorMILiBeanList();
                List<DoorMILiBean> doorMILiBeans1 = new ArrayList<>();
                DoorMILiBean doorMILiBean = new DoorMILiBean();
                doorMILiBean.setFirst(true);
                doorMILiBean.setName("一键开门");
                doorMILiBeans1.add(doorMILiBean);
                doorMILiBeans.setDoorMILiBeans(doorMILiBeans1);
            }
            commonAdapter.setDatas(doorMILiBeans.getDoorMILiBeans());
        }

    }


    /**
     * 每个Item进行操作
     *
     * @param holder
     * @param doorMILiBean
     * @param position
     */
    private void itemDo(final ViewHolder holder, final DoorMILiBean doorMILiBean, final int position) {

        if (ChangeAccessActivity.this.doorMiLiBean != null) {
            if (ChangeAccessActivity.this.doorMiLiBean.isFirstChecked()) {
                if(position==0){
                    ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                }
            } else {
                if (ChangeAccessActivity.this.doorMiLiBean.getMac() != null) {
                    if (ChangeAccessActivity.this.doorMiLiBean.getMac().equals(doorMILiBean.getMac())) {
                        ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                    }
                }
            }
        } else {
            if (position == 0) {
                ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
            }
        }

        holder.setText(R.id.tv_item, doorMILiBean.getName());
        holder.setOnClickListener(R.id.tv_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    doorMILiBean.setFirstChecked(true);
                } else {
                    doorMILiBean.setFirstChecked(false);
                }

                ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                Intent intent = new Intent();
                intent.putExtra("doorMILiBean", doorMILiBean);
                setResult(10, intent);
                finish();

            }
        });
    }


    /**
     * 判断获取的设备是否是米粒蓝牙
     */
    private void getMenJinMiLi() {

        DoorMiLiRequestBean doorMiLiRequestBean = new DoorMiLiRequestBean();
        doorMiLiRequestBean.setCommunityId("5a82adf3b06c97e0cd6c0f3d");

        Api.getDoorDate(doorMiLiRequestBean, new ResponseCallBack<List<DoorMILiBean>>() {
            @Override
            public void onSuccess(List<DoorMILiBean> doorMILiBeans) {
                super.onSuccess(doorMILiBeans);
                Log.e(Tag, "doorMILiBeans==" + doorMILiBeans);
                if (doorMILiBeans != null) {

                    if (doorMILiBeans.size() > 0) {
                        DoorMILiBean doorMILiBean = new DoorMILiBean();
                        doorMILiBean.setFirst(true);
                        doorMILiBean.setName("一键开门");
                        DoorMILiBeanList doorMILiBeanList = new DoorMILiBeanList();
                        doorMILiBeanList.setDoorMILiBeans(doorMILiBeans);
                        doorMILiBeanList.getDoorMILiBeans().add(0, doorMILiBean);
                        commonAdapter.setDatas(doorMILiBeanList.getDoorMILiBeans());
                        PreferenceUtils.setPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, PreferenceConst.MILIDOORMAC, new Gson().toJson(doorMILiBeanList));

                    } else {
                        ToastUtil.showShort("您还没有可以开锁的设备");
                    }
                } else {
                    ToastUtil.showShort("您还没有可以开锁的设备");
                }

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);

            }
        });

    }
}


