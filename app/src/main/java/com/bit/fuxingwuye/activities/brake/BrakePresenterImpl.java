package com.bit.fuxingwuye.activities.brake;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/11/16.
 * Created time:2017/11/16 10:41
 */

public class BrakePresenterImpl extends BaseRxPresenter<BrakeContract.View> implements BrakeContract.Presenter {

    private Context context;

    @Inject
    public BrakePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void open() {

    }
}
