package com.bit.fuxingwuye.activities.fragment.smartGate;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.BaseFragment;

/**
 * Created by Dell on 2018/3/1.
 */

public class FragmentDoor extends BaseFragment {

    public static int currentDoorFragmentPositon=0;
    private RadioGroup rg_gate;
    private BluetoothDoorFragment bluetoothDoorFragment;
    private RemoteDoorFragment remoteDoorFragment;

    @Override
    public void toastMsg(String msg) {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_door;
    }

    @Override
    protected void initEventAndData() {
        rg_gate = (RadioGroup) mView.findViewById(R.id.rg_gate);

        rg_gate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.rb_remote_gate) {
                    currentDoorFragmentPositon=1;
                    checkRemoteDoorFragment();
                } else if (checkedId == R.id.rb_bluetooth) {
                    currentDoorFragmentPositon=0;
                    checkBluetoothDoorFragment();
                }
            }
        });
        rg_gate.check(R.id.rb_bluetooth);
    }


    private void checkBluetoothDoorFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        setAllFragmentHide(fragmentTransaction);
        if (bluetoothDoorFragment == null) {
            bluetoothDoorFragment = new BluetoothDoorFragment();
            fragmentTransaction.add(R.id.rl_content, bluetoothDoorFragment).commit();
        } else {
            fragmentTransaction.show(bluetoothDoorFragment).commit();
        }
    }


    private void checkRemoteDoorFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        setAllFragmentHide(fragmentTransaction);
        if (remoteDoorFragment == null) {
            remoteDoorFragment = new RemoteDoorFragment();
            fragmentTransaction.add(R.id.rl_content, remoteDoorFragment).commit();
        } else {
            fragmentTransaction.show(remoteDoorFragment).commit();
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    public void setAllFragmentHide(FragmentTransaction fragmentTransaction) {
        if (remoteDoorFragment != null) {
            fragmentTransaction.hide(remoteDoorFragment);
        }
        if (bluetoothDoorFragment != null) {
            fragmentTransaction.hide(bluetoothDoorFragment);
        }
    }


}
