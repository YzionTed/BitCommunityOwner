package com.BIT.fuxingwuye.bean;

/**
 * Created by Dell on 2017/11/9.
 * Created time:2017/11/9 16:17
 */

public class EditUserBean {

    private String id;
    private String userName;
    private String sex;
    private String bdaddr;
    private String imgId;//头像id（如果上传头像，则将上传接口返回的ID放在url后面传过来。如：http://xxx.xx.xx?imgId=xxxxx）

    private String mobilePhone;
    private String password;
    private String code;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBdaddr() {
        return bdaddr;
    }

    public void setBdaddr(String bdaddr) {
        this.bdaddr = bdaddr;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
