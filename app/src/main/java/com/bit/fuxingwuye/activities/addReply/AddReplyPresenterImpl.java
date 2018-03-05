package com.BIT.fuxingwuye.activities.addReply;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.ReplyBean;
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
