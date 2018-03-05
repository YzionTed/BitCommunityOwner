package com.bit.fuxingwuye.activities.personlPage;

import android.content.Context;
import android.util.Log;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.VersionBean;
import com.bit.fuxingwuye.constant.NetworkApi;
import com.bit.fuxingwuye.http.HttpResultFunc;
import com.bit.fuxingwuye.http.ProgressSubscriber;
import com.bit.fuxingwuye.http.RetrofitManager;
import com.bit.fuxingwuye.http.SubscriberOnNextListenter;
import com.bit.fuxingwuye.utils.InstallUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 18:00
 */

public class PPPresenterImpl extends BaseRxPresenter<PPContract.View> implements PPContract.Presenter{

    private Context context;

    @Inject
    public PPPresenterImpl(Context context) {
        this.context = context;
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

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void editPersonalPage() {
        mView.editPersonalPage();
    }

    @Override
    public void houseManager() {
        mView.houseManager();
    }

    @Override
    public void carManager() {
        mView.carManager();
    }

    @Override
    public void aboutBit() {
        mView.aboutBit();
    }

    @Override
    public void feedback() {
        mView.feedback();
    }

    @Override
    public void checkUpgrade(CommonBean commonBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).checkVersion(commonBean);
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<VersionBean>>() {
            @Override
            public void next(BaseEntity<VersionBean> o) {
                if (o.isSuccess()){
                    mView.hasUpgrade(o.getData());
                }else{
                    mView.toastMsg(o.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void downLoad(final Context ctx, String url) {
        new InstallUtils(ctx, url, "update", new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                mView.initProgressDialog();
            }

            @Override
            public void onComplete(String path) {
                InstallUtils.installAPK(ctx, path, "com.bit.fuxingwuye.fileprovider", new InstallUtils.InstallCallBack() {
                    @Override
                    public void onSuccess() {
                        mView.toastMsg("正在安装程序...");
                    }

                    @Override
                    public void onFail(Exception e) {
                        mView.toastMsg("安装失败:"+e.toString());
                        Log.e("error",e.toString());
                    }
                });
                mView.showProgressDialog(100);
            }

            @Override
            public void onLoading(long total, long current) {
                mView.showProgressDialog((int) (current * 100 / total));
            }

            @Override
            public void onFail(Exception e) {

            }
        }).downloadAPK();
    }
}
