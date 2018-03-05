package com.bit.fuxingwuye.activities.setting;

import android.content.Context;


import com.bit.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 11:28
 */

public class SettingPresenterImpl extends BaseRxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private Context context;

    @Inject
    public SettingPresenterImpl(Context context) {
        this.context = context;
    }
}
