package com.bit.fuxingwuye.activities.replenishHouse;

import android.content.Context;


import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.HouseholdsBean;
import com.bit.fuxingwuye.bean.ReplenishHouseBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 14:28
 */

public class RHPresenterImpl extends BaseRxPresenter<RHContract.View> implements RHContract.Presenter {

    private Context context;

    @Inject
    public RHPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void commitData(ReplenishHouseBean replenishHouseBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).replenishHouse(replenishHouseBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.commitSuccess(o.getMsg());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void commitHousehold(HouseholdsBean householdBeans) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addHouseholds(householdBeans)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.commithhSuccess(o.getMsg());
            }

            @Override
            public void onError(Throwable e) {

            }
        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void addHousehold() {
        mView.addHousehold();
    }
}
