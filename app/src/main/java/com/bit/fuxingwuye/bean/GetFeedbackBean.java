package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 14:19
 */

public class GetFeedbackBean extends BaseObservable {
    private String content;


    public GetFeedbackBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
