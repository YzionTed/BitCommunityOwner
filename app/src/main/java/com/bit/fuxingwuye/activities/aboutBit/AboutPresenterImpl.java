package com.BIT.fuxingwuye.activities.aboutBit;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
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
 * Created by Dell on 2017/7/25.
 * Created time:2017/7/25 10:07
 */

public class AboutPresenterImpl extends BaseRxPresenter<AboutContract.View> implements AboutContract.Presenter  {

    private Context context;

    @Inject
    public AboutPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getStores() {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getStores()
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.showQr(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
