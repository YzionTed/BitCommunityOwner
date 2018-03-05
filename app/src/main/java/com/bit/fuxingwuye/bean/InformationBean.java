package com.bit.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/11/2.
 * Created time:2017/11/2 15:55
 */

public class InformationBean {

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<Info> list;
    private boolean firstPage;
    private boolean lastPage;
    private int nextPage;
    private int prevPage;
    private int firstResult;

    public InformationBean() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Info> getList() {
        return list;
    }

    public void setList(List<Info> list) {
        this.list = list;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public class Info{
        private String id;
        private String title;
        private String content;
        private String userId;
        private String userName;
        private long pasteTime;
        private String zanNumber;
        private String replyNumber;
        private List<ImagePathBean> imgList;
        private String zanId;
        private String headImg;

        public Info() {
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

        public String getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(String replyNumber) {
            this.replyNumber = replyNumber;
        }

        public void setZanNumber(String zanNumber) {
            this.zanNumber = zanNumber;
        }

        public List<ImagePathBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImagePathBean> imgList) {
            this.imgList = imgList;
        }

        public String getZanId() {
            return zanId;
        }

        public void setZanId(String zanId) {
            this.zanId = zanId;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }
}
