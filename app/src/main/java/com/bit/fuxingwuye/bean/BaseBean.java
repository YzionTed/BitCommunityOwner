package com.bit.fuxingwuye.bean;

/**
 * SmartCommunity-com.bit.fuxingwuye.bean
 * 作者： YanwuTang
 * 时间： 2017/7/10.
 */

public class BaseBean<T> {
    private int action;  // 区别接口
    private T bean;  // 真正的bean

    public BaseBean(){}
    public BaseBean(int i, T bean) {
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }
}
