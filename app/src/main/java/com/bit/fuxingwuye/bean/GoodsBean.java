package com.BIT.fuxingwuye.bean;

/**
 * Created by Dell on 2017/10/31.
 * Created time:2017/10/31 13:17
 */

public class GoodsBean {

    private String id;
    private String mId;//商家ID
    private String mName;//商品名称
    private String imgUrl;//图片url
    private String mNun;//数量
    private String mType;//商品类型（1：食品类、2：日用品类、3：电器类、4：纺织品类、5：五金电料类、6：厨具类）
    private String mUnit;//单位
    private String mSize;//规格
    private String brand;//品牌
    private String price;//销售价格
    private String rank;//排序
    private String buyNum;//已售数量
    private String remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getmNun() {
        return mNun;
    }

    public void setmNun(String mNun) {
        this.mNun = mNun;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmUnit() {
        return mUnit;
    }

    public void setmUnit(String mUnit) {
        this.mUnit = mUnit;
    }

    public String getmSize() {
        return mSize;
    }

    public void setmSize(String mSize) {
        this.mSize = mSize;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
