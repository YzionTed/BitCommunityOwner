package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/15.
 */

public class FeedbackBean extends BaseObservable {
    private String appId;
    private String content;

    public FeedbackBean(){}
    @Bindable
    public String getAppId() {
        return appId;
    }
    @Bindable
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Bindable
    public String getContent() {
        return content;
    }
    @Bindable
    public void setContent(String content) {
        this.content = content;
    }

}
