package com.bit.fuxingwuye.activities.addReply;

import android.content.Context;

import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.base.BaseRxPresenter;
import com.bit.fuxingwuye.bean.ReplyBean;
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
 * Created by Dell on 2017/11/3.
 * Created time:2017/11/3 13:37
 */

public class AddReplyPresenterImpl extends BaseRxPresenter<AddReplyContract.View> implements AddReplyContract.Presenter {

    private Context context;

    @Inject
    public AddReplyPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void upload(List<File> files) {
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

    @Override
    public void addFault(ReplyBean replyBean) {
        Observable observable = RetrofitManager.getInstace()
                .create(NetworkApi.class).addReply(replyBean)
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
}
