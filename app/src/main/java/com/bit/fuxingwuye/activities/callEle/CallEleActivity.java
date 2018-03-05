package com.bit.fuxingwuye.activities.callEle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.adapter.BtAdapter;
import com.bit.fuxingwuye.adapter.GateAdapter;
import com.bit.fuxingwuye.adapter.HouseholdAdapter;
import com.bit.fuxingwuye.base.BaseActivity;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.ElevatorBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityCallEleBinding;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.views.FullyGridLayoutManager;
import com.inuker.bluetooth.library.BluetoothClientManger;
import com.inuker.bluetooth.library.base.CardLog;
import com.inuker.bluetooth.library.base.Conf;
import com.inuker.bluetooth.library.base.Register;
import com.inuker.bluetooth.library.search.SearchResult;

import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CallEleActivity extends BaseActivity<CallElePresenterImpl> implements CallEleContract.View,
        View.OnClickListener,BluetoothClientManger.BleConnectLister,BluetoothClientManger.BleScanLister,
        BluetoothClientManger.BleNotifyLister,BluetoothClientManger.BleCloseNotifyLister,
        BluetoothClientManger.BleWriteLister{

    private static final String TAG = "CallEleActivity";
    private BluetoothClientManger mBluetoothClientManger;
    private ActivityCallEleBinding mBinding;
    private BtAdapter mAdapter;
    private String currentMac;
    List<BluetoothDevice> mac = new ArrayList<>();
    private GateAdapter gateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_call_ele);
        mBluetoothClientManger = new BluetoothClientManger(this);
        mBluetoothClientManger.setBleConnectLister(this);
        mBluetoothClientManger.setBleNotifyLister(this);
        mBluetoothClientManger.setBleScanLister(this);
        CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(this).getAsString(HttpConstants.USERID));
        mPresenter.getElevator(commonBean);
    }

    @Override
    protected void setupVM() {
        mBinding.toolbar.actionBarTitle.setText("智能梯控");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
        mBinding.rgEle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_bluetooth){
                    mBinding.llBluetooth.setVisibility(View.VISIBLE);
                    mBinding.llRemote.setVisibility(View.GONE);
                }else if(checkedId == R.id.rb_remote){
                    mBinding.llBluetooth.setVisibility(View.GONE);
                    mBinding.llRemote.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    protected void setupHandlers() {

        mBinding.toolbar.btnBack.setOnClickListener(this);
        mBinding.btnC.setOnClickListener(this);
        mBinding.btnD.setOnClickListener(this);
        mBinding.btnE.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.eleRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.eleRecyclerView.setNestedScrollingEnabled(false);
        mBinding.eleRecyclerView.setFocusable(false);
        mAdapter = new BtAdapter(mac);
        mBinding.eleRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HouseholdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BluetoothDevice device = mAdapter.getDevices().get(position);
                Log.e("onclick",device.getAddress());
                if(device.getBondState()==BluetoothDevice.BOND_NONE){
                    try {
                        Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                        Log.d("BlueToothTestActivity", "开始配对");
                        mBinding.tvState.setText("开始配对");
                        try {
                            createBondMethod.invoke(device);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("boundstate",device.getBondState()+"");
                    mBinding.tvState.setText("本机"+getBtAddressViaReflection()+"开始连接"+device.getAddress());
                    mBluetoothClientManger.connect(device.getAddress());
                }
            }
        });

        gateAdapter.setOnItemClickListener(new GateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("ddddddddd",position+"");
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_c:
                if(mBluetoothClientManger.isBleSupported()){
                    if(!mBluetoothClientManger.isBluetoothOpened()){
                        mBluetoothClientManger.openBluetooth();
                    }
                    mBluetoothClientManger.searchDevice();
                }else{
                    Log.e("notsupport","not support");
                }

                break;
            case R.id.btn_d:
                Register reg = new Register();
                reg.setPhoneMac(getBtAddressViaReflection());
                reg.setFloors("6");
                reg.setIsSjxz(false);
                reg.setIsDirect(true);
                reg.setIsManager(false);
                reg.setYxqStart("20161222000000");
                reg.setYxqEnd("20171222000000");
                reg.setTimeStart("0000");
                reg.setTimeEnd("2359");
                reg.setWeeks("01111111");
                reg.setRoomnum("0401");
                reg.setUTCTime(getFormatUTCTime());
                Log.e("time",getFormatUTCTime());
                reg.setType(Conf.STATE_DATA_REGISTERED);
                List<Register> arry1 = new ArrayList<Register>();
                arry1.add(reg);
                mBluetoothClientManger.write(currentMac,arry1);
                break;
            case R.id.btn_e:
                Register register = new Register();
                register.setPhoneMac(getBtAddressViaReflection());
                register.setType(Conf.STATE_DATA_LADDER);
                List<Register> arry2 = new ArrayList<Register>();
                arry2.add(register);
                mBluetoothClientManger.write(currentMac,arry2);
                break;
            default:
                break;
        }
    }

    private String getFormatUTCTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        String formattime = sdf.format(date);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="07";
        }else if("2".equals(mWay)){
            mWay ="01";
        }else if("3".equals(mWay)){
            mWay ="02";
        }else if("4".equals(mWay)){
            mWay ="03";
        }else if("5".equals(mWay)){
            mWay ="04";
        }else if("6".equals(mWay)){
            mWay ="05";
        }else if("7".equals(mWay)){
            mWay ="06";
        }
        return formattime+mWay;
    }

    @Override
    public void connectedResult(Boolean aBoolean, String s) {
        if (aBoolean){
            Log.e("connect","success"+s);
            currentMac = s;
            mBinding.tvState.setText(s+"----->connected");
            mBluetoothClientManger.openNotify(s);
        }else{
            Log.e("connect","fail"+s);
            mBinding.tvState.setText(s+"----->连接失败");
        }
    }

    @Override
    public void closeNotifyOnResponse(boolean b) {
        Log.e("closeNotifyOnResponse","......"+b);
    }

    @Override
    public void onNotifyResult(Register register) {
        mBinding.tvState2.setText("register.type="+register.type+",register.type="+register.PhoneMac+",register.register="+register.register);
        Log.e("type","register.type="+register.type+",register.type="+register.PhoneMac+",register.register="+register.register);
    }

    @Override
    public void noNewNotifyResutlt(String s) {
        Log.e("noNewNotifyResutlt","......"+s);
    }

    @Override
    public void dealOver(boolean b) {

    }

    @Override
    public void onResponse(boolean b) {
        if (b){
            Log.e("notify","opened");
//            Register register = new Register();
//            register.setPhoneMac(getBtAddressViaReflection());
//            register.setType(Conf.STATE_DATA_LADDER);
//            List<Register> arry2 = new ArrayList<Register>();
//            arry2.add(register);
//            mBluetoothClientManger.write(currentMac,arry2);
            Toast.makeText(this,"连接成功",Toast.LENGTH_SHORT).show();
            mBinding.tvState2.setText("开始注册");
            Register reg = new Register();
            reg.setPhoneMac(getBtAddressViaReflection());
            reg.setFloors("6");
            reg.setIsSjxz(false);
            reg.setIsDirect(true);
            reg.setIsManager(false);
            reg.setYxqStart("20161222000000");
            reg.setYxqEnd("20171222000000");
            reg.setTimeStart("0000");
            reg.setTimeEnd("2359");
            reg.setWeeks("01111111");
            reg.setRoomnum("0401");
            reg.setUTCTime(getFormatUTCTime());
            Log.e("time",getFormatUTCTime());
            reg.setType(Conf.STATE_DATA_REGISTERED);
            List<Register> arry1 = new ArrayList<Register>();
            arry1.add(reg);
            mBluetoothClientManger.write(currentMac,arry1);
        }else{
            Log.e("notify","closed");
        }
    }

    @Override
    public void onLadderNotifyResult(CardLog cardLog) {

    }

    @Override
    public void onLadderOverNotifyResult(boolean b) {
        Log.e("onLadderOverNotify","......"+b);
    }

    @Override
    public void onSearchStarted() {
        Log.e("onSearchStarted","started");
    }

    @Override
    public void onDeviceFounded(SearchResult searchResult) {
        Log.e("device",searchResult.toString());
        boolean isAdded = false;
        for (BluetoothDevice device1 : mAdapter.getDevices()) {
            if (device1 == null) continue;
            if (searchResult.device.getAddress().equals(device1.getAddress())) {
                isAdded = true;
                break;
            }
        }
        if (!isAdded && searchResult.device != null && !TextUtils.isEmpty(searchResult.device.getAddress())) {
            mAdapter.addBleDevice(searchResult.device);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSearchStopped() {
        Log.e("device","onSearchStopped");
        Toast.makeText(this,"扫描完成",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchCanceled() {
        Log.e("onSearchCanceled","canceled");
    }

    @Override
    public void writerOnResponse(boolean b) {
        Log.e("writerOnResponse","...."+b);
    }

    private String getBtAddressViaReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Object bluetoothManagerService = new Mirror().on(bluetoothAdapter).get().field("mService");
        if (bluetoothManagerService == null) {
            Log.w(TAG, "couldn't find bluetoothManagerService");
            return null;
        }
        Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();
        if (address != null && address instanceof String) {
            Log.e(TAG, "using reflection to get the BT MAC address: " + address);
            return (String) address;
        } else {
            return null;
        }
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showElevator(ElevatorBean elevatorBean) {
        mBinding.rvElevators.setHasFixedSize(true);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3);
        manager.setSmoothScrollbarEnabled(false);
        mBinding.rvElevators.setLayoutManager(manager);
        List<String> lists = new ArrayList<>();
        lists.add("aaaaa");
        lists.add("bbbbb");
        lists.add("ccccc");
        lists.add("ddddd");
        gateAdapter = new GateAdapter(lists);
        mBinding.rvElevators.setAdapter(gateAdapter);
    }
}
