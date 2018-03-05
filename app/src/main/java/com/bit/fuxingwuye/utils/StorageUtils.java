package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * KangTuUpperComputer-com.kangtu.uppercomputer.utils
 * 作者： YanwuTang
 * 时间： 2016/8/30.
 * SharedPreferences
 */
public class StorageUtils {

    /**
     * common 文件 保存手机版本等信息  默认文件夹
     */
    public static final String COMMON_FILE_NAME = "common";

    /*********************************   异常捕获  ****************************************/
    public static final String CRASH_LOG_CREATE_TIME = "CRASH_LOG_CREATE_TIME";  // 异常日志捕获
    public static final String LOG_FILE_NAME = "LOG_FILE_NAME";


    // boolean
    public static void setBoolean(String key, boolean value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // boolean
    public static void setBoolean(String prefName, String key, boolean value,
                                  Context context) {
        SharedPreferences preferences = context.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String prefName, String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    // int
    public static int getInt(String fileName, String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 0);

        return value;
    }

    public static int getInt(String key, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(COMMON_FILE_NAME,
                Context.MODE_PRIVATE);
        int value = preferences.getInt(key, 0);

        return value;
    }
    public static void setInt(String key, int value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setInt(String fileName, String key, int value,
                              Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getShareValue(Context context, String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static String getShareValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static void setShareValue(String fileName, Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value).apply();
    }

    public static void setShareValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value).apply();
    }

    // 删除保留值。。
    public static void removeShareValue(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                COMMON_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    // 删除保留值。。
    public static void removeShareValue(Context context, String fileName, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }
}
