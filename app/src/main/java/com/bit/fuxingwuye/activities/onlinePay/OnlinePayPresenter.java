package com.bit.fuxingwuye.activities.onlinePay;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayReqBean;
import com.bit.fuxingwuye.bean.TradeBean;
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
 * Created by Dell on 2017/9/1.
 * Created time:2017/9/1 13:29
 */

public class OnlinePayPresenter extends BaseRxPresenter<OnlinePayContract.View> implements OnlinePayContract.Presenter {

    private Context context;

    @Inject
    public OnlinePayPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void wechat(PayReqBean payReqBean) {

        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).wxpay(payReqBean)
                .map(new HttpResultFunc<PayReqBean.cont>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<PayReqBean.cont>>() {
            @Override
            public void next(BaseEntity<PayReqBean.cont> o) {
                mView.wxpay(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        }, context, true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

    @Override
    public void alipay(PayReqBean payReqBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).alipay(payReqBean)
                .map(new HttpResultFunc<PayReqBean.cont>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<PayReqBean.cont>>() {
            @Override
            public void next(BaseEntity<PayReqBean.cont> o) {
                mView.aliPay(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        }, context, true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void unionpay() {

    }

    @Override
    public void wechatQuery(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).wxquery(commonBean)
                .map(new HttpResultFunc<TradeBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<TradeBean>>() {
            @Override
            public void next(BaseEntity<TradeBean> o) {
                mView.wxPaySuccess();
            }

            @Override
            public void onError(Throwable e) {

            }
        }, context, true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void aliQuery(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).aliquery(commonBean)
                .map(new HttpResultFunc<TradeBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<TradeBean>>() {
            @Override
            public void next(BaseEntity<TradeBean> o) {
                mView.aliPaySuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        }, context, true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
