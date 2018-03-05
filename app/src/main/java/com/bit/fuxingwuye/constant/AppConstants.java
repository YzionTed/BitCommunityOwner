package com.BIT.fuxingwuye.constant;

import com.BIT.fuxingwuye.base.BaseApplication;

import java.io.File;

/**
 * SmartCommunity-com.BIT.fuxingwuye.constant
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class AppConstants {

    /**              Request Action               **/
    public static final int ACTION_GET_PLOT_INFO = 10001;
    public static final int ACTION_GET_PARK_INFO = 10002;


    /**                文件                  **/
    public static final String FILE_ROOT_PATH = "/SmartCommunity/";          // 文件目录
    public static final String FILE_APK_PATH = "/apk/";                   // apk 目录
    public static final String FILE_CRASH_PATH = "/crash/";        // crash
    public static final String FILE_DOWNLOADER_DIR = "/downloader/";        // 下载目录

    public static final String PATH_DATA = BaseApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String WECHAT_APPID = "wxcdfd01ff3e101e7f";

    public static final String FAULT_PERSONAL = "1";
    public static final String FAULT_PUBLIC = "2";

    public static final String REPAIR_TYPE_WATER = "1";
    public static final String REPAIR_TYPE_HOUSE = "2";
    public static final String REPAIR_TYPE_SAFE = "3";
    public static final String REPAIR_TYPE_ELEVATOR = "4";
    public static final String REPAIR_TYPE_GATE = "5";
    public static final String REPAIR_TYPE_OTHER = "99";

    public static final int MALE = 1;
    public static final int FAMALE = 2;
    public static final int UNKNOWN_SEX = -1;

    public static final String HOUSE_OWNER = "1";//户主
    public static final String HOUSE_RELATIONSHIP = "2";//家属
    public static final String HOUSE_RENTER = "3";//租客

    public static final int COME_FROM_REPLENISHDATA = 0;
    public static final int COME_FROM_HOUSEMANAGER = 1;
    public static final int COME_FROM_CARMANAGER = 2;

    public static final int EDIT_PHOTO = 1;
    public static final int EDIT_NAME = 2;
    public static final int EDIT_PHONE = 3;
    public static final int EDIT_SEX = 4;

    public static final int VIA_TYPE_LIST = 0;
    public static final int VIA_TYPE_QR = 1;
    public static final int VIA_TYPE_ADD = 2;

    public static final int REQ_ADD_HOUSE = 10;
    public static final int RES_ADD_HOUSE = 20;
    public static final int REQ_ADD_CAR = 11;
    public static final int RES_ADD_CAR = 21;
    public static final int REQ_ADD_HOUSEHOLD = 12;
    public static final int RES_ADD_HOUSEHOLD = 22;

    public static final int REQ_REFRESH_REPAIR = 13;
    public static final int RES_REFRESH_REPAIR = 23;

    public static final int REQ_PUBLIC_REPAIR = 14;
    public static final int RES_PUBLIC_REPAIR = 24;
    public static final int REQ_PERSONAL_REPAIR = 15;
    public static final int RES_PERSONAL_REPAIR = 25;

    public static final int REQ_CHOOSE_HOUSE = 16;
    public static final int RES_CHOOSE_HOUSE = 26;

    public static final int REQ_REFRESH_INFO = 17;
    public static final int RES_REFRESH_INFO = 27;

    public static final int REQ_PICK_PROVINCE = 100;
    public static final int RES_PICK_PROVINCE = 200;
    public static final int REQ_PICK_PARK = 101;
    public static final int RES_PICK_PARK = 201;
    public static final int REQ_PICK_ROOM = 102;
    public static final int RES_PICK_ROOM = 202;
    public static final int REQ_PICK_FAULT_ADDRESS = 103;
    public static final int RES_PICK_FAULT_ADDRESS = 203;

    public static final int REQ_UPDATE_REPAIR = 1000;
    public static final int RES_UPDATE_REPAIR = 2000;
}
