package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/8/28.
 * Created time:2017/8/28 13:32
 */

public class PaymentDetailBean {

    private String id;
    private String paymentId;
    private String payItem;
    private Double payMoney;
    private String payRemark;
    private String payContext;

    public PaymentDetailBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
