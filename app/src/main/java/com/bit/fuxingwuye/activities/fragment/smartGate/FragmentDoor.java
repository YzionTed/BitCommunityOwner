package com.BIT.fuxingwuye.activities.fragment.smartGate;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.base.BaseFragment;

/**
 * Created by Dell on 2018/3/1.
 */

public class FragmentDoor extends BaseFragment {

    private RadioGroup rg_gate;
    private BluetoothDoorFragment bluetoothDoorFragment;
    private EarlyCallFragment earlyCallFragment;

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
                    checkRemoteDoorFragment();
                } else if (checkedId == R.id.rb_bluetooth) {
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
        if (earlyCallFragment == null) {
            earlyCallFragment = new EarlyCallFragment();
            fragmentTransaction.add(R.id.rl_content, earlyCallFragment).commit();
        } else {
            fragmentTransaction.show(earlyCallFragment).commit();
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    public void setAllFragmentHide(FragmentTransaction fragmentTransaction) {
        if (earlyCallFragment != null) {
            fragmentTransaction.hide(earlyCallFragment);
        }
        if (bluetoothDoorFragment != null) {
            fragmentTransaction.hide(bluetoothDoorFragment);
        }
    }


}
