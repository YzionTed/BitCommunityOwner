package com.bit.fuxingwuye.activities.payment;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/7/6.
 */

public class PaymentPresenterImpl extends BaseRxPresenter<PaymentContract.View> implements PaymentContract.Prsenter{

    private Context context;

    @Inject
    public PaymentPresenterImpl(Context context) {
        this.context = context;
    }
}
