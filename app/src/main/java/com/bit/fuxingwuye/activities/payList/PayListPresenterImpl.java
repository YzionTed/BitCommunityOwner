package com.BIT.fuxingwuye.activities.payList;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.PayListBean;
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
