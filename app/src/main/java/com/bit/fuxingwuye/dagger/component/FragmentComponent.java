package com.BIT.fuxingwuye.dagger.component;

import android.app.Activity;


import com.BIT.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.BIT.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.BIT.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.BIT.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.BIT.fuxingwuye.dagger.FragmentScope;
import com.BIT.fuxingwuye.dagger.module.FragmentModule;

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
