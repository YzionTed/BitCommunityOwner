package com.BIT.fuxingwuye.dagger.module;

import com.BIT.fuxingwuye.activities.fragment.elevatorFragment.ElevatorFragment;
import com.BIT.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.BIT.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.BIT.fuxingwuye.activities.fragment.smartGate.FragmentGate;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.dagger.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * SmartCommunity-com.BIT.fuxingwuye.dagger.module
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
