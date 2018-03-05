package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2018/3/3.
 */

public class ElevatorListRequestion extends BaseObservable {

    private String communityId;
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ElevatorListRequestion(){
        super();
    }
    @Bindable
    public String getCommunityId() {
        return communityId;
    }
    @Bindable
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

}
