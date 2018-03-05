package com.bit.fuxingwuye.activities.houseManager;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FloorBean;
import com.bit.fuxingwuye.bean.RoomList;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;
import com.bit.fuxingwuye.utils.Tag;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:29
 */

public class HMPresenterImpl extends BaseRxPresenter<HMContract.View> implements HMContract.Presenter {

    private Context context;

    @Inject
    public HMPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getFloors(Map<String,String> commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getFloors(commonBean)
                .map(new HttpResultFunc<List<RoomList>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<RoomList>>>() {
            @Override
            public void next(BaseEntity<List<RoomList>> o) {
                mView.showFloors(o.getData());
            }


            @Override
            public void onError(Throwable e) {
                mView.onError();
            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void deleteFloor(String id) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).Closed(id)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.deleteSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
