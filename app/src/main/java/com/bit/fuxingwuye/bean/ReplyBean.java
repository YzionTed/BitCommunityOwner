package com.bit.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/11/3.
 * Created time:2017/11/3 14:14
 */

public class ReplyBean {

    private String content;
    private String userId;
    private String pasteId;
    private String replyId;
    private List<String> imgIds;

    public ReplyBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasteId() {
        return pasteId;
    }

    public void setPasteId(String pasteId) {
        this.pasteId = pasteId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public List<String> getImgIds() {
        return imgIds;
    }

    public void setImgIds(List<String> imgIds) {
        this.imgIds = imgIds;
    }
}
