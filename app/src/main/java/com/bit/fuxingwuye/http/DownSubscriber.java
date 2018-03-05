package com.BIT.fuxingwuye.http;

import rx.Subscriber;

/**
 * SmartCommunity-com.BIT.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/11.
 */

public class DownSubscriber<ResponseBody> extends Subscriber<ResponseBody> {

    private FileCallBack callBack;
    private String path;

    public DownSubscriber(String path, FileCallBack callBack) {
        this.callBack = callBack;
        this.path = path;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        DownLoadManager.getInstance().writeResponseBodyToDisk((okhttp3.ResponseBody) responseBody, path, callBack);
    }

}
