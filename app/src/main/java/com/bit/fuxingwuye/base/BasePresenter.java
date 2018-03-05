package com.bit.fuxingwuye.base;

/**
 * SmartCommunity-com.bit.fuxingwuye.dagger.presenter
 * 作者： YanwuTang
 * 时间： 2017/7/4.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
