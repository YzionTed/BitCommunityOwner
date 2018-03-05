package com.bit.communityOwner.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author 作者 张嘉杰 E-mail：jackzjj@qq.com
 * @version V1.0.0
 * @date 创建时间：2014-10-28
 */
public class AppUtil {

    private AppUtil() {
    }

    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;

    /**
     * Unknown network class. {@hide}
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_4_G = 3;

    public static int getNetworkType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * @param context
     * @param content
     */
    public static void copyToClipboard(Context context, String content) {
        //创建一个新的文本clip对象
        ClipData clipData = ClipData.newPlainText("content", content);
        //把clip对象放在剪贴板中
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 判断是否在wifi，3g，4g，网络状态下
     *
     * @param context
     * @return
     */
    public static boolean isFastSpeedNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                LogUtil.d(
                        TAG,
                        "name:" + info.getTypeName() + " | type:"
                                + info.getType() + " | subName:"
                                + info.getSubtypeName() + " | subType:"
                                + info.getSubtype() + " | isConnected:"
                                + info.isConnected());

                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else if ((info.getType() == ConnectivityManager.TYPE_MOBILE)
                        && (getNetworkType(info.getSubtype()) == NETWORK_CLASS_3_G ||
                        getNetworkType(info
                                .getSubtype()) == NETWORK_CLASS_4_G)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断外部sdcard是否可用
     *
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getUrlFileName(String url) {
        String[] strings = url.split("/");

        String fileName = strings[strings.length - 1];

        // 如果有，去掉扩展名
        int index = fileName.indexOf(".");
        if (index != -1) {
            fileName = fileName.substring(0, index);
        }

        return fileName;
    }

    /**
     * 设置桌面背景
     */
    public static void setWallpaper(Context context, Bitmap bitmap) {
        try {
            WallpaperManager wallpaperManager = WallpaperManager
                    .getInstance(context);
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * dip 转 px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }
        return (int) dipValue;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 定义API LEVEL 默认是 3
     */
    private static int SYSTEM_API_LEVEL = 3;

    private final static Map<String, SharedPreferences> sharedPreferencesMap = new
            HashMap<String, SharedPreferences>();

    /**
     * 获取应用设置
     *
     * @return
     */
    public static SharedPreferences getSettingPreferences(Context context) {
        return getSharedPreferences(context, "lc_sdk_setting");
    }

    /**
     * @param ctx
     * @param name
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context ctx,
                                                         String name) {
        SharedPreferences sharedPreferences = sharedPreferencesMap.get(name);
        if (sharedPreferences != null) {
            return sharedPreferences;
        } else {
            sharedPreferences = ctx.getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            sharedPreferencesMap.put(name, sharedPreferences);
        }
        return sharedPreferences;
    }


    private static String TAG = AppUtil.class.getSimpleName();

    /**
     * 获取渠道号
     */
    public static String getChannel(Context context) {
        String channel = "";
        try {
            ApplicationInfo pinfo = context.getPackageManager()
                    .getApplicationInfo(
                            context.getPackageName(),
                            PackageManager.GET_META_DATA);
            channel = pinfo.metaData.get("MOBILE_CHANNEL")
                    .toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * wifi是否激活
     *
     * @param context
     * @return
     */
    public static boolean isWiFiActive(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                LogUtil.d(
                        TAG,
                        "name:" + info.getTypeName() + " | type:"
                                + info.getType() + " | isConnected:"
                                + info.isConnected());
                if (info.getTypeName().equals("WIFI") && info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * sim卡串码标识
     *
     * @return
     */
    public static String getICCID(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = "";

        if (!TextUtils.isEmpty(tm.getSimSerialNumber())) {
            iccid = tm.getSimSerialNumber();
        }

        return iccid;
    }

    /**
     * 获取SIM卡运营商
     *
     * @param context
     * @return
     */
    public static String getOperators(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = "";
        String IMSI = tm.getSubscriberId();
        if (IMSI == null || IMSI.equals("")) {
            return operator;
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            operator = "中国移动";
        } else if (IMSI.startsWith("46001") && IMSI.startsWith("46006")) {
            operator = "中国联通";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005") ||
                IMSI.startsWith("46011")) {
            operator = "中国电信";
        }
        return operator;
    }

    /**
     * 获取分辨率
     *
     * @param context
     * @return
     */
    public static String getPX(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }

    /**
     * 获取包名
     *
     * @param ctx
     * @return
     */
    public static String getPackageName(Context ctx) {
        try {
            return ctx.getPackageName();
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static final String getMobileManufacturer() {
        String manufacturer = "";
        Process pro = null;
        try {
            // getprop apps.setting.platformversion
            pro = Runtime.getRuntime().exec(
                    "getprop apps.customerservice.device");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
            manufacturer = (br.readLine());
            manufacturer = manufacturer.toUpperCase();
            pro.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pro != null)
                pro.destroy();
        }
        if (TextUtils.isEmpty(manufacturer))
            manufacturer = Build.MANUFACTURER;
        return manufacturer.toUpperCase();
    }

    /**
     * 获取手机具体型号，如:LT118i
     *
     * @return
     */
    public static final String getMobileModel() {

        String MODEL = Build.MODEL;
        // LogUtilUtils.e(TAG, "MODEL:" + MODEL);
        return MODEL;
    }

    /**
     * 获取本地号码
     *
     * @param context
     * @return
     */
    public static String getLocalPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    /**
     * 检查地址有效性
     *
     * @param hostname
     * @return
     */
    public static int lookupHost(String hostname) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            return -1;
        }
        byte[] addrBytes;
        int addr;
        addrBytes = inetAddress.getAddress();
        addr = ((addrBytes[3] & 0xff) << 24) | ((addrBytes[2] & 0xff) << 16)
                | ((addrBytes[1] & 0xff) << 8) | (addrBytes[0] & 0xff);
        return addr;
    }

    /**
     * 获取应用的版本号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        int version = -1;
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取应用的版本号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        String version = null;
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取随机数
     *
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt());
        }
        return sb.toString();
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pinfo = null;
        try {
            pinfo = context

                    .getPackageManager().getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return pinfo;
    }


    /**
     * sim卡唯一标识
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = null;

        if (!TextUtils.isEmpty(tm.getSubscriberId())) {
            imsi = tm.getSubscriberId();
        }

        return imsi;
    }

    /**
     * 手机唯一标识
     *
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        if (!TextUtils.isEmpty(tm.getDeviceId())) {
            imei = tm.getDeviceId();
        }
//        LogUtil.d(TAG, "imei:" + imei);
        return imei;
    }

    @SuppressLint("NewApi")
    public static void preferencesCommit(Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    /**
     * 判断是否中国移动手机号码
     *
     * @param number
     * @return
     */
    public static boolean isCMCCPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        if (number.length() > 11) {
            number = number.substring(number.length() - 11);
        }
        String regex = "^1(3[4-9]|47|5[012789]|8[2378])\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        boolean b = m.matches();
        if (number.equals("13800138000")) {
            return false;
        }
        return b;
    }

    /**
     * require device mac address
     *
     * @param context
     * @return String
     */

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return null != info.getMacAddress() ? info.getMacAddress().toUpperCase() : "unknown";
    }

    /**
     * @param context
     * @return
     */
    public static String getCPUInfo(Context context) {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader =
                    new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toUpperCase();
        } catch (IOException ex) {
        }
        return result;
    }

    /**
     * @param encodeStr 加密类型如:MD5 SHA1 etc.
     * @param value     所要加密的串
     */
    public static String encrypt(String encodeStr, String value) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(encodeStr);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        md.update(value.getBytes());
        byte[] md5Bytes = md.digest();
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 根据包名判断是否安装某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (NameNotFoundException e) {

        }

        if (info != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据Intent判断是否安装某应用
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean checkApkExist(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager()
                .queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     *  * 返回当前屏幕是否为竖屏。
     *  * @param context
     *  * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
     *  
     */
    public static boolean isScreenOriatationPortrait(Context context) {

        return context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT;

    }

    /**
     * 判读应用是否在后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {

        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses =
                activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance ==
                        ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    LogUtil.d("后台", appProcess.processName);
                    return true;
                } else {
                    LogUtil.d("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    //    /**
    //     * 是否在Home界面
    //     *
    //     * @return
    //     */
    //    public static boolean isHome(Context context) {
    //        // Activity管理器
    //        ActivityManager activityManager = (ActivityManager) context
    //                .getSystemService(Context.ACTIVITY_SERVICE);
    //    /*
    //     * 获得当前正在运行的任务
    //     * 返回最多任务数
    //     * mActivityManager.getRunningTasks(maxNum);
    //     * 这里1就够了 得到的即为当前正在运行（可见）的任务
    //     */
    //        List<ActivityManager.RunningTaskInfo> listRti = activityManager.getRunningTasks(1);
    //        return getHomes(context).contains(listRti.get(0).topActivity.getPackageName());
    //    }
    //
    //    /**
    //     * 得到所有的Home界面
    //     *
    //     * @return Home应用的包名
    //     */
    //    public static List<String> getHomes(Context context) {
    //        List<String> names = new ArrayList<String>();
    //        // 包管理器
    //        PackageManager packageManager = context.getPackageManager();
    //        Intent intent = new Intent(Intent.ACTION_MAIN);
    //        intent.addCategory(Intent.CATEGORY_HOME);
    //        // 查找出属于桌面应用的列表
    //        List<ResolveInfo> listRi = packageManager.queryIntentActivities(intent, PackageManager
    //                .MATCH_DEFAULT_ONLY);
    //        for (ResolveInfo ri : listRi) {
    //            names.add(ri.activityInfo.packageName);
    //        }
    //        return names;
    //    }

    /**
     * @param context
     */
    public static void startMarketActivity(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 安装指定APK
     *
     * @param context
     * @param apkPath
     */
    public static void InstallApk(Context context, String apkPath) {
        context.startActivity(createInstallIntent(apkPath));
    }

    /**
     * @param apkPath
     * @return
     */
    public static Intent createInstallIntent(String apkPath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + apkPath);
        LogUtil.d(TAG, "uri:" + uri);
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 启动指定APP
     *
     * @param context
     * @param packageName
     * @throws NameNotFoundException
     */
    public static void openApp(Context context, String packageName) throws NameNotFoundException {
        LogUtil.d(TAG, "openApp:" + packageName);
        PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo>
                apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /**
     * 获取手机序列号
     *
     * @return
     */
    public static String getSerial() {
        return Build.SERIAL;
    }


    /**
     * 创建桌面快捷方式
     *
     * @param context
     * @param name
     * @param icon
     * @param launcherIntent
     */
    public static void createShortcut(Context context, String name, Bitmap icon, Intent
            launcherIntent) {
        Intent addShortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        // 名字
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

        // 图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);

        // 设置关联程序
        addShortcutIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        try {
            // 发送广播
            context.sendBroadcast(addShortcutIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据包名判断当前手机是否安装某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalledApk(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (NameNotFoundException e) {

        }

        if (info != null) {
            return true;
        }
        return false;
    }

    public static String getBtAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Field field = null;
        try {
            field = BluetoothAdapter.class.getDeclaredField("mService");
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            if (method != null) {
                Object obj = method.invoke(bluetoothManagerService);
                if (obj != null) {
                    return obj.toString();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
