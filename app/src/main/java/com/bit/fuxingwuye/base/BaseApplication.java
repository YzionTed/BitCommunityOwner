package com.bit.fuxingwuye.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.bit.fuxingwuye.Bluetooth.BluetoothApplication;
import com.bit.fuxingwuye.dagger.component.AppComponent;
import com.bit.fuxingwuye.dagger.component.DaggerAppComponent;
import com.bit.fuxingwuye.dagger.module.AppModule;
import com.bit.fuxingwuye.utils.LogUtil;
import com.ddclient.push.DongPushMsgManager;
import com.facebook.stetho.Stetho;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.inuker.bluetooth.library.BluetoothClientManger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smarthome.yunintercom.sdk.IntercomSDK;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * SmartCommunity-com.bit.fuxingwuye.base
 * 作者： YanwuTang
 * 时间： 2017/6/30.
 */

public class BaseApplication extends MultiDexApplication {

    private static final String TAG = "BasicApplication";

    private static BaseApplication instance;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    private String token = "";

    private Context context;

    private BluetoothApplication blueToothApp;
    private static BluetoothClientManger bluetoothClientManger;//蓝牙梯禁

    @Override
    public void onCreate() {
        super.onCreate();

        // 当程序发生Uncaught异常的时候,由该类来接管程序,保存到log中
        //CrashHandler.getInstance().init(this);

        context = getApplicationContext();
        instance = this;
        if (LogUtil.D) {  // chrome 测试
            Stetho.initializeWithDefaults(this);
        }
        getScreenSize();
        initImageLoader();
        IntercomSDK.initIntercomSDK(this);
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);

        x.Ext.init(this);

        registerActivityLifecycleCallbacks();


        bluetoothClientManger = new BluetoothClientManger(this);
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        blueToothApp = new BluetoothApplication(this);

        int result = IntercomSDK.initIntercomSDK(this);//米粒
        Log.e("===","IntercomSDK  result=="+result);
        //初始化推送设置
        IntercomSDK.initializePush(this, DongPushMsgManager.PUSH_TYPE_GETUI);
        IntercomSDK.initializePush(this, DongPushMsgManager.PUSH_TYPE_JG);

    }

    public static BaseApplication getInstance() {
        if (instance == null) {
            Log.e("BasicApplication",
                    " 程序出错,BasicApplication instance is null");
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(instance)
                .writeDebugLogs()//打开log打印图片加载信息
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 初始化屏幕宽高
     */
    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public static AppComponent getComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }


//    /**
//     * 添加activity
//     */
//    public void addActivity(Activity act) {
//        if (allActivities == null) {
//            allActivities = new HashSet<>();
//        }
//        allActivities.add(act);
//    }

//    /**
//     * 移除activity
//     */
//    public void removeActivity(Activity act) {
//        if (allActivities != null) {
//            allActivities.remove(act);
//        }
//    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (!act.isFinishing()) {
                        act.finish();
                    }
                }
            }
        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

    public boolean isPhoneEnalbe() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean callPhoneEnalbe() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isFileEnalbe() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkPhoneEnable(final Activity context) {
        if (!isPhoneEnalbe()) {
            LemonHello.getWarningHello("提示", "需要获取机型信息才能继续提供服务？")
                    .addAction(new LemonHelloAction("是", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, 4);
                            lemonHelloView.hide();
                        }
                    }))
                    .addAction(new LemonHelloAction("否", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    }))
                    .show(context);
            return false;
        }
        return true;
    }

    public boolean checkCallPhoneEnable(final Activity context) {
        if (!callPhoneEnalbe()) {
            LemonHello.getWarningHello("提示", "是否允许向外拨打电话？")
                    .addAction(new LemonHelloAction("是", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, 5);
                            lemonHelloView.hide();
                        }
                    }))
                    .addAction(new LemonHelloAction("否", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    }))
                    .show(context);
            return false;
        }
        return true;
    }

    public boolean checkWriteReadEnable(final Activity context) {
        if (!isFileEnalbe()) {
            LemonHello.getWarningHello("提示", "需要获取读写文件权限才能继续提供服务？")
                    .addAction(new LemonHelloAction("是", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                            lemonHelloView.hide();
                        }
                    }))
                    .addAction(new LemonHelloAction("否", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    }))
                    .show(context);
            return false;
        }
        return true;
    }


    private static List<Activity> listActivity = new ArrayList<>();
    public static Activity sCurrActivity;

    /**
     * activity 生命周期监听
     */
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                sCurrActivity = activity;

                addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                removeActivity(activity);
            }
        });
    }

    public static void addActivity(Activity activity) {
        listActivity.add(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : listActivity) {
            activity.finish();
        }
        listActivity.clear();
    }

    public static void removeActivity(Activity activity) {
        listActivity.remove(activity);
    }
    public BluetoothApplication getBlueToothApp() {
        return blueToothApp;
    }

}
