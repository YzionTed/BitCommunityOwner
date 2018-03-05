package com.BIT.communityOwner.model;

/**
 * Created by zhangjiajie on 18/3/1.
 */

public class ResetPassword {

    private String phone;
    private String code;
    private String newPass;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
