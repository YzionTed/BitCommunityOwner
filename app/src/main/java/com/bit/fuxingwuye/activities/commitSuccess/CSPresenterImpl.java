package com.BIT.fuxingwuye.activities.commitSuccess;

import android.content.Context;


import com.BIT.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:15
 */

public class CSPresenterImpl extends BaseRxPresenter<CSContract.View> implements CSContract.Presenter {

    private Context context;

    @Inject
    public CSPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void continue_replenish() {
        mView.continue_replenish();
    }

    @Override
    public void complete() {
        mView.complete();
    }
}
