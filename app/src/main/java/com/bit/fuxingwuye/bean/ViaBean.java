package com.BIT.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 14:19
 */

public class ViaBean {
    private String id;
    private String viaNo;
    private String userName;
    private String userId;
    private String remark;
    private String beginTime;//格式: yyyy-MM-dd HH:mm:ss
    private String endTime;//格式: yyyy-MM-dd HH:mm:ss
    private int viaStatus;
    private String url;


    public ViaBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViaNo() {
        return viaNo;
    }

    public void setViaNo(String viaNo) {
        this.viaNo = viaNo;
    }

    public int getViaStatus() {
        return viaStatus;
    }

    public void setViaStatus(int viaStatus) {
        this.viaStatus = viaStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
