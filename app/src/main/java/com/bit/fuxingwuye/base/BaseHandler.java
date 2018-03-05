package com.bit.fuxingwuye.base;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * KangTuUpperComputer-com.kangtu.uppercomputer.base
 * 作者： YanwuTang
 * 时间： 2016/12/7.
 */
public class BaseHandler extends Handler {
    public WeakReference<Activity> act;
    public BaseHandler(Activity activity){
        this.act = new WeakReference<Activity>(activity);
    }
}
