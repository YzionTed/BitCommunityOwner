package com.bit.fuxingwuye.activities.payResult;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 13:30
 */

public class PayResultPresenterImpl extends BaseRxPresenter<PayResultContract.View> implements PayResultContract.Presenter {

    private Context context;

    @Inject
    public PayResultPresenterImpl(Context context) {
        this.context = context;
    }

}
