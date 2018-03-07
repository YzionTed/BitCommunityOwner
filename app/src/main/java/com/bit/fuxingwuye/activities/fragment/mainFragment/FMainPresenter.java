package com.bit.fuxingwuye.activities.fragment.mainFragment;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.Notice;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> map=new HashMap();
        map.put("communityId",communityId);
        map.put("page",1000);
        map.put("size",10);
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getNotices(map);
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
