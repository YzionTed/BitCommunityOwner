package com.BIT.fuxingwuye.activities.community;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.bean.ZanBean;
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
