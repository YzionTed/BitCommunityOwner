package com.bit.fuxingwuye.activities.payRecord;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 13:28
 */

public class PayRecordPresenterImpl extends BaseRxPresenter<PayRecordContract.View> implements PayRecordContract.Presenter {

    private Context context;

    @Inject
    public PayRecordPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void showPayList(CommonBean commonBean, final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getPayHistory(commonBean)
                .map(new HttpResultFunc<List<PayListBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<PayListBean>>>() {
            @Override
            public void next(BaseEntity<List<PayListBean>> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()&&o.getData().size()>0){
                        mView.showPayList(o.getData(),type);
                    }else{
                        mView.showPayList(o.getData(),2);
                    }
                }else{
                    mView.showPayList(null,4);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
