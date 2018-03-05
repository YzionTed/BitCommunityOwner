package com.bit.fuxingwuye.activities.via;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.ViaBean;
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
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 14:33
 */

public class ViaPresenterImpl extends BaseRxPresenter<ViaContract.View> implements ViaContract.Presenter{

    private Context context;

    @Inject
    public ViaPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addVia(ViaBean viaBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addVia(viaBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.addSuccess(o.getData());
            }

            @Override
            public void onError(Throwable e) {
            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
