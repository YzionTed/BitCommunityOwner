package com.BIT.fuxingwuye.activities.roomPicker;

import android.content.Context;


import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.Building;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.Community;
import com.BIT.fuxingwuye.bean.PlotInfoBean;
import com.BIT.fuxingwuye.bean.Room;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;
import com.BIT.fuxingwuye.utils.LogUtil;

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
