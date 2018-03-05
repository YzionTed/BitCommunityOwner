package com.BIT.fuxingwuye.activities.fragment.mainFragment;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.Notice;
import com.BIT.fuxingwuye.bean.NoticeListBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/8/5.
 * Created time:2017/8/5 9:16
 */

public class FMainPresenter extends BaseRxPresenter<FMainContract.View> implements FMainContract.Presenter {

    private FragmentMain fragmentMain;

    @Inject
    public FMainPresenter(FragmentMain fragmentMain) {
        this.fragmentMain = fragmentMain;
    }

    @Override
    public void getNotices(String communityId,int page, final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getNotices(communityId,page);
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<NoticeListBean>>() {
            @Override
            public void next(BaseEntity<NoticeListBean> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()){
                        mView.showNotices(o.getData(),type);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },fragmentMain.getContext(),false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void findOne(FindBean findBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).findOne(findBean);
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<UserBean>>() {
            @Override
            public void next(BaseEntity<UserBean> o) {
                mView.findOne(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },fragmentMain.getContext(),false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
