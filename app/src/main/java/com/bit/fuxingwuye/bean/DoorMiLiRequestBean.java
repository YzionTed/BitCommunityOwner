package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/15.
 */

public class DoorMiLiRequestBean extends BaseObservable {
    private String communityId;
    String[] mac;

    public DoorMiLiRequestBean(){
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
    @Bindable
    public String[] getDoorMacArr() {
        return mac;
    }
    @Bindable
    public void setDoorMacArr(String[] doorMacArr) {
        this.mac = doorMacArr;
    }
}
