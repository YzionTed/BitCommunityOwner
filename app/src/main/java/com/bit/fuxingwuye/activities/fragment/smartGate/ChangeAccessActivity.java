package com.bit.fuxingwuye.activities.fragment.smartGate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bit.communityOwner.BaseActivity;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.StoreDoorMILiBeanList;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.constant.PreferenceConst;
import com.bit.fuxingwuye.databinding.ActivityAddReplyBinding;
import com.bit.fuxingwuye.utils.ACache;
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
    private ACache mCache;
    private ActivityAddReplyBinding mBinding;
    private BluetoothNetUtils bluetoothNetUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_access);
        initView();
        initDate();
    }


    protected void initView() {
        mCache = ACache.get(this);
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

        bluetoothNetUtils = new BluetoothNetUtils(this);
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

        StoreDoorMILiBeanList doorMILiBeans = bluetoothNetUtils.getBletoothDoorDate();
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
            doorMILiBeans = new StoreDoorMILiBeanList();
            List<DoorMILiBean> doorMILiBeans1 = new ArrayList<>();
            DoorMILiBean doorMILiBean = new DoorMILiBean();
            doorMILiBean.setFirst(true);
            doorMILiBean.setName("一键开门");
            doorMILiBeans1.add(doorMILiBean);
            doorMILiBeans.setDoorMILiBeans(doorMILiBeans1);
        }
        commonAdapter.setDatas(doorMILiBeans.getDoorMILiBeans());

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
                if (position == 0) {
                    ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                }else {
                    ((CheckBox) holder.getView(R.id.tv_item)).setChecked(false);
                }
            } else {
                if (ChangeAccessActivity.this.doorMiLiBean.getMac() != null) {
                    if (ChangeAccessActivity.this.doorMiLiBean.getMac().equals(doorMILiBean.getMac())) {
                        ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                    }else {
                        ((CheckBox) holder.getView(R.id.tv_item)).setChecked(false);
                    }
                }else {
                    ((CheckBox) holder.getView(R.id.tv_item)).setChecked(false);
                }
            }
        } else {
            if (position == 0) {
                ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
            }else {
                ((CheckBox) holder.getView(R.id.tv_item)).setChecked(false);
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

        bluetoothNetUtils.getMiLiNetDate(null,  1, new BluetoothNetUtils.OnBlutoothDoorCallBackListener() {
            @Override
            public void OnCallBack(int state, StoreDoorMILiBeanList storeDoorMILiBeanList) {
                if (state == 1) {//请求网络成功
                    if (storeDoorMILiBeanList != null) {
                        if (storeDoorMILiBeanList.getDoorMILiBeans().size() > 0) {
                            DoorMILiBean doorMILiBean = new DoorMILiBean();
                            doorMILiBean.setFirst(true);
                            doorMILiBean.setName("一键开门");
                            storeDoorMILiBeanList.getDoorMILiBeans().add(0, doorMILiBean);
                            PreferenceUtils.setPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() +BaseApplication.getVillageInfo().getId()+ PreferenceConst.MILIDOORMAC, new Gson().toJson(storeDoorMILiBeanList));
                            commonAdapter.setDatas(storeDoorMILiBeanList.getDoorMILiBeans());
                        } else {
                            ToastUtil.showShort("您还没有可以开锁的设备");
                        }
                    } else {
                        ToastUtil.showShort("您还没有可以开锁的设备");
                    }
                }
            }
        });

    }
}


