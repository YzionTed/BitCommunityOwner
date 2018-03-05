package com.BIT.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/7/16.
 */

public class NoticeListBean {

    /**
     * currentPage : 1
     * records : [{"body":"尊敬的住户：\r\n因工程建设的需要，自2018年1月19日20:00起，以下地区的供水可能受到影响：金普新区十三里工业区、二十里堡景致小区、七顶山街道、大魏家街道、普湾、石河等周边区域停水或降压，预计工程将于20日20:00结束，届时上述区域供水将陆续恢复。大连市自来水集团有限公司提醒上述地区的用户做好储水准备，在此期间，请用户切记随手关闭水龙头。不便之处,敬请谅解！","communityId":"5a82adf3b06c97e0cd6c0f3d","createAt":1519875580961,"dataStatus":1,"editorId":"5a82a22c9ce93e30677c3f9a","editorName":"系统管理员","id":"5a9775fc0cf22047da288a85","noticeType":1,"publishAt":1519976174901,"publishStatus":1,"thumbnailUrl":"ap15a8fb5340cf2835206a3aabc_20180225130421690.jpg","title":"停水通知","updateAt":1519976174901,"url":"http://www.baidu.com"},{"body":"尊敬的业主/住户:\r\n您好,春天临近,为了防止小区出现鼠患,我管理处将从2018年02月22日起到2018年03月02日,每天22:00在小区内的草坪等公共区域内投放鼠药,次日06:00收药。请各位业主/住户在此期间注意安全,特别是一定要看管好自己的小孩,家中有宠物业主/住户也要照顾好自己的宠物,以免其误食。谢谢合作!","communityId":"5a82adf3b06c97e0cd6c0f3d","createAt":1519875688580,"dataStatus":1,"editorId":"5a82a22c9ce93e30677c3f9a","editorName":"系统管理员","id":"5a9776680cf22047da288a8b","noticeType":1,"publishAt":1519887480822,"publishStatus":1,"thumbnailUrl":"ap15a8fb5340cf2835206a3aabc_20180225130421690.jpg","title":"社区灭鼠通知","updateAt":1519887480822,"url":"http://www.baidu.com"},{"body":"尊敬的住户:\r\n你们好!现本小区已进入装修阶段,进出苑区人员日益增多,管理处为加强小区人员进出管理,确保苑区的安全和谐,须对苑区住户.办-理.业主卡,住户须凭业主卡进出苑区，\r\n物业公司各类通知范文。请业主于近期内到管理处.办-理.,谢谢合作!\r\n.办-理.业主卡需带资料:\r\n一、业主及家人:\r\n1寸照片2张,身份证复印件1张\r\n二、租住户:\r\n租房合同复件;身份证复件1份;1寸照片2张","communityId":"5a82adf3b06c97e0cd6c0f3d","createAt":1519875649491,"dataStatus":1,"editorId":"5a82a22c9ce93e30677c3f9a","editorName":"系统管理员","id":"5a9776410cf22047da288a88","noticeType":1,"publishAt":null,"publishStatus":0,"thumbnailUrl":"ap15a8fb5340cf2835206a3aabc_20180225130421690.jpg","title":"住户卡办理通知","updateAt":1519875649491,"url":"http://www.baidu.com"}]
     * total : 3
     * totalPage : 1
     */

    private int currentPage;
    private int total;
    private int totalPage;
    private List<RecordsBean> records;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
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
         * publishAt : 1519976174901
         * publishStatus : 1
         * thumbnailUrl : ap15a8fb5340cf2835206a3aabc_20180225130421690.jpg
         * title : 停水通知
         * updateAt : 1519976174901
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
}
