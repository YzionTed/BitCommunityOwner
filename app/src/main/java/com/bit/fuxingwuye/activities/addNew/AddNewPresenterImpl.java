package com.BIT.fuxingwuye.activities.addNew;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.base.BaseEntity;
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
