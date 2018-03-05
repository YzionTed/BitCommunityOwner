package com.BIT.fuxingwuye.activities.replenishHouse;

import android.content.Context;


import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.bean.ReplenishHouseBean;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

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
