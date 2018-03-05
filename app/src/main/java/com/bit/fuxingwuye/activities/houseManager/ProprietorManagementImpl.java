package com.bit.fuxingwuye.activities.houseManager;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.RoomList;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by 23 on 2018/2/28.
 */

public class ProprietorManagementImpl extends BaseRxPresenter<ProprietorManagementContract.View> implements ProprietorManagementContract.Presenter{
    private Context context;

    @Inject
    public ProprietorManagementImpl(Context context) {
        this.context = context;
    }

    @Override
    public void GetProprietorData(String roomid) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).Proprietor(roomid,"1")
                .map(new HttpResultFunc<ProprietorBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<ProprietorBean>>() {
            @Override
            public void next(BaseEntity<ProprietorBean> o) {
                mView.showProprietorData(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void Relieve(String id) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).relieve(id)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.showRelieveSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
