package com.BIT.fuxingwuye.activities.houseManager;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.base.ProprietorBean;
import com.BIT.fuxingwuye.bean.RoomList;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 23 on 2018/3/1.
 */

public class ApplicationDetailsImpl extends BaseRxPresenter<ApplicationDetailsContract.View> implements ApplicationDetailsContract.Presenter{
    private Context context;
    @Inject
    public ApplicationDetailsImpl(Context context) {
        this.context = context;
    }

    @Override
    public void GetApplication(ProprietorBean.RecordsBean bean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).audit(bean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.ShowApplication();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void DismissApplication(ProprietorBean.RecordsBean bean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).audit(bean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.ShowDismissApplication();
            }

            @Override
            public void onError(Throwable e) {

            }


        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
