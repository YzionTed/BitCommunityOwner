package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/7.
 */

public class RegisterBean extends BaseObservable{
    private String phone;
    private String code;
    private String verityType;    //验证方式(0:验证码; 1:密码;)
    private String password;

    public RegisterBean() {
    }

    @Bindable
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

    @Bindable
    public String getPassword() {
        return password==null?null:password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerityType() {
        return verityType;
    }

    public void setVerityType(String verityType) {
        this.verityType = verityType;
    }
}
