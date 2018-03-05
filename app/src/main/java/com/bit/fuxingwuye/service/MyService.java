package com.BIT.fuxingwuye.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.BIT.fuxingwuye.activities.callPolice.CallPoliceActivity;
import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;
import com.BIT.fuxingwuye.utils.ACache;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dell on 2017/11/10.
 * Created time:2017/11/10 14:34
 */

public class MyService extends Service {

    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {
        super.onCreate();
        CommonBean commonBean = new CommonBean();
        commonBean.setUserId(ACache.get(getApplicationContext()).getAsString(HttpConstants.USERID));
        commonBean.setAlias(ACache.get(getApplicationContext()).getAsString(HttpConstants.USERID));

        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).setJPushAlias(commonBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                if (o.isSuccess()){
                    Log.e("setJPush","set JPUSH Alias success");
                    stopSelf();
                }else {

                }

            }
            @Override
            public void onError(Throwable e) {

            }
        },this,false);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(rxSubscription);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
