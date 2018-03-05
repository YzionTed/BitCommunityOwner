package com.BIT.fuxingwuye.activities.myRepairList;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;
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
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 17:09
 */

public class MyRepairsPresenterImpl extends BaseRxPresenter<MyRepairsContract.View> implements MyRepairsContract.Presenter {

    private Context context;

    @Inject
    public MyRepairsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getRepairs(CommonBean commonBean,final int type) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getRepairs(commonBean)
                .map(new HttpResultFunc<List<RepairBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<RepairBean>>>() {
            @Override
            public void next(BaseEntity<List<RepairBean>> o) {
                if(o.isSuccess()){
                    if(null!=o.getData()){
                        mView.showRepairs(o.getData(),type);
                    }else{
                        mView.showRepairs(o.getData(),2);
                    }
                }else{
                    mView.showRepairs(null,4);
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
