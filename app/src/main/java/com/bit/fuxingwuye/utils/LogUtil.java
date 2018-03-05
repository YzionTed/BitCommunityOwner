package com.bit.fuxingwuye.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 作者： YanwuTang
 * 时间： 2016/10/9.
 */
public class LogUtil {
    public static boolean D = true; // debug

    /**
     * 打印普通消息 （常用）
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (D && !TextUtils.isEmpty(msg))
            Log.i(tag, msg);
    }

    /**
     * 打印普通消息 （常用）
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (D && !TextUtils.isEmpty(msg))
            Log.d(tag, msg);
    }

    /**
     * 打印普通消息 （常用）
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (D && !TextUtils.isEmpty(msg))
            Log.v(tag, msg);
    }

    /**
     * 打印普通消息 （常用）
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (D && !TextUtils.isEmpty(msg))
            Log.w(tag, msg);
    }

    /**
     * 打印普通消息 （常用）
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (D && !TextUtils.isEmpty(msg))
            Log.e(tag, msg);
    }
}
