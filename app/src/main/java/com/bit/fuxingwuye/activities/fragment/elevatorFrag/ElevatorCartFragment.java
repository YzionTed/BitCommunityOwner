package com.BIT.fuxingwuye.activities.fragment.elevatorFrag;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
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

import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.communityOwner.net.ServiceException;
import com.BIT.fuxingwuye.Bluetooth.bean.SearchBlueDeviceBean;
import com.BIT.fuxingwuye.Bluetooth.jinbo.JiBoUtils;
import com.BIT.fuxingwuye.Bluetooth.util.BluetoothUtils;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.EAnimationActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.base.BaseFragment;
import com.BIT.fuxingwuye.bean.ElevatorCardRequestion;
import com.BIT.fuxingwuye.bean.ElevatorListBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.BlueToothUtil;
import com.BIT.fuxingwuye.utils.HexUtil;
import com.BIT.fuxingwuye.utils.SensorManagerHelper;
import com.BIT.fuxingwuye.utils.ToastUtil;
import com.BIT.fuxingwuye.views.DoughnutProgress;
import com.inuker.bluetooth.library.base.Conf;
import com.inuker.bluetooth.library.base.Register;

import java.util.ArrayList;
import java.util.List;


/**
 * 梯禁卡
 * Created by kezhangzhao on 2018/3/1.
 */

public class ElevatorCartFragment extends BaseFragment implements View.OnClickListener, BlueToothUtil.BTUtilListener, BlueToothUtil.OnCharacteristicListener {

    private String Tag = "ElevatorCartFragment";
    private ACache mCache;
    private ImageView iv_animation;
    private Switch shake_switch;
    private DoughnutProgress circle_progress;
    private RelativeLayout rl_change_gate;
    private TextView tv_name;
    private boolean isNeedShake = true;
    ElevatorListBean doorJinBoBean;
    private String village;
    private int villageType;//1:天津会展 2:和谐景苑
    private boolean connected = false;

    @Override
    public int getLayoutId() {
        return R.layout.frag_elevator_card;
    }

