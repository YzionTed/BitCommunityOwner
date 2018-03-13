package com.bit.fuxingwuye.activities.fragment.elevatorFrag;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bit.fuxingwuye.Bluetooth.BluetoothApplication;
import com.bit.fuxingwuye.Bluetooth.bean.SearchBlueDeviceBean;
import com.bit.fuxingwuye.Bluetooth.jinbo.JiBoUtils;
import com.bit.fuxingwuye.Bluetooth.util.BluetoothUtils;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.EAnimationActivity;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.activities.fragment.smartGate.BluetoothNetUtils;
import com.bit.fuxingwuye.activities.fragment.smartGate.FragmentDoor;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseFragment;
import com.bit.fuxingwuye.bean.CardListBean;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.BlueToothUtil;
import com.bit.fuxingwuye.utils.LiteOrmUtil;
import com.bit.fuxingwuye.utils.SensorManagerHelper;
import com.bit.fuxingwuye.utils.ToastUtil;
import com.bit.fuxingwuye.views.CircleProgressBar;
import com.inuker.bluetooth.library.base.Conf;
import com.inuker.bluetooth.library.base.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 梯禁卡
 * Created by kezhangzhao on 2018/3/1.
 */

public class ElevatorCartFragment extends BaseFragment implements View.OnClickListener, BlueToothUtil.BTUtilListener, BlueToothUtil.OnCharacteristicListener {

    private String Tag = "ElevatorCartFragment";

    private Switch shake_switch;
    private CircleProgressBar circle_progress;
    private RelativeLayout rl_change_gate;
    private TextView tv_name;

    ElevatorListBean doorJinBoBean;
    ElevatorListBean openDoorBean;

    private int villageType;//1:和谐景苑  2: 天津会展 3.虚拟小区 4.包头市九原区和谐警苑小区
    private boolean connected = false;
    private ImageView iv_open;
    private BluetoothNetUtils bluetoothNetUtils;

    private String haiKangOpenKeyNo;//自己公司电梯的开梯mac
    private TextView tv_open_type;
    public boolean isChecked;//开关是否打开

    @Override
    public int getLayoutId() {
        return R.layout.frag_elevator_card;
    }

    @Override
    protected void initInject() {

        BlueToothUtil.getInstance().setContext(getActivity());
        BlueToothUtil.getInstance().setBTUtilListener(this);
        BlueToothUtil.getInstance().setOnCharacteristicListener(this);

        BaseApplication.getInstance().getBlueToothApp().checkLocationEnable(getActivity());
        BaseApplication.getInstance().getBlueToothApp().openBluetooth();

        circle_progress = (CircleProgressBar) mView.findViewById(R.id.loading_view);
        iv_open = (ImageView) mView.findViewById(R.id.iv_open);
        shake_switch = (Switch) mView.findViewById(R.id.shake_switch);
        rl_change_gate = (RelativeLayout) mView.findViewById(R.id.rl_change_gate);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        tv_open_type = (TextView) mView.findViewById(R.id.tv_open_type);

        rl_change_gate.setOnClickListener(this);
        iv_open.setOnClickListener(this);

        if (bluetoothNetUtils == null) {
            bluetoothNetUtils = new BluetoothNetUtils(getActivity());
        }

        getCommutiyType();
        initListener();
        shake_switch.setChecked(false);
    }

