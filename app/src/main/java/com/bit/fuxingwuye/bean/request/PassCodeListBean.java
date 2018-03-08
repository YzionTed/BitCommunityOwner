package com.bit.fuxingwuye.bean.request;

import java.util.List;

/**
 * Created by mac_cai on 2018/3/7.
 */

public class PassCodeListBean {

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PassCodeBean> getRecords() {
        return records;
    }

    public void setRecords(List<PassCodeBean> records) {
        this.records = records;
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

    private int currentPage;
    private List<PassCodeBean> records;
    private int total;
    private int totalPage;

}
