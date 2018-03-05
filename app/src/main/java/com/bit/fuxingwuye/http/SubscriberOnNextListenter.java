package com.BIT.fuxingwuye.http;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 15:15
 */

public interface SubscriberOnNextListenter<T> {
    void next(T t);
    void onError(Throwable e);
}
