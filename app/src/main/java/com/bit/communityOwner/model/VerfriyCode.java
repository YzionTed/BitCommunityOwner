package com.bit.communityOwner.model;

/**
 * Created by zhangjiajie on 18/3/1.
 */

public class VerfriyCode {

    private String phone;   //手机号
    private int bizCode;//验证码类型  0：注册；1：登录；2：忘记密码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }
}
