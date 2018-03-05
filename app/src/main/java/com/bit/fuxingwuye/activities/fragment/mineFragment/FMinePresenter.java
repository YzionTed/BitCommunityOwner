package com.bit.fuxingwuye.activities.fragment.mineFragment;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.OutLogin;
import com.bit.fuxingwuye.bean.UserBean;
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
 * Created by Dell on 2017/8/9.
 * Created time:2017/8/9 14:34
 */

public class FMinePresenter extends BaseRxPresenter<FMineContract.View> implements FMineContract.Presenter {

    private FragmentMine fragmentMine;

    @Inject
    public FMinePresenter(FragmentMine fragmentMine) {
        this.fragmentMine = fragmentMine;
    }

    @Override
    public void findPersonal(FindBean findBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).findOne(findBean)
                .map(new HttpResultFunc<UserBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<UserBean>>() {
            @Override
            public void next(BaseEntity<UserBean> o) {
                mView.findPersonal(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },fragmentMine.getContext(),false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void outlogin() {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).signOut()
                .map(new HttpResultFunc<OutLogin>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<OutLogin>>() {
            @Override
            public void next(BaseEntity<OutLogin> o) {
                mView.outloginSucess(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },fragmentMine.getContext(),false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
