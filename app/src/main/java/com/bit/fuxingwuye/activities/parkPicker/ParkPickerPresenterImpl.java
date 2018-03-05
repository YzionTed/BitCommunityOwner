package com.BIT.fuxingwuye.activities.parkPicker;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.PlotInfoBean;
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
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 17:33
 */

public class ParkPickerPresenterImpl extends BaseRxPresenter<ParkPickerContract.View> implements ParkPickerContract.Presenter {

    private Context context;

    @Inject
    public ParkPickerPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getParks(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getParks(commonBean)
                .map(new HttpResultFunc<List<PlotInfoBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<PlotInfoBean>>>() {
            @Override
            public void next(BaseEntity<List<PlotInfoBean>> o) {
                mView.showParks(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
