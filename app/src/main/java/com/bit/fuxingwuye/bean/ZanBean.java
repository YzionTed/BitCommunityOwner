package com.BIT.fuxingwuye.bean;

/**
 * Created by Dell on 2017/11/6.
 * Created time:2017/11/6 13:54
 */

public class ZanBean {

    private String pasteId;
    private String userId;
    private String id;//点赞ID(第二次点的时候回传)

    public ZanBean() {
    }

    public String getPasteId() {
        return pasteId;
    }

    public void setPasteId(String pasteId) {
        this.pasteId = pasteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
