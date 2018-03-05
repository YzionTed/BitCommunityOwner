package com.bit.fuxingwuye.activities.viaRecord;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.ViaBean;
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
 * Created by Dell on 2017/11/15.
 * Created time:2017/11/15 13:31
 */

public class ViaRecordPresenterImpl extends BaseRxPresenter<ViaRecordContract.View> implements ViaRecordContract.Presenter {

    private Context context;

    @Inject
    public ViaRecordPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getVias(CommonBean commonBean, final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getViaList(commonBean)
                .map(new HttpResultFunc<List<ViaBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<ViaBean>>>() {
            @Override
            public void next(BaseEntity<List<ViaBean>> o) {
                if(o.isSuccess()){
                    if (null==o.getData()){
                        mView.showList(null,4);
                    }else if(o.getData().size()>0){
                        mView.showList(o.getData(),type);
                    }else{
                        mView.showList(o.getData(),2);
                    }
                }else{
                    mView.showList(null,4);
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
