package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/7/14.
 */

public class FindBean {
    private String id;
    private String userName;
    private String mobilePhone;

    public FindBean() {
    }

    public FindBean(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
