package com.bit.fuxingwuye.bean.request;

import java.util.List;

/**
 * Created by mac_cai on 2018/3/7.
 */

public class DataPagesBean<T> {

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
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
    private List<T> records;
    private int total;
    private int totalPage;

}
