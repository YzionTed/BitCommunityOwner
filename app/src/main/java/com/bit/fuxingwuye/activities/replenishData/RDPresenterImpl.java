package com.bit.fuxingwuye.activities.replenishData;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.HouseBean;
import com.bit.fuxingwuye.bean.ReplenishBean;
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
 * Created time:2017/7/27 14:26
 */

public class RDPresenterImpl extends BaseRxPresenter<RDContract.View> implements RDContract.Presenter {

    private Context context;

    @Inject
    public RDPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void commitData(ReplenishBean replenishBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).replenish(replenishBean)
                .map(new HttpResultFunc<HouseBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<HouseBean>>() {
            @Override
            public void next(BaseEntity<HouseBean> o) {
                mView.commitSuccess(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void commitMember(ReplenishBean replenishBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).member(replenishBean)
                .map(new HttpResultFunc<HouseBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<HouseBean>>() {
            @Override
            public void next(BaseEntity<HouseBean> o) {
                mView.getMemberSuccess(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
