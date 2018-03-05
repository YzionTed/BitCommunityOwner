package com.BIT.fuxingwuye.activities.replenishData;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.HouseBean;
import com.BIT.fuxingwuye.bean.ReplenishBean;
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
