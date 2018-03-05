package com.bit.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/10/31.
 * Created time:2017/10/31 13:48
 */

public class MerchantsBean {

    private String currentPage;
    private String pageSize;
    private String totalCount;
    private String totalPage;
    private List<MerchantBean> list;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<MerchantBean> getList() {
        return list;
    }

    public void setList(List<MerchantBean> list) {
        this.list = list;
    }
}
