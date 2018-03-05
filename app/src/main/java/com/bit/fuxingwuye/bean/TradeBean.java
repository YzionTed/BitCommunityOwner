package com.bit.fuxingwuye.bean;

/**
 * Created by Dell on 2017/11/17.
 * Created time:2017/11/17 17:12
 */

public class TradeBean {

    private String order_no;
    private String trade_status;
    private String trade_no;
    private String buyer_logon_id;

    public TradeBean() {
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }
}
