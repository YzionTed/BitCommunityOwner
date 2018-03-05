package com.BIT.fuxingwuye.activities.residential_quarters;

import android.content.Context;

import com.BIT.fuxingwuye.activities.roomPicker.RoomPickerContract;
import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.Community;
import com.BIT.fuxingwuye.bean.TokenBean;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by 23 on 2018/2/26.
 */

public class HousingImpl extends BaseRxPresenter<HousingContract.View> implements HousingContract.Presenter {
    private Context context;

    @Inject
    public HousingImpl(Context context) {
        this.context = context;
    }
    @Override
    public void getHousing(String userId) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getcommunity(userId)
                .map(new HttpResultFunc<Community>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Community>>() {
            @Override
            public void next(BaseEntity<Community> o) {
                mView.showHousing(o.getData());

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