    /**
     * 获取小区的类别
     */
    private void getCommutiyType() {
        if ("5a82adf3b06c97e0cd6c0f3d".equals(BaseApplication.getVillageInfo().getId())) {    //和谐景苑
            villageType = 1;
        } else if ("5a8cfa62518089ae7afccc0c".equals(BaseApplication.getVillageInfo().getId())) {//天津展会
            villageType = 2;
        } else if ("5a8cfc54518089ae7afccc0d".equals(BaseApplication.getVillageInfo().getId())) {//虚拟小区
            villageType = 3;
        } else if ("5aa1ea6ce4b071c9f9b6f1a8".equals(BaseApplication.getVillageInfo().getId())) {//虚拟小区
            villageType = 4;
        }

        if (villageType == 1 || villageType == 3) {
            tv_open_type.setText("自动感应开梯");
        } else {
            tv_open_type.setText("摇一摇开梯");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_open:
                tv_name.setText("开始开梯");
                circle_progress.start();

                // doorJinBoBean.setKeyType(1);
                //    BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");
////                   doorJinBoBean=new ElevatorListBean();
////                   doorJinBoBean.setMacAddress("E6:48:3C:5A:D4:1D");
////                   doorJinBoBean.setMacType(1);
////                   doorJinBoBean.setKeyNo("123456123457");

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
                        } else if (doorJinBoBean.getMacType() == 1 || doorJinBoBean.getMacType() == 2) {
                            openDoorBean = doorJinBoBean;
                            openElevator(doorJinBoBean.getMacAddress(), 1);
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
                tv_name.setText(doorJinBoBean.getName());
            }
        }

    }

    /**
     * 判断获取的设备是否是米粒蓝牙
     *
     * @param searchBlueDeviceBeanList fromType 1:点击 2：摇一摇
     */
    private void getDevice(final ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList, final int fromType) {

        if (BaseApplication.getInstance().getBlueToothApp().isLocationEnalbe()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort("请打开您的定位权限！");
                    if (fromType == 1) {
                        isYaoyiYao = true;
                    }
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
                    if (fromType == 1) {
                        isYaoyiYao = true;
                    }
                }
            });

            return;
        }
        StoreElevatorListBeans bletoothElevateDate = bluetoothNetUtils.getBletoothElevateDate();
        if (bletoothElevateDate != null) {
            if (bletoothElevateDate.getElevatorListBeans() != null) {
                if (bletoothElevateDate.getElevatorListBeans().size() > 0) {//缓存不为空时
                    final ElevatorListBean doorJinBoBean = BluetoothUtils.getMaxElevatorRsic(searchBlueDeviceBeanList, bletoothElevateDate.getElevatorListBeans());

                    if (doorJinBoBean != null) {//匹配最强信号
                        openDoorBean = doorJinBoBean;
                        openElevator(doorJinBoBean.getMacAddress(), fromType);
                    } else {//如果搜索设备的数组个数为0,则选择蓝牙信号最强的1个蓝牙设备Mac地址和虚
                        if (searchBlueDeviceBeanList.size() > 0) {
                            CardListBean.RecordsBean cardListBean = LiteOrmUtil.getInstance().queryById(BaseApplication.getUserLoginInfo().getId(), BaseApplication.getVillageInfo().getId());
                            if (cardListBean == null) {
                                Log.e(Tag, "获取极光推动的缓存为空");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showShort("没找到您可以开的电梯");
                                        circle_progress.stop();
                                    }
                                });
                                return;
                            }
                            isSuccess = false;
                            openElevatorByAuto(searchBlueDeviceBeanList, 0, fromType);
                        }
                    }
                } else {
                    getElevateDate(searchBlueDeviceBeanList, fromType);
                }
            } else {
                getElevateDate(searchBlueDeviceBeanList, fromType);
            }
        } else {
            getElevateDate(searchBlueDeviceBeanList, fromType);
        }

    }

    /**
     * 获取网络的数据
     *
     * @param searchBlueDeviceBeanList fromType1.点击 2，摇一摇
     */
    private void getElevateDate(final ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList, final int fromType) {
        bluetoothNetUtils.getBluetoothElevatorDate(1, new BluetoothNetUtils.OnBlutoothElevatorCallBackListener() {
            @Override
            public void OnCallBack(int state, StoreElevatorListBeans storeDoorMILiBeanList) {
                if (state == 1) {
                    final ElevatorListBean doorJinBoBean = BluetoothUtils.getMaxElevatorRsic(searchBlueDeviceBeanList, storeDoorMILiBeanList.getElevatorListBeans());
                    if (doorJinBoBean != null) {
                        openDoorBean = doorJinBoBean;
                        openElevator(doorJinBoBean.getMacAddress(), fromType);
                    } else {
                        CardListBean.RecordsBean cardListBean = LiteOrmUtil.getInstance().queryById(BaseApplication.getUserLoginInfo().getId(), BaseApplication.getVillageInfo().getId());
                        if (cardListBean != null) {
                            Log.e(Tag, "获取极光推动的缓存为空");
                            ToastUtil.showShort("没找到您可以开的电梯");
                            circle_progress.stop();
                            return;
                        }
                        isSuccess = false;
                        openElevatorByAuto(searchBlueDeviceBeanList, 0, fromType);
                    }
                } else if (state == 2) {
                    tv_name.setText("开梯失败,重新开梯");
                    circle_progress.stop();
                }
            }
        });
    }

    /**
     * 打开电梯
     */
    private void openElevator(final String macAddress, final int fromType) {

        if (openDoorBean != null) {
            if (openDoorBean.getMacType() == 1) {
                Log.e(Tag, "匹配金箔：" + openDoorBean.getMacType() + "  openDoorBean.getMacAddress() =" + openDoorBean.getMacAddress());
                JiBoUtils.getInstance(getActivity()).openDevice(macAddress, openLift(openDoorBean), new JiBoUtils.OnOpenLiftCallBackListenter() {
                    @Override
                    public void OpenLiftCallBackListenter(int backState, String msg) {
                        tv_name.setText(openDoorBean.getName() + msg);
                        if (backState == 1) {//成功
                            succssAnimation();
                            if (fromType == 1) {
                                isYaoyiYao = true;
                            }
                            circle_progress.stop();
                        } else {//失败
                            if (fromType == 1) {
                                isYaoyiYao = true;
                            }
                            circle_progress.stop();

                        }
                    }
                });
            } else if (openDoorBean.getMacType() == 2) {
                Log.e(Tag, "匹配海康：" + openDoorBean.getMacType() + "  doorJinBoBean.getMacAddress() =" + openDoorBean.getMacAddress());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        haiKangOpenKeyNo = openDoorBean.getKeyNo();
                        BlueToothUtil.getInstance().connectLeDevice(openDoorBean.getMacAddress());
                    }
                });
            } else {
                Log.e(Tag, "openDoorBean.getMacType()==" + openDoorBean.getMacType() + "  openDoorBean.getMacAddress() =" + openDoorBean.getMacAddress());
            }
        }
    }

    /**
     * 打开电梯
     * 5秒自动连接开梯
     */
    boolean isSuccess = false;//最近的两个蓝牙 进行匹配
    // 初始化定时器
    Timer timer;

    private void openElevatorByAuto(final ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList, int postion, final int fromType) {
        if (searchBlueDeviceBeanList.size() == 0) {
            Log.e(Tag, "自动匹配蓝牙 searchBlueDeviceBeanList.size");
            return;
        }
        final String macAddress = searchBlueDeviceBeanList.get(postion).getBluetoothDevice().getAddress();

        Log.e(Tag, "自动匹配蓝牙：" + macAddress);
        if (openLift().size() == 0) {
            ToastUtil.showShort("没有找到您可以开的电梯");
            circle_progress.stop();
            return;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isSuccess) {
                    if (searchBlueDeviceBeanList.size() > 1) {
                        isSuccess = false;
                        openElevatorByAuto(searchBlueDeviceBeanList, 1, fromType);
                    }
                }
                if (timer != null) {
                    timer.cancel();
                    // 一定设置为null，否则定时器不会被回收
                    timer = null;
                }
                Log.e("lzp", "timer excute");
            }
        }, 3000);

        JiBoUtils.getInstance(getActivity()).openDevice(macAddress, openLift(), new JiBoUtils.OnOpenLiftCallBackListenter() {
            @Override
            public void OpenLiftCallBackListenter(int backState, String msg) {
                tv_name.setText(msg);
                if (backState == 1) {//成功
                    isSuccess = true;
                    succssAnimation();
                    if (fromType == 1) {
                        isYaoyiYao = true;
                    }
                    circle_progress.stop();
                } else {//失败
                    if (fromType == 1) {
                        isYaoyiYao = true;
                    }
                    circle_progress.stop();
                }
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (BaseApplication.getVillageInfo() != null && BaseApplication.getUserLoginInfo() != null) {
                    if (BaseApplication.getVillageInfo().getId() != null && BaseApplication.getUserLoginInfo().getId() != null)
                        ;
                    CardListBean.RecordsBean cardListBean = LiteOrmUtil.getInstance().queryById(BaseApplication.getUserLoginInfo().getId(), BaseApplication.getVillageInfo().getId());
                    if (cardListBean != null) {
                        haiKangOpenKeyNo = cardListBean.getKeyNo();
                        BlueToothUtil.getInstance().connectLeDevice(macAddress);
                    } else {
                        circle_progress.stop();
                        tv_name.setText("没找您可以开的电梯");
                    }
                }
            }
        });
    }


    /**
     * 监听
     * fromType 1.点击 2，摇一摇
     */
    boolean isYaoyiYao = true;

    private void initListener() {

        SensorManagerHelper sensorHelper = new SensorManagerHelper(getActivity());
        sensorHelper.setOnShakeListener(new SensorManagerHelper.OnShakeListener() {

            @Override
            public void onShake() {

                if (villageType == 1 || villageType == 3) {//天津展会 虚拟小区){
                    return;
                }
                if (!isChecked) {
                    return;
                }
                if (MainTabActivity.currentFragmentPosiont != 2 || FragmentDoor.currentDoorFragmentPositon != 0) {
                    return;
                }
                Log.e(Tag, "shake==");

                if (isYaoyiYao) {
                    isYaoyiYao = false;
                    circle_progress.start();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            if (doorJinBoBean == null || doorJinBoBean.isFirst()) {
                                BaseApplication.getInstance().getBlueToothApp().scanBluetoothDevice(2000, new BluetoothApplication.CallBack() {
                                    @Override
                                    public void onCall(ArrayList<SearchBlueDeviceBean> searchBlueDeviceBeanList) {
                                        getDevice(searchBlueDeviceBeanList, 2);
                                    }
                                });
                            } else if (doorJinBoBean.getMacType() == 1 || doorJinBoBean.getMacType() == 2) {
                                openDoorBean = doorJinBoBean;
                                openElevator(doorJinBoBean.getMacAddress(), 2);
                            }
                        }
                    }.start();
                }
            }
        });

        shake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(Tag, "isChecked==" + isChecked);
                ElevatorCartFragment.this.isChecked = isChecked;
                if (villageType == 1 || villageType == 3) {//天津展会 虚拟小区
                    //  tv_open_type.setText("自动感应开梯");
                    autoGanyingOpen(isChecked);
                } else {
                    tv_open_type.setText("摇一摇开梯");
                }
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

    /**
     * 自动感应开梯
     *
     * @param isChecked
     */
    private void autoGanyingOpen(boolean isChecked) {

        if (Build.VERSION.SDK_INT >= 21) {
            if (isChecked) {
                StoreElevatorListBeans bletoothElevateDate = bluetoothNetUtils.getBletoothElevateDate();
                if (bletoothElevateDate != null) {
                    if (bletoothElevateDate.getElevatorListBeans() != null) {
                        if (bletoothElevateDate.getElevatorListBeans().size() > 0) {//当缓存不为o
                            if (bletoothElevateDate.getElevatorListBeans().get(0).isFirst()) {
                                BlueToothUtil.getInstance().openBroadcast(bletoothElevateDate.getElevatorListBeans().get(1).getKeyNo());
                            } else {
                                BlueToothUtil.getInstance().openBroadcast(bletoothElevateDate.getElevatorListBeans().get(0).getKeyNo());
                            }
                        }
                    } else {
                        Log.e(Tag, "bletoothElevateDate.getElevatorListBeans()==null  keyNo==null");
                    }
                } else {
                    Log.e(Tag, "bletoothElevateDate==null  keyNo==null");
                }
            } else {
                BlueToothUtil.getInstance().closeBroadcast();
            }
        } else {
            ToastUtil.showShort("只支持版本sdk 21以上的");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void initEventAndData() {

    }


    @Override
    public void toastMsg(String msg) {

    }

    // 组织开梯数据
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

    // 组织开梯数据
    public List<Register> openLift() {
        Register reg = new Register();
        StoreElevatorListBeans bletoothElevateDate = bluetoothNetUtils.getBletoothElevateDate();
        String keyNo = "";
        if (bletoothElevateDate != null) {
            if (bletoothElevateDate.getElevatorListBeans() != null) {
                if (bletoothElevateDate.getElevatorListBeans().get(0).isFirst()) {
                    if (bletoothElevateDate.getElevatorListBeans().get(1).getKeyNo() != null) {
                        keyNo = bletoothElevateDate.getElevatorListBeans().get(1).getKeyNo();
                    }
                } else {
                    if (bletoothElevateDate.getElevatorListBeans().get(0).getKeyNo() != null) {
                        keyNo = bletoothElevateDate.getElevatorListBeans().get(0).getKeyNo();
                    }
                }

            } else {
                CardListBean.RecordsBean cardListBean = LiteOrmUtil.getInstance().queryById(BaseApplication.getUserLoginInfo().getId(), BaseApplication.getVillageInfo().getId());
                if (cardListBean.getKeyNo() != null) {
                    keyNo = cardListBean.getKeyNo();
                }
            }
        } else {
            CardListBean.RecordsBean cardListBean = LiteOrmUtil.getInstance().queryById(BaseApplication.getUserLoginInfo().getId(), BaseApplication.getVillageInfo().getId());
            if (cardListBean.getKeyNo() != null) {
                keyNo = cardListBean.getKeyNo();
            }
        }
        if (keyNo != null && keyNo.trim().length() > 0) {
            if (keyNo.trim().length() == 12) {

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
                reg.PhoneMac = keyNo;
            }
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
        tv_name.setText("开梯成功！");
        isSuccess = true;
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
                isYaoyiYao = true;
                circle_progress.stop();
                connected = false;
            }
        });

    }

    @Override
    public void onServicesDiscovered() {
        isYaoyiYao = true;
        circle_progress.stop();
    }


    @Override
    public void onNotificationSetted() {
        BlueToothUtil.getInstance().sendOpen(haiKangOpenKeyNo);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        BlueToothUtil.getInstance().handleResultCallBack(characteristic, new BlueToothUtil.OnCallBackListener() {
            @Override
            public void OnCallBack(final int state) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circle_progress.stop();
                        isYaoyiYao = true;
                        if (state == 1) {
                            isSuccess = true;
                            tv_name.setText("开梯成功");
                            succssAnimation();
                        } else {
                            ToastUtil.showShort("开梯失败");
                        }
                    }
                });

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
