package com.BIT.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/7/18.
 */

public class HouseholdsBean {
    private String userId;
    private String floorId;
    private List<HouseholdBean> list;

    public HouseholdsBean() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public List<HouseholdBean> getList() {
        return list;
    }

    public void setList(List<HouseholdBean> list) {
        this.list = list;
    }
}
