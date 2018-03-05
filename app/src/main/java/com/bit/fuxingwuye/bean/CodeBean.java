package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Dell on 2017/7/6.
 */

public class CodeBean extends BaseObservable{
    private String phone;   //手机号
    private String codeType;    //验证码类型  0：注册；1：登录；2：忘记密码
    private int bizCode;

    public CodeBean(String mobilePhone, String codeType,int bizCode) {
        this.phone = mobilePhone;
        this.bizCode = bizCode;
        this.codeType = String.valueOf(codeType);
    }


    @Bindable
    public String getMobilePhone() {
        return phone==null?null:phone.trim();
    }

    public void setMobilePhone(String mobilePhone) {
        this.phone = mobilePhone;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public int getCode() {
        return bizCode;
    }

    public void setCode(int code) {
        this.bizCode = code;
    }
}
