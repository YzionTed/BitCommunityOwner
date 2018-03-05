package com.bit.fuxingwuye.activities.editPersonal;

import android.content.Context;


import com.bit.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 11:14
 */

public class EpPresenterImpl extends BaseRxPresenter<EpContract.View> implements EpContract.Presenter {

    private Context context;

    @Inject
    public EpPresenterImpl(Context context) {
        this.context = context;
    }
}
