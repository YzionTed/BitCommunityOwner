package com.bit.fuxingwuye.activities.houseManager;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.base.ProprietorBean;
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
                mView.NetEorror();
            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
