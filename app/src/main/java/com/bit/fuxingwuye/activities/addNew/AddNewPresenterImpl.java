package com.bit.fuxingwuye.activities.addNew;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.HouseholdsBean;
import com.bit.fuxingwuye.base.BaseEntity;
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
 * Created by Dell on 2017/7/13.
 */

public class AddNewPresenterImpl extends BaseRxPresenter<AddNewContract.View> implements AddNewContract.Presenter {

    private Context context;

    @Inject
    public AddNewPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addNew(HouseholdsBean householdsBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addHouseholds(householdsBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.addSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
