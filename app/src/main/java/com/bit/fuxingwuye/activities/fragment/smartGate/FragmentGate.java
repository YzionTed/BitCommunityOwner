package com.BIT.fuxingwuye.activities.fragment.smartGate;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.GAnimationActivity;
import com.BIT.fuxingwuye.activities.videoDevices.VideoDevicesActivity;
import com.BIT.fuxingwuye.adapter.FacilityAdapter;
import com.BIT.fuxingwuye.adapter.GateAdapter;
import com.BIT.fuxingwuye.base.BaseFragment;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FacilityBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.BlueToothUtil;
import com.BIT.fuxingwuye.views.DoughnutProgress;
import com.BIT.fuxingwuye.views.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGate extends BaseFragment<FGatePresenter> implements FGateContract.View, SensorEventListener,
        BlueToothUtil.OnCharacteristicListener, BlueToothUtil.BTUtilListener {

    private RecyclerView grid_door;
    private LinearLayout ll_no_gatelimit, ll_bluetooth;
    private DoughnutProgress circle_progress;
    private TextView tip_gate;
    private RadioGroup rg_gate;
    private Switch shake_switch;
    private List<FacilityBean> list = new ArrayList<>();
    private FacilityAdapter mAdapter;
    private ImageView iv_animation;
    private boolean connected = false;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;

    //记录摇动状态
    private boolean isShake = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fragment_gate;
    }

    @Override
    protected void initEventAndData() {

        BlueToothUtil.getInstance().setContext(getActivity());
        BlueToothUtil.getInstance().setBTUtilListener(this);
        BlueToothUtil.getInstance().setOnCharacteristicListener(this);

        ll_no_gatelimit = (LinearLayout) mView.findViewById(R.id.ll_no_gatelimit);
        ll_bluetooth = (LinearLayout) mView.findViewById(R.id.ll_bluetooth);
        if ("3".equals(ACache.get(getActivity()).getAsString(HttpConstants.STATUS))) {
            ll_no_gatelimit.setVisibility(View.GONE);
        } else {
            ll_no_gatelimit.setVisibility(View.VISIBLE);
        }
        tip_gate = (TextView) mView.findViewById(R.id.tip_gate);
        rg_gate = (RadioGroup) mView.findViewById(R.id.rg_gate);
        grid_door = (RecyclerView) mView.findViewById(R.id.grid_door);
        circle_progress = (DoughnutProgress) mView.findViewById(R.id.circle_progress);
        iv_animation = (ImageView) mView.findViewById(R.id.iv_animation);
        shake_switch = (Switch) mView.findViewById(R.id.shake_switch);

        rg_gate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_remote_gate) {
                    grid_door.setVisibility(View.VISIBLE);
                    ll_bluetooth.setVisibility(View.GONE);
                    tip_gate.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_bluetooth) {
                    grid_door.setVisibility(View.GONE);
                    ll_bluetooth.setVisibility(View.VISIBLE);
                    tip_gate.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected) {
                    if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                        Toast.makeText(getActivity(), "请打开蓝牙", Toast.LENGTH_SHORT).show();
                    } else {
                        circle_progress.start();
                        BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");
                    }
                    connected = true;
                }
            }
        });

        shake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
                    if (mSensorManager != null) {
                        //获取加速度传感器
                        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                        if (mAccelerometerSensor != null) {
                            mSensorManager.registerListener(FragmentGate.this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
                        }
                    }
                } else {
                    if (mSensorManager != null) {
                        mSensorManager.unregisterListener(FragmentGate.this);
                    }
                }
            }
        });

        grid_door.setHasFixedSize(true);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(), 2);
        manager.setSmoothScrollbarEnabled(false);
        grid_door.setLayoutManager(manager);

        CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(getActivity()).getAsString(HttpConstants.USERID));
        // mPresenter.getDoors(commonBean);
    }


    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDoors(List<FacilityBean> facilityBeanList) {
        list = facilityBeanList;
        mAdapter = new FacilityAdapter(list);

        grid_door.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FacilityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                getActivity().startActivity(new Intent(getActivity(), GAnimationActivity.class));
                getActivity().startActivity(new Intent(getActivity(), VideoDevicesActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    public void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SensorEventListener回调方法
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17) && !isShake) {
                isShake = true;
                // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        super.run();
                        try {
                            Log.d("gate", "onSensorChanged: 摇动");

                            if (!connected) {
                                circle_progress.start();
                                Looper.prepare();
                                BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");
                                Looper.loop();
                                connected = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

    }

    @Override
    public void onDisConnected(BluetoothDevice mCurDevice) {
        circle_progress.stop();
        connected = false;
        isShake = false;
    }

    @Override
    public void onServicesDiscovered() {

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
        BlueToothUtil.getInstance().handleResult(characteristic);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

    }
}
