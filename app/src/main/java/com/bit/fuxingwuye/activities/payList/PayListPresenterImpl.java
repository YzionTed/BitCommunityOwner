package com.bit.fuxingwuye.activities.payList;

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

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 8:54
 */

public class PayListPresenterImpl extends BaseRxPresenter<PayListContract.View> implements PayListContract.Presenter {

    private Context context;
    @Inject
    public PayListPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void showPayList(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getunpaylists(commonBean)
                .map(new HttpResultFunc<List<PayListBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<PayListBean>>>() {
            @Override
            public void next(BaseEntity<List<PayListBean>> o) {
                mView.showPayList(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
