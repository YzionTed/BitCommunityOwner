package com.bit.fuxingwuye.activities.community;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.InformationBean;
import com.bit.fuxingwuye.bean.RepairBean;
import com.bit.fuxingwuye.bean.ZanBean;
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
 * Created by Dell on 2017/9/30.
 * Created time:2017/9/30 14:56
 */

public class CommunityPresenterImpl extends BaseRxPresenter<CommunityContract.View> implements CommunityContract.Presenter {

    private Context context;

    @Inject
    public CommunityPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getEvents(CommonBean commonBean, final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).queryInfoPage(commonBean)
                .map(new HttpResultFunc<InformationBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<InformationBean>>() {
            @Override
            public void next(BaseEntity<InformationBean> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()){
                        mView.showEvents(o.getData(),type);
                    }else{
                        mView.showEvents(o.getData(),2);
                    }
                }else{
                    mView.showEvents(null,4);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getReplies(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).get_new_replies(commonBean)
                .map(new HttpResultFunc<List<InformationBean.Info>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<InformationBean.Info>>>() {
            @Override
            public void next(BaseEntity<List<InformationBean.Info>> o) {
                if (o.isSuccess()){
                    mView.showReply(o.getData());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void zan(ZanBean zanBean, final int position) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).zan(zanBean)
                .map(new HttpResultFunc<InformationBean.Info>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<InformationBean.Info>>() {
            @Override
            public void next(BaseEntity<InformationBean.Info> o) {
                if (o.isSuccess()){
                    mView.refresh(position,o.getData());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
