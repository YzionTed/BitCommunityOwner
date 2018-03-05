package com.BIT.fuxingwuye.dagger.component;

import com.BIT.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.BIT.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.BIT.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.BIT.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.dagger.ContextLife;
import com.BIT.fuxingwuye.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * SmartCommunity-com.BIT.fuxingwuye.dagger.component
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
