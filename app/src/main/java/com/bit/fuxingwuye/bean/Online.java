package com.bit.fuxingwuye.bean;

import java.util.List;


public class Online {

    private List<OnlineData> data;
    private String errorCode;
    private String errorMsg;
    private boolean success;

    public List<OnlineData> getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }
}
