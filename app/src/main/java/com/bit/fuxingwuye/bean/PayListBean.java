package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 9:35
 */

public class PayListBean {

    private long createDate;
    private long updateTime;
    private int dataStatus;
    private String id;
    private String title;
    private String userId;
    private String userName;
    private String floorId;
    private String address;
    private double amount;
    private int expensesType;
    private String expensesNo;
    private int payStatus;//缴费状态（1：未缴费，2：已缴费）默认未缴费
    private long amountBTime;
    private long amountETime;

    public PayListBean() {
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(int expensesType) {
        this.expensesType = expensesType;
    }

    public String getExpensesNo() {
        return expensesNo;
    }

    public void setExpensesNo(String expensesNo) {
        this.expensesNo = expensesNo;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public long getAmountBTime() {
        return amountBTime;
    }

    public void setAmountBTime(long amountBTime) {
        this.amountBTime = amountBTime;
    }

    public long getAmountETime() {
        return amountETime;
    }

    public void setAmountETime(long amountETime) {
        this.amountETime = amountETime;
    }
}
