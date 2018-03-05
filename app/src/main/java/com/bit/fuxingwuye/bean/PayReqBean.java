package com.bit.fuxingwuye.bean;

import java.util.List;

/**
 * Created by Dell on 2017/9/14.
 * Created time:2017/9/14 9:29
 */

public class PayReqBean {

    private cont cont;
    private String id;    //订单ID	（不能重复使用）
    private String userId;

    public PayReqBean.cont getCont() {
        return cont;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public class cont{
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;
        private String orderString;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOrderStr() {
            return orderString;
        }

        public void setOrderStr(String orderString) {
            this.orderString = orderString;
        }
    }
}
