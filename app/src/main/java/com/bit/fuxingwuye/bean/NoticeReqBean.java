package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;

/**
 * Created by Dell on 2017/7/15.
 */

public class NoticeReqBean extends BaseObservable{

    private String communityId;

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    private int noticeType;
    private int page;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int  size;
}
