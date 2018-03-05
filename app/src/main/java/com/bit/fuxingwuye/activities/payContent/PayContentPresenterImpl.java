package com.bit.fuxingwuye.activities.payContent;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;
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
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 10:18
 */

public class PayContentPresenterImpl extends BaseRxPresenter<PayContentContract.View> implements PayContentContract.Presenter {

    private Context context;

    @Inject
    public PayContentPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getPayContent(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getPayContent(commonBean)
                .map(new HttpResultFunc<PayListBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<PayListBean>>() {
            @Override
            public void next(BaseEntity<PayListBean> o) {

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
