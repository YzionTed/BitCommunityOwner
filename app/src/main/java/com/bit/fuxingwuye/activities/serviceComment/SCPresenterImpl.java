package com.bit.fuxingwuye.activities.serviceComment;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.EvaluationBean;
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
 * Created by Dell on 2017/8/14.
 * Created time:2017/8/14 10:44
 */

public class SCPresenterImpl extends BaseRxPresenter<SCContract.View> implements SCContract.Presenter {

    private Context context;

    @Inject
    public SCPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void comment(EvaluationBean evaluationBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addEvaluation(evaluationBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.conmmentSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
