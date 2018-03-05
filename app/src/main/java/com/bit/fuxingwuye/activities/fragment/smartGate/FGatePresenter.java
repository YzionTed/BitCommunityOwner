package com.BIT.fuxingwuye.activities.fragment.smartGate;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FacilityBean;
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
 * Created by Dell on 2017/8/11.
 * Created time:2017/8/11 8:41
 */

public class FGatePresenter extends BaseRxPresenter<FGateContract.View> implements FGateContract.Presenter {

    private FragmentGate fragmentGate;

    @Inject
    public FGatePresenter(FragmentGate fragmentGate) {
        this.fragmentGate = fragmentGate;
    }

    @Override
    public void getDoors(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getDoors(commonBean)
                .map(new HttpResultFunc<List<FacilityBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<FacilityBean>>>() {
            @Override
            public void next(BaseEntity<List<FacilityBean>> o) {
                if (o.isSuccess()){
                    mView.showDoors(o.getData());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },fragmentGate.getContext(),false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
