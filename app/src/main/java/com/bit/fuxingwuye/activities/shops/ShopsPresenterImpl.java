package com.BIT.fuxingwuye.activities.shops;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.MerchantBean;
import com.BIT.fuxingwuye.bean.MerchantsBean;
import com.BIT.fuxingwuye.bean.RepairBean;
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
 * Created by Dell on 2017/9/30.
 * Created time:2017/9/30 10:19
 */

public class ShopsPresenterImpl extends BaseRxPresenter<ShopsContract.View> implements ShopsContract.Presenter {

    private Context context;

    @Inject
    public ShopsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getShops(MerchantBean merchantBean, final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getMerchants(merchantBean)
                .map(new HttpResultFunc<MerchantsBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<MerchantsBean>>() {
            @Override
            public void next(BaseEntity<MerchantsBean> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()){
                        mView.showShops(o.getData().getList(),type);
                    }else{
                        mView.showShops(o.getData().getList(),2);
                    }
                }else{
                    mView.showShops(null,4);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getSlide(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getslide(commonBean)
                .map(new HttpResultFunc<List<String>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<String>>>() {
            @Override
            public void next(BaseEntity<List<String>> o) {

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
