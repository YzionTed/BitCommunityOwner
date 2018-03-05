package com.BIT.fuxingwuye.activities.personalEdit;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.EditUserBean;
import com.BIT.fuxingwuye.bean.ResetPwdBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.bean.request.Code;
import com.BIT.fuxingwuye.constant.NetworkApi;
import com.BIT.fuxingwuye.http.HttpResultFunc;
import com.BIT.fuxingwuye.http.ProgressSubscriber;
import com.BIT.fuxingwuye.http.RetrofitManager;
import com.BIT.fuxingwuye.http.SubscriberOnNextListenter;

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
import rx.functions.Action0;

/**
 * Created by Dell on 2017/11/9.
 * Created time:2017/11/9 14:18
 */

public class PersonalEditPresenterImpl extends BaseRxPresenter<PersonalEditContract.View> implements PersonalEditContract.Presenter {

    private Context context;

    @Inject
    public PersonalEditPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void editUser(EditUserBean editUserBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).editUser(editUserBean)
                .map(new HttpResultFunc<UserBean>());
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnNextListenter<BaseEntity<UserBean>>() {
            @Override
            public void next(BaseEntity<UserBean> o) {
                mView.editSuccess(o.getData());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
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
                mView.toastMsg(o.getData().getDescription());
            }

            @Override
            public void onError(Throwable e) {

            }

        },context,true);
        RetrofitManager.getInstace().toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    @Override
    public void upload(File file) {
        Map<String, RequestBody> paths = new HashMap<>();

            paths.put("file" + "\"; filename=\"" + file.getName(),
                    RequestBody.create(MediaType.parse("image/jpg"), file));

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
