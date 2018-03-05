package com.BIT.fuxingwuye.activities.fragment.mineFragment;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.OutLogin;
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
