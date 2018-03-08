package com.bit.fuxingwuye.activities.fragment.elevatorFrag;

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

import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.fuxingwuye.Bluetooth.BluetoothApplication;
import com.bit.fuxingwuye.Bluetooth.bean.SearchBlueDeviceBean;
import com.bit.fuxingwuye.Bluetooth.jinbo.JiBoUtils;
import com.bit.fuxingwuye.Bluetooth.util.BluetoothUtils;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.EAnimationActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseFragment;
import com.bit.fuxingwuye.bean.ElevatorCardRequestion;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.BlueToothUtil;
import com.bit.fuxingwuye.utils.SensorManagerHelper;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.views.CircleProgressBar;
import com.bit.fuxingwuye.views.DoughnutProgress;
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

    private Switch shake_switch;
    private CircleProgressBar circle_progress;
    private RelativeLayout rl_change_gate;
    private TextView tv_name;
    private boolean isNeedShake = true;
    ElevatorListBean doorJinBoBean;
    ElevatorListBean haiKangdoorJinBoBean;
    private String village;
    private int villageType;//1:天津会展 2:和谐景苑
    private boolean connected = false;
    private ImageView iv_open;

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


        circle_progress = (CircleProgressBar) mView.findViewById(R.id.loading_view);
        iv_open = (ImageView) mView.findViewById(R.id.iv_open);
        shake_switch = (Switch) mView.findViewById(R.id.shake_switch);
        rl_change_gate = (RelativeLayout) mView.findViewById(R.id.rl_change_gate);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);


        rl_change_gate.setOnClickListener(this);
        iv_open.setOnClickListener(this);

        initListener();
        shake_switch.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_open:

                circle_progress.start();

                // doorJinBoBean.setKeyType(1);
                //    BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");
////                   doorJinBoBean=new ElevatorListBean();
////                   doorJinBoBean.setMacAddress("E6:48:3C:5A:D4:1D");
////                   doorJinBoBean.setMacType(1);
////                   doorJinBoBean.setKeyNo("123456123457");
//
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (doorJinBoBean == null || doorJinBoBean.isFirst()) {
                            BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000, new BluetoothApplication.CallBack() {
                                @Override
                                public void onCall(ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList) {
                                    getDevice(searchBlueDeviceBeanList, 1);
                                }
                            });
                        } else if (doorJinBoBean.getMacType() == 1) {
                            JiBoUtils.getInstance(getActivity()).openDevice(doorJinBoBean, openLift(doorJinBoBean), new JiBoUtils.OnOpenLiftCallBackListenter() {
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
                            haiKangdoorJinBoBean = doorJinBoBean;
                            BlueToothUtil.getInstance().connectLeDevice(haiKangdoorJinBoBean.getMacAddress());
                        }
                    }
                }.start();

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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort("请打开您的定位权限！");
                    circle_progress.stop();
                }
            });

            return;
        }
        if (searchBlueDeviceBeanList.size() < 1) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort("没有找到蓝牙设备");
                    circle_progress.stop();
                }
            });

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
                        JiBoUtils.getInstance(getActivity()).openDevice(doorJinBoBean, openLift(doorJinBoBean), new JiBoUtils.OnOpenLiftCallBackListenter() {
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                haiKangdoorJinBoBean = doorJinBoBean;
                                BlueToothUtil.getInstance().connectLeDevice(haiKangdoorJinBoBean.getMacAddress());
                            }
                        });
                    } else {
                        Log.e(Tag, "doorJinBoBean.getMacType()==" + doorJinBoBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + doorJinBoBean.getMacAddress());
                    }
                } else {
                    if (type == 2) {
                        isNeedShake = true;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circle_progress.stop();
                            ToastUtil.showShort("没有搜索到门的设备");
                            Log.e(Tag, "没有搜索到门的设备！");
                        }
                    });

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
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            isNeedShake = false;
                            BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000, new BluetoothApplication.CallBack() {
                                @Override
                                public void onCall(ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList) {
                                    getDevice(searchBlueDeviceBeanList, 2);
                                }
                            });
                        }
                    }.start();

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList = BaseApplication.getInstance().getBlueToothApp().getSearchBlueDeviceBeanList();
//
//                        }
//                    }, 2000);
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
        BlueToothUtil.getInstance().setOnBluetoothStateCallBack(new BlueToothUtil.OnBluetoothStateCallBack() {
            @Override
            public void OnBluetoothState(final String state) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_name.setText(state);
                    }
                });
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
    public List<Register> openLift(ElevatorListBean doorJinBoBean) {
        Register reg = new Register();
        if (doorJinBoBean.getKeyNo().length() == 12) {
            String keyNo = doorJinBoBean.getKeyNo();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < keyNo.length() / 2; i++) {
                String a = keyNo.substring(2 * i, 2 * i + 2);
                stringBuffer.append(a + ":");
            }
            String stringBuffer1 = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
            Log.e(" reg.PhoneMac", " reg.PhoneMac==" + stringBuffer1);
            // reg.PhoneMac = "12:34:56:12:34:57";
            reg.PhoneMac = stringBuffer1;
        } else {
            reg.PhoneMac = doorJinBoBean.getKeyNo();
        }

        reg.type = Conf.STATE_DATA_LADDER;
        List<Register> arry = new ArrayList<Register>();
        arry.add(reg);
        return arry;
    }

    /**
     * 成功开梯动画
     */
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                circle_progress.stop();
                connected = false;
            }
        });

    }

    @Override
    public void onServicesDiscovered() {
        circle_progress.stop();
    }


    @Override
    public void onNotificationSetted() {
        BlueToothUtil.getInstance().sendOpen(haiKangdoorJinBoBean);
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
        if (status == BluetoothGatt.GATT_SUCCESS) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    succssAnimation();
                }
            });
            //  Log.e(Tag,"写入数据成功");
        }
    }
}
