package com.bit.fuxingwuye.dagger.component;

import com.bit.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.bit.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.bit.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.bit.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.dagger.ContextLife;
import com.bit.fuxingwuye.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * SmartCommunity-com.bit.fuxingwuye.dagger.component
 * 作者： YanwuTang
 * 时间： 2017/7/4.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    BaseApplication getContext();

    FragmentMain getFragmentMain();
    FragmentMine getFragmentMine();
    FragmentGate getFragmentGate();
    ElevatorFragment getElevatorFragment();
}
