package com.bit.fuxingwuye.activities.message;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.NoticeBean;
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
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 17:06
 */

public class MsgPresenterImpl extends BaseRxPresenter<MsgContract.View> implements MsgContract.Presenter {

    private Context context;

    @Inject
    public MsgPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getNotice(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getNotice(commonBean)
                .map(new HttpResultFunc<NoticeBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<NoticeBean>>() {
            @Override
            public void next(BaseEntity<NoticeBean> o) {
                if(o.isSuccess()){
                    mView.showMsg(o.getData());
                }else{
                    mView.toastMsg(o.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
