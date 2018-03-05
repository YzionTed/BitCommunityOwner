package com.bit.fuxingwuye.activities.register;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CodeBean;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RegisterBean;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.request.Code;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by Dell on 2017/7/4.
 */

public class RegisterPresenterImpl extends BaseRxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private Context context;

    @Inject
    public RegisterPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCode(CodeBean codeBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getCode(codeBean)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.runTimerTask();
                    }
                })
                .map(new HttpResultFunc<Code>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Code>>() {
            @Override
            public void next(BaseEntity<Code> o) {
                mView.toastMsg(o.getData().getDescription());
            }

            @Override
            public void onError(Throwable e) {

            }


        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void register(RegisterBean registerBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).register(registerBean)
                .map(new HttpResultFunc<TokenBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<TokenBean>>() {
            @Override
            public void next(BaseEntity<TokenBean> o) {
                mView.registerSuccess(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
