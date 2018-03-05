package com.bit.fuxingwuye.activities.fragment.smartGate;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.Bluetooth.bean.SearchBlueDeviceBean;
import com.bit.fuxingwuye.Bluetooth.mili.MiLiState;
import com.bit.fuxingwuye.Bluetooth.util.BluetoothUtils;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseFragment;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.DoorMiLiRequestBean;
import com.bit.fuxingwuye.utils.CustomDialog;
import com.bit.fuxingwuye.utils.DialogUtil;
import com.bit.fuxingwuye.utils.SensorManagerHelper;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.views.DoughnutProgress;
import com.smarthome.bleself.sdk.BluetoothApiAction;
import com.smarthome.bleself.sdk.IBluetoothApiInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2018/3/1.
 */

public class BluetoothDoorFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_animation;
    private Switch shake_switch;
    private DoughnutProgress circle_progress;
    private DoorMILiBean doorMILiBean;
    private String Tag = "BluetoothDoorFragment";
    private RelativeLayout rl_change_gate;
    private TextView tv_name;

    private boolean isNeedShake=true;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_blue_door;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initEventAndData() {
        iv_animation = (ImageView) mView.findViewById(R.id.iv_animation);
        circle_progress = (DoughnutProgress) mView.findViewById(R.id.circle_progress);
        shake_switch = (Switch) mView.findViewById(R.id.shake_switch);
        rl_change_gate = (RelativeLayout) mView.findViewById(R.id.rl_change_gate);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);

        iv_animation.setOnClickListener(this);
        rl_change_gate.setOnClickListener(this);

        initListener();
        shake_switch.setChecked(true);
    }

    /**
     * 监听
     */
    private void initListener() {
        SensorManagerHelper sensorHelper = new SensorManagerHelper(getActivity());
        sensorHelper.setOnShakeListener(new SensorManagerHelper.OnShakeListener() {

            @Override
            public void onShake() {
                // TODO Auto-generated method stub
                System.out.println("shake");
                Toast.makeText(getActivity(), "你在摇哦", Toast.LENGTH_SHORT).show();
                Log.e(Tag, "shake==");
                if (isNeedShake) {
                    circle_progress.start();
                    isNeedShake = false;
                    BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList = BaseApplication.getInstance().getBlueToothApp().getSearchBlueDeviceBeanList();
                            getMenJinMiLi(searchBlueDeviceBeanList, 2);
                        }
                    }, 2000);
                }
            }
        });

        shake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(Tag, "isChecked==" + isChecked);
                isNeedShake = isChecked;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_animation:
                circle_progress.start();
                if (doorMILiBean == null || doorMILiBean.isFirst()) {
                    BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList = BaseApplication.getInstance().getBlueToothApp().getSearchBlueDeviceBeanList();
                            getMenJinMiLi(searchBlueDeviceBeanList, 1);
                        }
                    }, 2000);
                } else {
                    clickBlueMiLi(doorMILiBean.getMac(), doorMILiBean.getPin(), 1);
                }
                break;
            case R.id.rl_change_gate:
                Intent intent = new Intent(mActivity, ChangeAccessActivity.class);
                if (doorMILiBean != null) {
                    intent.putExtra("doorMILiBean", doorMILiBean);
                }
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 10) {
                doorMILiBean = (DoorMILiBean) data.getSerializableExtra("doorMILiBean");
                tv_name.setText(doorMILiBean.getName());
            }
        }
    }

    /**
     * 判断获取的设备是否是米粒蓝牙
     *
     * @param searchBlueDeviceBeanList
     * @param type                     1:点击 2：摇一摇
     */
    private void getMenJinMiLi(final ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList, final int type) {
        //  BluetoothUtils.getMiliBluetooth(searchBlueDeviceBeanList);

        if (BaseApplication.getInstance().getBlueToothApp().isLocationEnalbe()) {
            ToastUtil.showShort("请打开您的定位权限！");
            circle_progress.stop();
            if (type == 2) {
                isNeedShake = true;
            }
            return;
        }
        if (searchBlueDeviceBeanList.size() < 1) {
            ToastUtil.showShort("没有找到蓝牙设备");
            circle_progress.stop();
            if (type == 2) {
                isNeedShake = true;
            }
            return;
        }

        String[] doorMacArr = new String[searchBlueDeviceBeanList.size()];
        for (int i = 0; i < searchBlueDeviceBeanList.size(); i++) {
            String address = searchBlueDeviceBeanList.get(i).getBluetoothDevice().getAddress();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < address.length(); j++) {
                char c = address.charAt(j);
                if (!(c + "").equals(":")) {
                    sb.append(c);
                }
            }
            doorMacArr[i] = sb.toString();
            Log.e(Tag, " doorMacArr[i]=" + doorMacArr[i]);
        }
        DoorMiLiRequestBean doorMiLiRequestBean = new DoorMiLiRequestBean();
        doorMiLiRequestBean.setCommunityId("5a82adf3b06c97e0cd6c0f3d");
        doorMiLiRequestBean.setDoorMacArr(doorMacArr);
        Api.getDoorDate(doorMiLiRequestBean, new ResponseCallBack<List<DoorMILiBean>>() {
            @Override
            public void onSuccess(List<DoorMILiBean> doorMILiBeans) {
                super.onSuccess(doorMILiBeans);
                Log.e(Tag, "doorMILiBeans==" + doorMILiBeans);
                if (doorMILiBeans != null) {
                    if (doorMILiBeans.size() > 0) {
                        DoorMILiBean doorMILiBean = BluetoothUtils.getMaxRsic(searchBlueDeviceBeanList, doorMILiBeans);
                        if (doorMILiBean != null) {
                            clickBlueMiLi(doorMILiBean.getMac(), doorMILiBean.getPin(), type);
                        } else {
                            if (type == 2) {
                                isNeedShake = true;
                            }
                            circle_progress.stop();
                            ToastUtil.showShort("没有搜索到门的设备");
                            Log.e(Tag, "没有搜索到门的设备！");
                        }
                    } else {
                        if (type == 2) {
                            isNeedShake = true;
                        }
                        circle_progress.stop();
                        ToastUtil.showShort("没有搜索到门的设备");
                        Log.e(Tag, "没有有用的蓝牙设备");
                    }
                } else {
                    if (type == 2) {
                        isNeedShake = true;
                    }
                    ToastUtil.showShort("没有搜索到门的设备");
                    circle_progress.stop();
                    Log.e(Tag, "没有有用的蓝牙设备");
                }

            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                circle_progress.stop();
                if (type == 2) {
                    isNeedShake = true;
                }
            }
        });
    }


    /**
     * 米粒的开门
     */
    private void clickBlueMiLi(String destMac, String destPin, final int type) {
        Log.e(Tag, "打开米粒门 mac=" + destMac + " destPin=" + destPin);

        //F0:C7:7F:9D:66:37
        BluetoothApiAction.bluetoothActionUnlock(destMac, destPin, getActivity(), new IBluetoothApiInterface.IBluetoothApiCallback<Object>() {

            @Override
            public void onFailure(final String arg0) {
                circle_progress.stop();
                if (type == 2) {
                    isNeedShake = true;
                }
                Log.e(Tag, "" + MiLiState.getCodeDesc(arg0));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "开门失败" + MiLiState.getCodeDesc(arg0), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onSuccess(Object arg0) {
                if (type == 2) {
                    isNeedShake = true;
                }
                succssAnimation();
            }
        });
    }


    @Override
    public void toastMsg(String msg) {

    }

    public void succssAnimation() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final CustomDialog customDialog = DialogUtil.showFrameAnimDialog(mContext, R.drawable.anim_open_door);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (customDialog != null && customDialog.isShowing()) {
                            customDialog.dismiss();
                            circle_progress.stop();
                        }
                    }
                }, 3000);

            }
        });
    }

}
