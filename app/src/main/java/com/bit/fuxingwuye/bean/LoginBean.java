package com.BIT.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/7.
 */

public class LoginBean extends BaseObservable{
    private String phone;
    private String code;
    private String verityType;    //验证方式(0:验证码; 1:密码;)
    private String pwd;

    public LoginBean() {
    }
    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Bindable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVerityType() {
        return verityType;
    }

    public void setVerityType(String verityType) {
        this.verityType = verityType;
    }
    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
/* @Bindable
    public String getMobilePhone() {
        return phone==null?null:phone.trim();
    }

    public void setMobilePhone(String mobilePhone) {
        this.phone = mobilePhone;
    }

    @Bindable
    public String getCode() {
        return code==null?null:code.trim();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVerityType() {
        return verityType;
    }

    public void setVerityType(String verityType) {
        this.verityType = verityType;
    }

    @Bindable
    public String getPassword() {
        return pwd==null?null:pwd.trim();
    }

    public void setPassword(String password) {
        this.pwd = password;
    }*/
}
