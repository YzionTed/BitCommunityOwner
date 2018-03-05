package com.bit.fuxingwuye.activities.addFaultRepair;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.RepairBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 14:06
 */

public class FaultRepairPresenterImpl extends BaseRxPresenter<FaultRepairContract.View> implements FaultRepairContract.Presenter {

    private Context context;

    @Inject
    public FaultRepairPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addFault(RepairBean repairBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addRepair(repairBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.addSuccess();
            }
            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void findone(FindBean findBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).findOne(findBean)
                .map(new HttpResultFunc<UserBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<UserBean>>() {
            @Override
            public void next(BaseEntity<UserBean> o) {
                mView.showFloors(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void upload(final List<File> files) {
        Map<String, RequestBody> paths = new HashMap<>();
        for (int i = 0;i<files.size();i++){
            paths.put("file" + i + "\"; filename=\"" + files.get(i).getName(),
                    RequestBody.create(MediaType.parse("image/jpg"), files.get(i)));
        }
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).upload(paths)
                .map(new HttpResultFunc<List<String>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<String>>>() {
            @Override
            public void next(BaseEntity<List<String>> o) {
                mView.upload(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);

    }

}
