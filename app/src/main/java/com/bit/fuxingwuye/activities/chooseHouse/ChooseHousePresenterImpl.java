package com.BIT.fuxingwuye.activities.chooseHouse;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.UserBean;
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
 * Created by Dell on 2017/10/9.
 * Created time:2017/10/9 9:45
 */

public class ChooseHousePresenterImpl extends BaseRxPresenter<ChooseHouseContract.View> implements ChooseHouseContract.Presenter {

    private Context context;

    @Inject
    public ChooseHousePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void findone(FindBean findBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).findOne(findBean)
                .map(new HttpResultFunc<UserBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<UserBean>>() {
            @Override
            public void next(BaseEntity<UserBean> o) {
                mView.showFloors(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
