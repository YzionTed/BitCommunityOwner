package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/7/15.
 */

public class ParkBean {
    private String id;
    private String carbarnId;//所属车库
    private String parkNo;//车位号
    private String parkArea;//车位面积
    private String parkType;//车位类型0：公共；1：私人）
    private String userId;//业主id（公共车位为空）
    private String userName;
    private String parkUse;//使用情况（0：未使用；1：使用中）
    private String buyingTime;//买入时间
    private long createDate;//创建时间
    private String creator;//创建人
    private String modifier;  //修改人
    private long updateTime;
    private int dateStatus;//数据状态
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarbarnId() {
        return carbarnId;
    }

    public void setCarbarnId(String carbarnId) {
        this.carbarnId = carbarnId;
    }

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }

    public String getParkArea() {
        return parkArea;
    }

    public void setParkArea(String parkArea) {
        this.parkArea = parkArea;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParkUse() {
        return parkUse;
    }

    public void setParkUse(String parkUse) {
        this.parkUse = parkUse;
    }

    public String getBuyingTime() {
        return buyingTime;
    }

    public void setBuyingTime(String buyingTime) {
        this.buyingTime = buyingTime;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(int dateStatus) {
        this.dateStatus = dateStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
