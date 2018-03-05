package com.bit.fuxingwuye.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bit.fuxingwuye.constant.AppConstants;

import java.io.File;

/**
 * KangTuUpperComputer-com.kangtu.uppercomputer.utils
 * 作者： YanwuTang
 * 时间： 2016/9/21.
 */
public class SDCardUtil {
    private static final String LOG_TAG = "SDCardUtil";
    // SD卡不存在
    public static String SDCARD_IS_UNMOUTED = "sdcard is not exist";

    // 判断sdcard是否存在,true为存在，false为不存在
    public static boolean isSDCardExist() {
        String status = Environment.getExternalStorageState();
        boolean flag = status.equals(android.os.Environment.MEDIA_MOUNTED);
        return flag;
    }

    // 判断sdcard的状态，并告知用户
    public static String checkAndReturnSDCardStatus() {
        String status = Environment.getExternalStorageState();
        if (status != null) {
            // SD已经挂载,可以使用
            if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
                return "1";
            } else if (status.equals(android.os.Environment.MEDIA_REMOVED)) {
                // SD卡已经已经移除
                return "SD卡已经移除或不存在";

            } else if (status.equals(android.os.Environment.MEDIA_SHARED)) {
                // SD卡正在使用中
                return "SD卡正在使用中";

            } else if (status.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
                // SD卡只能读，不能写
                return "SD卡只能读，不能写";
            } else {
                // SD卡的其它情况
                return "SD卡不能使用或不存在";
            }
        } else {
            // SD卡的其它情况
            return "SD卡不能使用或不存在";
        }
    }

    // 获取sdcard路径
    public static String getSdcardUrl() {
        File sdDir = null;
        if (isSDCardExist()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            Log.e(LOG_TAG, sdDir.toString());
            // 如果文件为空或者路径为空，返回默认路径
            if (sdDir == null || TextUtils.isEmpty(sdDir.toString())) {
                Log.e(LOG_TAG, "应用本地保存根目录为空， 返回默认路径/storage/sdcard0/");
                return "/storage/sdcard0/"; // end
            } else {
                return sdDir.getAbsolutePath();
            }
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }

    // 获取本地临时数据的根路径，要及时删除里面内容
    public static String getRootPath() {
        if (isSDCardExist()) {
            String root = getSdcardUrl() + AppConstants.FILE_ROOT_PATH;
            File dir = new File(root);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!dir.exists()){
                Log.d("tag", "dir create error");
            }
            return dir.getPath();
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }

    /**
     *  下载的文件 apk
     * @return
     */
    public static String getAPKFilePath(){
        if (isSDCardExist()){

            String path = getRootPath() + AppConstants.FILE_APK_PATH;
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            return path;
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }


    /**
     *  下载文件夹
     * @return
     */
    public static String getDownLoaderDir(){
        if (isSDCardExist()){

            String path = getRootPath() + AppConstants.FILE_DOWNLOADER_DIR;
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            return path;
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }

    /**
     *  异常文件
     * @return
     */
    public static String getCrashFilePath(){
        if (isSDCardExist()){

            String path = getRootPath() + AppConstants.FILE_CRASH_PATH;
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            return path;
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }

    /**
     * 获取目录地址
     * @param path
     * @return
     */
    public static String getDirPath(String path){
        if (isSDCardExist()){

            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            return path;
        } else {
            return SDCARD_IS_UNMOUTED;
        }
    }
}
