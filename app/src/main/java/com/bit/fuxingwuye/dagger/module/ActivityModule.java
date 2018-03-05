package com.BIT.fuxingwuye.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.BIT.fuxingwuye.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 10:13
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    public Context provideContext() {
        return mActivity;
    }
}
