package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/8/24.
 * Created time:2017/8/24 14:27
 */

public class ImagePathBean {

    private String id;
    private String imgUrl;
    private String imgName;
    private String contentType;

    public ImagePathBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
