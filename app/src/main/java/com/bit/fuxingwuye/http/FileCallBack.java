package com.bit.fuxingwuye.http;

/**
 * SmartCommunity-com.bit.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/11.
 */

public abstract class FileCallBack {

    public void onStart(){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded){}

    abstract public void onSucess(String path, long fileSize);

}
