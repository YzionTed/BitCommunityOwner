package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.BIT.fuxingwuye.constant.AppConstants;

import java.io.Serializable;


/**
 * Created by Dell on 2017/7/15.
 */

public class HouseholdBean extends BaseObservable implements Serializable{

    private String userId;
    private String id;
    private String floorId;   //楼房ID
    private String userName;
    private String identity;
    private int sex;
    private long birthday;
    private String account;
    private String mobilePhone;
    private String profession;
    private String owner;
    private String remark;

    public HouseholdBean() {
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    @Bindable
    public String getUserName() {
        return userName==null?null:userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Bindable
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Bindable
    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Bindable
    public String getMobilePhone() {
        return mobilePhone==null?"":mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Bindable
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Bindable
    public String getOwner() {
        if(owner.equals(AppConstants.HOUSE_OWNER)){
            return "户主";
        }else if(owner.equals(AppConstants.HOUSE_RELATIONSHIP)){
            return "家属";
        }else if(owner.equals(AppConstants.HOUSE_RENTER)){
            return "租客";
        }else{
            return owner;
        }
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdentity() {
        return identity==null?null:identity.trim();
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

}
