package com.BIT.fuxingwuye.http;

import android.content.Context;
import android.widget.Toast;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.utils.LogUtil;

import rx.Subscriber;

/**
 * SmartCommunity-com.BIT.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/1.
 */

public abstract class BaseObserver<T> extends Subscriber<BaseEntity<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onCompleted() {
        LogUtil.e(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(TAG, "onError:" + e.toString());
        onHandleError(e.toString());
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        LogUtil.e(TAG, "onNext");
        if (tBaseEntity.isSuccess()) {
            T t = tBaseEntity.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(tBaseEntity.getResult());
        }
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
