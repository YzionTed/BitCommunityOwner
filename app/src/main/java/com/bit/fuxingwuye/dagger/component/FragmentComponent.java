package com.bit.fuxingwuye.dagger.component;

import android.app.Activity;


import com.bit.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.bit.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.bit.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.bit.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.bit.fuxingwuye.dagger.FragmentScope;
import com.bit.fuxingwuye.dagger.module.FragmentModule;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(FragmentMain fragmentMain);
    void inject(FragmentMine fragmentMine);
    void inject(FragmentGate fragmentGate);
    void inject(ElevatorFragment elevatorFragment);
}
