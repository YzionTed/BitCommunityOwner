package com.bit.fuxingwuye.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


/**
 * Created by Dell on 2017/7/7.
 */

public class ReplenishHouseBean extends BaseObservable{
    private String userId;   //户主ID
    private String floorId;   //楼房ID
    private String contractNo;  //合同编号
    private String area;     //住房面积
    private Long registerTime;   //入住时间
    private String address;


    public ReplenishHouseBean() {
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    @Bindable
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Bindable
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Bindable
    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
