package com.BIT.fuxingwuye.bean.request;

/**
 * Created by 23 on 2018/2/26.
 */

public class Code {
    /**
     * result : 0
     * description : 发送短信成功
     * faillist :
     */

    private String result;
    private String description;
    private String faillist;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaillist() {
        return faillist;
    }

    public void setFaillist(String faillist) {
        this.faillist = faillist;
    }
}
