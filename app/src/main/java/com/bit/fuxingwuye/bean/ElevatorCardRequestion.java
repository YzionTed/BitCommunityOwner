package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2018/3/3.
 */

public class ElevatorCardRequestion extends BaseObservable {

    private String communityId;
    String[] macAddress;

    public ElevatorCardRequestion(){
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
        return macAddress;
    }
    @Bindable
    public void setDoorMacArr(String[] doorMacArr) {
        this.macAddress = doorMacArr;
    }
}
