package com.bit.fuxingwuye.bean;

import java.util.List;

/**
 * SmartCommunity-com.bit.fuxingwuye.bean
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class PlotInfoBean {

    private String key;  // id
    private String value;  // name
    private List<PlotInfoBean> list; // 下一级

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<PlotInfoBean> getList() {
        return list;
    }

    public void setList(List<PlotInfoBean> list) {
        this.list = list;
    }
}
