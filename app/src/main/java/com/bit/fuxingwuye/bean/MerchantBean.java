package com.bit.fuxingwuye.bean;

import java.io.Serializable;

/**
 * Created by Dell on 2017/10/31.
 * Created time:2017/10/31 11:42
 */

public class MerchantBean implements Serializable {

    private String currentPage;

    private String merchantName;//商家店铺名称
    private String operateType;//经营类型（1：甜点饮品；2：快餐便当；3：粥粉面馆；4：菜馆；5：便利店；6：五金；7：火锅；8：养生；）
    private String address;//地址（floorId为空的时候，这address不能为空）

    private String id;
    private String merchantNo;//商家编号
    private String merchantmen;//商家
    private String merchantSex;//性别（1：男； 2：女； 3：未知）
    private String mobilePhone;//联系方式（联系人）
    private String telPhone;//商家座机（店铺电话）
    private String floorId;//楼房ID（选填，有floorId，则address可为空）
    private String introduce;//商家介绍
    private String serve;//服务介绍
    private String website;//网址
    private String photo;//商家图片
    private String applyTime;//申请时间
    private String rank;//排序
    private String remark;//备注


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOperateType() {
        if (operateType.equals("1")){
            return "甜点饮品";
        }else if (operateType.equals("2")){
            return "快餐便当";
        }else if (operateType.equals("3")){
            return "粥粉面馆";
        }else if (operateType.equals("4")){
            return "菜馆";
        }else if (operateType.equals("5")){
            return "便利店";
        }else if (operateType.equals("6")){
            return "五金";
        }else if (operateType.equals("7")){
            return "火锅";
        }else if (operateType.equals("8")){
            return "养生";
        }else {
            return "其他";
        }
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantmen() {
        return merchantmen;
    }

    public void setMerchantmen(String merchantmen) {
        this.merchantmen = merchantmen;
    }

    public String getMerchantSex() {
        return merchantSex;
    }

    public void setMerchantSex(String merchantSex) {
        this.merchantSex = merchantSex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
