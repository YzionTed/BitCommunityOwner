package com.BIT.fuxingwuye.activities.fragment.elevatorFrag;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.BIT.fuxingwuye.R;

import com.BIT.fuxingwuye.base.BaseFragment;

/**
 * Created by Dell on 2018/3/3.
 */

public class ElevatorFragment extends BaseFragment {
    private RadioGroup rg_gate;
    private ElevatorCartFragment elevatorCartFragment;
    private EarlyCallFragment earlyCallFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_frag_elevator;
    }

    @Override
    protected void initInject() {

    }


    @Override
    protected void initEventAndData() {
        rg_gate = (RadioGroup) mView.findViewById(R.id.rg_gate);

        rg_gate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.rb_remote_gate) {
                    checkEarlyCallFragment();
                } else if (checkedId == R.id.rb_bluetooth) {
                    checkElevatorCartFragment();
                }
            }
        });
        rg_gate.check(R.id.rb_bluetooth);
    }

    private void checkElevatorCartFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        setAllFragmentHide(fragmentTransaction);
        if (elevatorCartFragment == null) {
            elevatorCartFragment = new ElevatorCartFragment();
            fragmentTransaction.add(R.id.rl_content, elevatorCartFragment).commit();
        } else {
            fragmentTransaction.show(elevatorCartFragment).commit();
        }
    }


    private void checkEarlyCallFragment() {
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
        if (elevatorCartFragment != null) {
            fragmentTransaction.hide(elevatorCartFragment);
        }
    }


    @Override
    public void toastMsg(String msg) {

    }
}
