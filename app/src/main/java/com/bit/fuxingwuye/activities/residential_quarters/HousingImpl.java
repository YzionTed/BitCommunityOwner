package com.bit.fuxingwuye.activities.residential_quarters;

import android.content.Context;

import com.bit.fuxingwuye.activities.roomPicker.RoomPickerContract;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

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

    /**
     * 获取社区的接口
     * @param userId 用户登录时的id
     */
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
                mView.showError();
            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
