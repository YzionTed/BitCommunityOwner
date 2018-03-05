package com.bit.fuxingwuye.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 15:12
 */

public class ProgressSubscriber<T> extends Subscriber<BaseEntity<T>> implements ProgressCancelListener {

    private SubscriberOnNextListenter mSubscriberOnNextListenter;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListenter mSubscriberOnNextListenter, Context context, boolean show) {
        this.mSubscriberOnNextListenter = mSubscriberOnNextListenter;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true, show);
    }

    @Override
    public void onStart() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
    }

    /**
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(BaseApplication.getInstance().getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(BaseApplication.getInstance().getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BaseApplication.getInstance().getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        LogUtil.e(Tag.tag,"请求网络进入错误信息："+e.getMessage()+"");
        mSubscriberOnNextListenter.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
  /*      mSubscriberOnNextListenter.next(tBaseEntity);*/
        if ("0".equals(tBaseEntity.getCode())) {
            mSubscriberOnNextListenter.next(tBaseEntity);
        }else if(tBaseEntity.getCode().equals("9050001")){
            EvenBusMessage message=new EvenBusMessage();
            message.setEvent(HttpConstants.loginstaut);
            EventBus.getDefault().post(message);
            Intent intent=new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
           Toast.makeText(context,tBaseEntity.getResult(),Toast.LENGTH_LONG).show();
        }
    }
/*
    @Override
    public void onNext(T t) {
        mSubscriberOnNextListenter.next(t);
    }*/

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }
}
