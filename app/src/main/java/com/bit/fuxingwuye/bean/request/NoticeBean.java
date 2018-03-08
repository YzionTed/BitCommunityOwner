package com.bit.fuxingwuye.bean.request;

/**
 * Created by mac_cai on 2018/3/8.
 */

public class NoticeBean {
    /**
     * body : 尊敬的住户：
     因工程建设的需要，自2018年1月19日20:00起，以下地区的供水可能受到影响：金普新区十三里工业区、二十里堡景致小区、七顶山街道、大魏家街道、普湾、石河等周边区域停水或降压，预计工程将于20日20:00结束，届时上述区域供水将陆续恢复。大连市自来水集团有限公司提醒上述地区的用户做好储水准备，在此期间，请用户切记随手关闭水龙头。不便之处,敬请谅解！
     * communityId : 5a82adf3b06c97e0cd6c0f3d
     * createAt : 1519875580961
     * dataStatus : 1
     * editorId : 5a82a22c9ce93e30677c3f9a
     * editorName : 系统管理员
     * id : 5a9775fc0cf22047da288a85
     * noticeType : 1
     * publishAt : 1520056945943
     * publishStatus : 1
     * thumbnailUrl : ap15a8fb5340cf2835206a3aabc_20180225130421690.jpg
     * title : 停水通知
     * updateAt : 1520056945943
     * url : http://www.baidu.com
     */

    private String body;
    private String communityId;
    private long createAt;
    private int dataStatus;
    private String editorId;
    private String editorName;
    private String id;
    private int noticeType;
    private long publishAt;
    private int publishStatus;
    private String thumbnailUrl;
    private String title;
    private long updateAt;
    private String url;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public long getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(long publishAt) {
        this.publishAt = publishAt;
    }

    public int getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(int publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
