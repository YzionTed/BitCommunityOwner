package com.BIT.fuxingwuye.activities.via;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.ViaBean;
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
