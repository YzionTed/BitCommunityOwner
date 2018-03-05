package com.bit.fuxingwuye.activities.videoDevices;

import android.content.Context;

import com.bit.fuxingwuye.activities.shops.ShopsContract;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RepairBean;
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
 * Created by Dell on 2017/10/30.
 * Created time:2017/10/30 14:52
 */

public class VideosPresenterImpl extends BaseRxPresenter<VideosContract.View> implements VideosContract.Presenter {

    private Context context;

    @Inject
    public VideosPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getVideos(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getRepairs(commonBean)
                .map(new HttpResultFunc<List<RepairBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<RepairBean>>>() {
            @Override
            public void next(BaseEntity<List<RepairBean>> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()){
                        mView.showVideos(o.getData());
                    }else{
                        mView.showVideos(o.getData());
                    }
                }else{
                    mView.showVideos(null);
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
