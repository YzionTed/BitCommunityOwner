package com.bit.fuxingwuye.activities.callEle;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/8/14.
 * Created time:2017/8/14 13:20
 */

public class CallElePresenterImpl extends BaseRxPresenter<CallEleContract.View> implements CallEleContract.Presenter {

    private Context context;

    @Inject
    public CallElePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getElevator(CommonBean commonBean) {
        mView.showElevator(null);
    }
}