    @Override
    protected void initInject() {
        mCache = ACache.get(getActivity());

        village = mCache.getAsString(HttpConstants.village);

        BlueToothUtil.getInstance().setContext(getActivity());
        BlueToothUtil.getInstance().setBTUtilListener(this);
        BlueToothUtil.getInstance().setOnCharacteristicListener(this);

//        if (village.equals("天津展会")) {
//            villageType = 1;
//
//        } else if (village.equals("和谐景苑")) {
//            villageType = 2;
//        }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_animation:
                circle_progress.start();

           //     BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");

            if (doorJinBoBean == null || doorJinBoBean.isFirst()) {
                BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList = BaseApplication.getInstance().getBlueToothApp().getSearchBlueDeviceBeanList();
                        getDevice(searchBlueDeviceBeanList, 1);
                    }
                }, 2000);
            } else if (doorJinBoBean.getMacType() == 1) {
                JiBoUtils.getInstance(getActivity()).openDevice(doorJinBoBean, openLift(), new JiBoUtils.OnOpenLiftCallBackListenter() {
                    @Override
                    public void OpenLiftCallBackListenter(int backState, String msg) {
                        tv_name.setText(doorJinBoBean.getElevatorNum() + doorJinBoBean.getName() + msg);
                        if (backState == 1) {//成功
                            circle_progress.stop();
                            succssAnimation();
                        } else {//失败
                            circle_progress.stop();
                        }
                    }
                });
            } else if (doorJinBoBean.getMacType() == 2) {
                Log.e(Tag, "doorJinBoBean.getMacType()==" + doorJinBoBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + doorJinBoBean.getMacAddress());
                BlueToothUtil.getInstance().connectLeDevice(doorJinBoBean.getMacAddress());
            }

            break;
            case R.id.rl_change_gate:
                Intent intent = new Intent(getActivity(), ChangeElevatorActivity.class);
                if (doorJinBoBean != null) {
                    intent.putExtra("doorJinBoBean", doorJinBoBean);
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
                doorJinBoBean = (ElevatorListBean) data.getSerializableExtra("elevatorListBean");
                tv_name.setText(doorJinBoBean.getElevatorNum() + doorJinBoBean.getName());
            }
        }

    }

    /**
     * 判断获取的设备是否是米粒蓝牙
     *
     * @param searchBlueDeviceBeanList
     * @param type                     1:点击 2：摇一摇
     */
    private void getDevice(final ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList, final int type) {

        if (BaseApplication.getInstance().getBlueToothApp().isLocationEnalbe()) {
            ToastUtil.showShort("请打开您的定位权限！");
            circle_progress.stop();
            return;
        }
        if (searchBlueDeviceBeanList.size() < 1) {
            ToastUtil.showShort("没有找到蓝牙设备");
            circle_progress.stop();
            return;
        }
        String[] doorMacArr = new String[searchBlueDeviceBeanList.size()];

        for (int i = 0; i < searchBlueDeviceBeanList.size(); i++) {
            doorMacArr[i] = searchBlueDeviceBeanList.get(i).getBluetoothDevice().getAddress();
        }

        ElevatorCardRequestion elevatorCardRequestion = new ElevatorCardRequestion();
        elevatorCardRequestion.setCommunityId("5a82adf3b06c97e0cd6c0f3d");
        elevatorCardRequestion.setDoorMacArr(doorMacArr);

        Api.lanyaElevatorList(elevatorCardRequestion, new ResponseCallBack<List<ElevatorListBean>>() {
            @Override
            public void onSuccess(List<ElevatorListBean> elevatorListBeans) {
                super.onSuccess(elevatorListBeans);
                Log.e(Tag, "doorMILiBeans==" + elevatorListBeans);
                final ElevatorListBean doorJinBoBean = BluetoothUtils.getMaxElevatorRsic(searchBlueDeviceBeanList, elevatorListBeans);
                if (doorJinBoBean != null) {
                    if (doorJinBoBean.getMacType() == 1) {
                        Log.e(Tag, "doorJinBoBean.getMacType()==" + doorJinBoBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + doorJinBoBean.getMacAddress());
                        JiBoUtils.getInstance(getActivity()).openDevice(doorJinBoBean, openLift(), new JiBoUtils.OnOpenLiftCallBackListenter() {
                            @Override
                            public void OpenLiftCallBackListenter(int backState, String msg) {
                                tv_name.setText(doorJinBoBean.getElevatorNum() + doorJinBoBean.getName() + msg);
                                if (backState == 1) {//成功
                                    if (type == 2) {
                                        isNeedShake = true;
                                    }
                                    succssAnimation();
                                    circle_progress.stop();
                                } else {//失败
                                    circle_progress.stop();
                                    if (type == 2) {
                                        isNeedShake = true;
                                    }
                                }
                            }
                        });
                    } else if (doorJinBoBean.getMacType() == 2) {
                        Log.e(Tag, "doorJinBoBean.getMacType()==" + doorJinBoBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + doorJinBoBean.getMacAddress());
                        BlueToothUtil.getInstance().connectLeDevice(doorJinBoBean.getMacAddress());
                    } else {
                        Log.e(Tag, "doorJinBoBean.getMacType()==" + doorJinBoBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + doorJinBoBean.getMacAddress());
                    }
                } else {
                    if (type == 2) {
                        isNeedShake = true;
                    }
                    circle_progress.stop();
                    ToastUtil.showShort("没有搜索到门的设备");
                    Log.e(Tag, "没有搜索到门的设备！");
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
                ToastUtil.showShort(e.getMsg());
                circle_progress.stop();
            }
        });

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
                //  Toast.makeText(getActivity(), "你在摇哦", Toast.LENGTH_SHORT).show();
                Log.e(Tag, "shake==");
                if (isNeedShake) {
                    circle_progress.start();
                    isNeedShake = false;
                    BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList = BaseApplication.getInstance().getBlueToothApp().getSearchBlueDeviceBeanList();
                            getDevice(searchBlueDeviceBeanList, 2);
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
    protected void initEventAndData() {

    }


    @Override
    public void toastMsg(String msg) {

    }

    // 6、组织开梯数据
    public List<Register> openLift() {
        Register reg = new Register();
        reg.PhoneMac = JiBoUtils.getBtAddressViaReflection();
        reg.type = Conf.STATE_DATA_LADDER;
        List<Register> arry = new ArrayList<Register>();
        arry.add(reg);
        return arry;
    }

    public void succssAnimation() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().startActivity(new Intent(getActivity(), EAnimationActivity.class));
            }
        });

    }

    @Override
    public void onLeScanStart() {

    }

    @Override
    public void onLeScanStop() {

    }

    @Override
    public void onLeScanDevice(BluetoothDevice device) {

    }

    @Override
    public void onLeScanDevices(List<BluetoothDevice> listDevice) {

    }

    @Override
    public void onConnected(BluetoothDevice mCurDevice) {
        connected = true;
    }

    @Override
    public void onDisConnected(BluetoothDevice mCurDevice) {
        circle_progress.stop();
        connected = false;
    }

    @Override
    public void onServicesDiscovered() {
        circle_progress.stop();
    }


    @Override
    public void onNotificationSetted() {
        BlueToothUtil.getInstance().sendOpen();
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        BlueToothUtil.getInstance().handleResultCallBack(characteristic, new BlueToothUtil.OnCallBackListener() {
            @Override
            public void OnCallBack(int state) {
                circle_progress.stop();
                if (state == 1) {
                    succssAnimation();
                } else {
                    ToastUtil.showShort("开梯失败");
                }
            }
        });
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

    }
}
