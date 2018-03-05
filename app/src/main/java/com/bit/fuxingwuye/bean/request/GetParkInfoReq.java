package com.bit.fuxingwuye.bean.request;

import android.databinding.BaseObservable;

/**
 * SmartCommunity-com.bit.fuxingwuye.bean
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class GetParkInfoReq extends BaseObservable {
    private String id;  // id:小区ID

    public GetParkInfoReq(String id){
        this.id = id;
    }
}
