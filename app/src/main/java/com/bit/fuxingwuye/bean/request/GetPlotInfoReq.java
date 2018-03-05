package com.BIT.fuxingwuye.bean.request;

import android.databinding.BaseObservable;

/**
 * SmartCommunity-com.BIT.fuxingwuye.bean
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class GetPlotInfoReq extends BaseObservable {
    private String id;  // id:小区ID

    public GetPlotInfoReq(String id){
        this.id = id;
    }
}
