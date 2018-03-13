package com.bit.fuxingwuye.activities.fragment.elevatorFrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.E;
import com.bit.communityOwner.BaseActivity;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.fragment.smartGate.BluetoothNetUtils;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.bean.ElevatorListRequestion;
import com.bit.fuxingwuye.bean.StoreDoorMILiBeanList;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.constant.PreferenceConst;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.CommonAdapter;
import com.bit.fuxingwuye.utils.PreferenceUtils;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.utils.ViewHolder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ChangeElevatorActivity extends BaseActivity implements View.OnClickListener {


    TextView actionBarTitle;
    ImageView btnBack;

    ListView lvElevator;

    private String[] blueAddressIds;
    private CommonAdapter commonAdapter;
    private ElevatorListBean doorJinBoBean;
    private ACache mCache;

    public String Tag = "ChangeElevatorActivity";
    private BluetoothNetUtils bluetoothNetUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_elevator);
        initView();
        initViewData();
    }

    private void initView() {
        bluetoothNetUtils = new BluetoothNetUtils();
        mCache = ACache.get(this);
        actionBarTitle = (TextView) findViewById(R.id.action_bar_title);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        lvElevator = (ListView) findViewById(R.id.lv_elevator);
        btnBack.setOnClickListener(this);
    }


    public void initViewData() {
        actionBarTitle.setText("切换电梯");
        doorJinBoBean = (ElevatorListBean) getIntent().getSerializableExtra("doorJinBoBean");

        blueAddressIds = getIntent().getStringArrayExtra("ids");
        initListView();
        getCashDate();
        getData();
    }

    /**
     * 展示缓存数据
     */
    private void getCashDate() {
        StoreElevatorListBeans bletoothElevateDate = bluetoothNetUtils.getBletoothElevateDate();
        if (bletoothElevateDate != null) {

            ElevatorListBean elevatorListBean = new ElevatorListBean();
            elevatorListBean.setFirst(true);
            elevatorListBean.setName("一键开梯");
            if (bletoothElevateDate.getElevatorListBeans() != null) {
                if (bletoothElevateDate.getElevatorListBeans().size() > 0) {
                    if (!bletoothElevateDate.getElevatorListBeans().get(0).isFirst()) {
                        bletoothElevateDate.getElevatorListBeans().add(0, elevatorListBean);
                    }
                } else {
                    bletoothElevateDate.getElevatorListBeans().add(0, elevatorListBean);
                }
            } else {
                List<ElevatorListBean> doorMILiBeans1 = new ArrayList<>();
                doorMILiBeans1.add(elevatorListBean);
                bletoothElevateDate.setElevatorListBeans(doorMILiBeans1);
            }
        } else {
            bletoothElevateDate = new StoreElevatorListBeans();
            List<ElevatorListBean> doorMILiBeans1 = new ArrayList<>();
            ElevatorListBean elevatorListBean = new ElevatorListBean();
            elevatorListBean.setFirst(true);
            elevatorListBean.setName("一键开梯");
            doorMILiBeans1.add(elevatorListBean);
            bletoothElevateDate.setElevatorListBeans(doorMILiBeans1);
        }

        commonAdapter.setDatas(bletoothElevateDate.getElevatorListBeans());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }


    private void initListView() {
        commonAdapter = new CommonAdapter<ElevatorListBean>(this, R.layout.item_access_child) {
            @Override
            public void convert(ViewHolder holder, final ElevatorListBean elevatorListBean, final int position, View convertView) {

                if (ChangeElevatorActivity.this.doorJinBoBean != null) {
                    if (ChangeElevatorActivity.this.doorJinBoBean.isFirstChecked()) {
                        if (position == 0) {
                            ((CheckBox) holder.getView(R.id.tv_item)).setChecked(true);
                        }else {
                            ((CheckBox) holder.getView(R.id.tv_item)).setChecked(false);
                        }
                    } else {
                        if (ChangeElevatorActivity.this.doorJinBoBean.getMacAddress() != null) {
                            if (ChangeElevatorActivity.this.doorJinBoBean.getMacAddress().equals(elevatorListBean.getMacAddress())) {
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
                    }
                }

                if (elevatorListBean.getName() != null) {
                    holder.setText(R.id.tv_item, elevatorListBean.getName());
                }else {
                    holder.setText(R.id.tv_item, "");
                }
                holder.setOnClickListener(R.id.tv_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            elevatorListBean.setFirstChecked(true);
                        } else {
                            elevatorListBean.setFirstChecked(false);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("elevatorListBean", elevatorListBean);
                        setResult(10, intent);
                        finish();
                    }
                });
            }
        };
        lvElevator.setAdapter(commonAdapter);
    }

    private void getData() {

        bluetoothNetUtils.getBluetoothElevatorDate(1, new BluetoothNetUtils.OnBlutoothElevatorCallBackListener() {
            @Override
            public void OnCallBack(int state, StoreElevatorListBeans storeElevatorListBeans) {

                if (state == 1) {

                    if (storeElevatorListBeans != null) {
                        if (storeElevatorListBeans.getElevatorListBeans().size() > 0) {
                            ElevatorListBean elevatorListBean = new ElevatorListBean();
                            elevatorListBean.setFirst(true);
                            elevatorListBean.setName("一键开梯");
                            storeElevatorListBeans.getElevatorListBeans().add(0, elevatorListBean);
                            PreferenceUtils.setPrefString(BaseApplication.getInstance().getContext(), PreferenceConst.PRE_NAME, BaseApplication.getUserLoginInfo().getId() + BaseApplication.getVillageInfo().getId() + PreferenceConst.BLUETOOTHELEVATOR, new Gson().toJson(storeElevatorListBeans));
                            commonAdapter.setDatas(storeElevatorListBeans.getElevatorListBeans());
                        } else {
                            ToastUtil.showShort("没有找到您可以开的电梯");
                        }
                    } else {
                        ToastUtil.showShort("没有找到您可以开的电梯");
                    }
                } else if (state == 2) {

                }
            }
        });

    }


}



