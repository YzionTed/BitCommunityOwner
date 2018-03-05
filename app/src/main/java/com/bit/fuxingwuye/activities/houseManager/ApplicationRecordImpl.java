package com.BIT.fuxingwuye.activities.houseManager;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.base.ProprietorBean;
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
 * Created by 23 on 2018/2/28.
 */

public class ApplicationRecordImpl extends BaseRxPresenter<ApplicationRecordContract.View> implements ApplicationRecordContract.Presenter{
    private Context context;
    @Inject
    public ApplicationRecordImpl(Context context) {
        this.context = context;
    }

    @Override
    public void GetRecord(String roomid) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).Proprietor(roomid)
                .map(new HttpResultFunc<ProprietorBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<ProprietorBean>>() {
            @Override
            public void next(BaseEntity<ProprietorBean> o) {
                mView.ShowRecord(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
