package com.bit.fuxingwuye.activities.repairDetail;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.EvaluationBean;
import com.bit.fuxingwuye.bean.ImagePathBean;
import com.bit.fuxingwuye.bean.RepairBean;
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
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 13:55
 */

public class RepairDetailPresenterImpl extends BaseRxPresenter<RepairDetailContract.View> implements RepairDetailContract.Presenter {

    private Context context;

    @Inject
    public RepairDetailPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getRepair(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getRepair(commonBean)
                .map(new HttpResultFunc<RepairBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<RepairBean>>() {
            @Override
            public void next(BaseEntity<RepairBean> o) {
                mView.showRepair(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void deleteRepair(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).deleteRepair(commonBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.deleteSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void updateRepair(RepairBean repairBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).updateRepair(repairBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.updateSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void delete() {
        mView.delete();
    }

    @Override
    public void update() {
        mView.update();
    }

    @Override
    public void comment() {
        mView.comment();
    }

    @Override
    public void getComment(EvaluationBean evaluationBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getEvaluation(evaluationBean)
                .map(new HttpResultFunc<EvaluationBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<EvaluationBean>>() {
            @Override
            public void next(BaseEntity<EvaluationBean> o) {
                mView.showComment(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getImages(RepairBean.ImageBean imageBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getImages(imageBean)
                .map(new HttpResultFunc<List<ImagePathBean>>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<List<ImagePathBean>>>() {
            @Override
            public void next(BaseEntity<List<ImagePathBean>> o) {
                mView.showImage(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
