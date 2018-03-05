package com.bit.fuxingwuye.activities.feedback;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FeedbackBean;
import com.bit.fuxingwuye.bean.GetFeedbackBean;
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
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:37
 */

public class FeedbackPresenterImpl extends BaseRxPresenter<FeedbackContract.View> implements FeedbackContract.Presenter {

    private Context context;

    @Inject
    public FeedbackPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void feedback(FeedbackBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).feedback(commonBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<GetFeedbackBean>>() {
            @Override
            public void next(BaseEntity<GetFeedbackBean> o) {
                mView.feedbackSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
