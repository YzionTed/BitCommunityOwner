package com.BIT.fuxingwuye.activities.feedback;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FeedbackBean;
import com.BIT.fuxingwuye.bean.GetFeedbackBean;
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
