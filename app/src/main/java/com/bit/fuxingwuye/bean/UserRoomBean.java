package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/15.
 */

public class UserRoomBean extends BaseObservable {
    private String communityId;
    private int auditStatus;

    public UserRoomBean(){}
    @Bindable
    public String getCommunityId() {
        return communityId;
    }
    @Bindable
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    @Bindable
    public int getAuditStatus() {
        return auditStatus;
    }
    @Bindable
    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

}
