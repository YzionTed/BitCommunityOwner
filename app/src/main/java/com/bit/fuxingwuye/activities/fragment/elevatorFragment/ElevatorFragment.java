package com.BIT.fuxingwuye.activities.fragment.elevatorFragment;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.EAnimationActivity;
import com.BIT.fuxingwuye.adapter.EleAdapter;
import com.BIT.fuxingwuye.base.BaseFragment;
import com.BIT.fuxingwuye.bean.ElevatorBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.BlueToothUtil;
import com.BIT.fuxingwuye.views.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElevatorFragment extends BaseFragment<FEPresenter> implements FEContract.View ,
        BlueToothUtil.OnCharacteristicListener,BlueToothUtil.BTUtilListener{

    private LinearLayout ll_no_elelimit;
    private RadioGroup rg_ele;
    private LinearLayout ll_card,ll_remote;
    private RecyclerView grid_elevator;
    private LinearLayout ll_ele_address,ll_ele_floor;
    private TextView tv_ele_address,tv_ele_floor;
    private ImageView iv_up,iv_down;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_elevator;
    }

    @Override
    protected void initEventAndData() {

        BlueToothUtil.getInstance().setContext(getActivity());
        BlueToothUtil.getInstance().setBTUtilListener(this);
        BlueToothUtil.getInstance().setOnCharacteristicListener(this);

        ll_no_elelimit = (LinearLayout)mView.findViewById(R.id.ll_no_elelimit);
        rg_ele = (RadioGroup)mView.findViewById(R.id.rg_ele);
        ll_card = (LinearLayout)mView.findViewById(R.id.ll_card);
        ll_remote = (LinearLayout)mView.findViewById(R.id.ll_remote);
        grid_elevator = (RecyclerView)mView.findViewById(R.id.grid_elevator);
        ll_ele_address = (LinearLayout)mView.findViewById(R.id.ll_ele_address);
        ll_ele_floor = (LinearLayout)mView.findViewById(R.id.ll_ele_floor);
        tv_ele_address = (TextView)mView.findViewById(R.id.tv_ele_address);
        tv_ele_floor = (TextView)mView.findViewById(R.id.tv_ele_floor);
        iv_up = (ImageView)mView.findViewById(R.id.iv_up);
        iv_down = (ImageView)mView.findViewById(R.id.iv_down);

        initHandler();
    }

    private void initHandler() {
        if("3".equals(ACache.get(getActivity()).getAsString(HttpConstants.STATUS))){
            ll_no_elelimit.setVisibility(View.GONE);
        }else{
            ll_no_elelimit.setVisibility(View.VISIBLE);
        }
        rg_ele.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rb_card){
                    ll_card.setVisibility(View.VISIBLE);
                    ll_remote.setVisibility(View.GONE);
                }else if(checkedId==R.id.rb_remote){
                    ll_card.setVisibility(View.GONE);
                    ll_remote.setVisibility(View.VISIBLE);
                }
            }
        });
        grid_elevator.setHasFixedSize(true);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(), 2);
        manager.setSmoothScrollbarEnabled(false);
        grid_elevator.setLayoutManager(manager);
        List<String> lists = new ArrayList<>();
        lists.add("aaaaa");
        lists.add("bbbbb");
        lists.add("ccccc");
        lists.add("ddddd");
        EleAdapter eleAdapter = new EleAdapter(lists);
        grid_elevator.setAdapter(eleAdapter);

        eleAdapter.setOnItemClickListener(new EleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!BluetoothAdapter.getDefaultAdapter().isEnabled()){
                    Toast.makeText(getActivity(),"请打开蓝牙",Toast.LENGTH_SHORT).show();
                }else{
                    BlueToothUtil.getInstance().connectLeDevice("44:A6:E5:14:CE:43");
                    getActivity().startActivity(new Intent(getActivity(), EAnimationActivity.class));
                }

            }
        });
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(getActivity().getApplicationContext(),msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showElevator(ElevatorBean elevatorBean) {

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
