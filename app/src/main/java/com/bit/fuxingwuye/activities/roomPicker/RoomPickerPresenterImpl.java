package com.bit.fuxingwuye.activities.roomPicker;

import android.content.Context;


import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.Building;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.PlotInfoBean;
import com.bit.fuxingwuye.bean.Room;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;
import com.bit.fuxingwuye.utils.LogUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 17:33
 */

public class RoomPickerPresenterImpl extends BaseRxPresenter<RoomPickerContract.View> implements RoomPickerContract.Presenter {

    private Context context;

    @Inject
    public RoomPickerPresenterImpl(Context context) {
        this.context = context;
    }



    @Override
    public void getcommunity(String userid) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getcommunity(userid)
                .map(new HttpResultFunc<Community>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Community>>() {
            @Override
            public void next(BaseEntity<Community> o) {
                LogUtil.d("back","进入这里");
                mView.showcommunity(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }



    @Override
    public void getbuilding(String map) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getbuilding(map)
                .map(new HttpResultFunc<Building>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Building>>() {
            @Override
            public void next(BaseEntity<Building> o) {

                mView.showbuilding(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getroom(String buildingId) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getroom(buildingId)
                .map(new HttpResultFunc<Room>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Room>>() {
            @Override
            public void next(BaseEntity<Room> o) {
                mView.showroom(o.getData());
            }

            @Override
            public void onError(Throwable e) {
            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
