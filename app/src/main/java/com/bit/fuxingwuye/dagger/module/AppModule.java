package com.bit.fuxingwuye.dagger.module;

import com.bit.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.bit.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.bit.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.bit.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.dagger.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * SmartCommunity-com.bit.fuxingwuye.dagger.module
 * 作者： YanwuTang
 * 时间： 2017/7/4.
 * app 全局变量
 */
@Module
public class AppModule {

    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    BaseApplication provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    FragmentMain providerFragmentMain(){
        return new FragmentMain();
    }

    @Provides
    @Singleton
    FragmentMine providerFragmentMine(){
        return new FragmentMine();
    }

    @Provides
    @Singleton
    FragmentGate providerFragmentGate(){
        return new FragmentGate();
    }

    @Provides
    @Singleton
    ElevatorFragment providerFragmentElevator(){
        return new ElevatorFragment();
    }
}
