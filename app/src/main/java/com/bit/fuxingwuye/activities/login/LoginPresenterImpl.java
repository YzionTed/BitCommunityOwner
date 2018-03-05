package com.bit.fuxingwuye.activities.login;

import android.content.Context;
import android.util.Log;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.CodeBean;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.LoginBean;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.VersionBean;
import com.bit.fuxingwuye.bean.request.Code;
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
import rx.functions.Action0;

/**
 * Created by Dell on 2017/7/3.
 */

public class LoginPresenterImpl extends BaseRxPresenter<LoginContract.View> implements LoginContract.Presenter{

    private Context context;

    @Inject
    public LoginPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCode(CodeBean codeBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).getCode(codeBean)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.runTimerTask();
                    }
                })
                .map(new HttpResultFunc<Code>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<Code>>() {
            @Override
            public void next(BaseEntity<Code> o) {
                mView.toastMsg(o.getMsg());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void login(LoginBean loginBean, Context ctx) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).login(loginBean)
                .map(new HttpResultFunc<TokenBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<TokenBean>>() {
            @Override
            public void next(BaseEntity<TokenBean> o) {
                    mView.loginSuccess(o.getData());

            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
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

    @Override
    public void gotoRegister() {
        mView.gotoRegister();
    }

    @Override
    public void gotoReset() {
        mView.gotoReset();
    }

    @Override
    public void existEmchat(LoginBean loginBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).existEmchat(loginBean)
                .map(new HttpResultFunc<String>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<String>>() {
            @Override
            public void next(BaseEntity<String> o) {
                mView.emchat();
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }
}
