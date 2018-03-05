package com.BIT.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/11/6.
 * Created time:2017/11/6 15:27
 */

public class CommunityBean {

    private String id;
    private String title;
    private String content;
    private String userId;
    private String userName;
    private long pasteTime;
    private String zanNumber;
    private String replyNumber;
    private List<InfoReply> replies;
    private List<ImagePathBean> imgList;
    private String zanStatus;
    private String names;
    private String zanId;
    private String headImg;

    public CommunityBean() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getPasteTime() {
        return pasteTime;
    }

    public void setPasteTime(long pasteTime) {
        this.pasteTime = pasteTime;
    }

    public String getZanNumber() {
        return zanNumber;
    }

    public void setZanNumber(String zanNumber) {
        this.zanNumber = zanNumber;
    }

    public List<InfoReply> getReplies() {
        return replies;
    }

    public void setReplies(List<InfoReply> replies) {
        this.replies = replies;
    }

    public List<ImagePathBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImagePathBean> imgList) {
        this.imgList = imgList;
    }

    public String getZanStatus() {
        return zanStatus;
    }

    public void setZanStatus(String zanStatus) {
        this.zanStatus = zanStatus;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getZanId() {
        return zanId;
    }

    public void setZanId(String zanId) {
        this.zanId = zanId;
    }

    public String getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public class InfoReply{
        private String id;
        private String pid;
        private String pasteId;
        private String pasteTitle;
        private String content;
        private String userId;
        private String userName;
        private long replyTime;
        private String sendeeId;
        private String sendee;
        private String headImg;
        private List<InfoReply> replies;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPasteId() {
            return pasteId;
        }

        public void setPasteId(String pasteId) {
            this.pasteId = pasteId;
        }

        public String getPasteTitle() {
            return pasteTitle;
        }

        public void setPasteTitle(String pasteTitle) {
            this.pasteTitle = pasteTitle;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(long replyTime) {
            this.replyTime = replyTime;
        }

        public List<InfoReply> getReplies() {
            return replies;
        }

        public void setReplies(List<InfoReply> replies) {
            this.replies = replies;
        }

        public String getSendeeId() {
            return sendeeId;
        }

        public void setSendeeId(String sendeeId) {
            this.sendeeId = sendeeId;
        }

        public String getSendee() {
            return sendee;
        }

        public void setSendee(String sendee) {
            this.sendee = sendee;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }
}
