package com.bit.fuxingwuye.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.bit.communityOwner.KeyString;
import com.bit.communityOwner.model.IMToken;
import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ApiRequester;
import com.bit.communityOwner.net.LogUtil;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.communityOwner.util.CheckSumBuilder;
import com.bit.communityOwner.util.RoomUtil;
import com.bit.fuxingwuye.Bluetooth.yunduijiang.YunDuiJIangUtils;
import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.fragment.elevatorFrag.ElevatorFragment;
import com.bit.fuxingwuye.activities.fragment.elevatorFrag.StoreElevatorListBeans;
import com.bit.fuxingwuye.activities.fragment.mainFragment.FragmentMain;
import com.bit.fuxingwuye.activities.fragment.mineFragment.FragmentMine;
import com.bit.fuxingwuye.activities.fragment.smartGate.BluetoothNetUtils;
import com.bit.fuxingwuye.activities.fragment.smartGate.FragmentDoor;
import com.bit.fuxingwuye.activities.houseManager.ApplicationRecordActivity;
import com.bit.fuxingwuye.activities.houseManager.HouseManagerActivity;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.AppVersionInfo;
import com.bit.fuxingwuye.bean.CardListBean;
import com.bit.fuxingwuye.bean.EvenBusMessage;
import com.bit.fuxingwuye.bean.GetUserRoomListBean;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.StoreDoorMILiBeanList;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.databinding.ActivityMainTabBinding;
import com.bit.fuxingwuye.newsdetail.NewsDetail;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.AppInfo;
import com.bit.fuxingwuye.utils.DownloadUtils;
import com.bit.fuxingwuye.utils.LiteOrmUtil;
import com.bit.fuxingwuye.utils.PermissionUtils;
import com.bit.fuxingwuye.views.TabItem;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.push.message.JPushBean;
import com.umeng.analytics.MobclickAgent;

import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class MainTabActivity extends SupportActivity {
    private static final String TAG = MainTabActivity.class.getSimpleName();
    public static MainTabActivity instance;
    private ActivityMainTabBinding mBinding;

    private Context mContext;

    private List<TabItem> mTabItemList;
    Toast toast = null;
    ACache aCache;

    private AppVersionInfo appVersionInfo;
    private YunDuiJIangUtils yunDuiJIangUtils;
    private OSS oss;
    private String downloadUrl;
    private OssToken ossTokenBean;
    private static final int REQUEST_CODE_LOCATION = 6;
    private String[] locPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入权限
            Manifest.permission.CAMERA,//相机
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,//电话
            Manifest.permission.RECORD_AUDIO//录音
    };
    private BluetoothNetUtils bluetoothNetUtils;

    public static int currentFragmentPosiont=0;//当前Fragment的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        BaseApplication.getInstance().addActivity(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_tab);
        aCache = ACache.get(this);
        instance = this;
        EventBus.getDefault().register(this);
        if (aCache.getAsString(HttpConstants.village) == null || "".equals(aCache.getAsString(HttpConstants.village))) {
            LemonHelloInfo info3 = new LemonHelloInfo()
                    .setTitle("切换小区")
                    .setTitleFontSize(18)
                    .setContentFontSize(16)
                    .setContent("尊敬的用户，当前尚未选择小区，部分功能无法实现。")
                    .addAction(new LemonHelloAction("不前往", Color.parseColor("#999999"), new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                            helloView.hide();

                        }
                    }))
                    .addAction(new LemonHelloAction("前往", Color.RED, new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                            helloView.hide();
                            Intent intent = new Intent(MainTabActivity.this, Housing.class);
                            startActivity(intent);
                        }
                    }));
            info3.show(MainTabActivity.this);
        }
        initTabData();
        initTabHost();
        initOssToken();

        yunDuiJIangUtils = new YunDuiJIangUtils();
//          yunDuiJIangUtils.login("13500000000", "123456");

        String phone = ACache.get(this).getAsString(HttpConstants.MOBILE);
        if (phone != null) {
            yunDuiJIangUtils.login(phone, "123456");
        }

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        }

        //从通知点击启动
        Bundle bundle = getIntent().getBundleExtra(KeyString.EXTRA_BUNDLE);
        if (bundle != null) {
            JPushBean jPushBean = (JPushBean) bundle.getSerializable("jpushbean");
            if (jPushBean != null) {
                switch (jPushBean.getAction()) {
                    case "100101"://社区公告
                        startActivity(new Intent(this, NewsDetail.class).putExtra("id", jPushBean.getData().getNotice_id()));
                        break;
                    case "100401"://房屋认证
                        startActivity(new Intent(this, HouseManagerActivity.class).putExtra("id", jPushBean.getData().getCommunityId()));
                        break;
                    case "100402"://房屋绑定
                        if (jPushBean.getData().getUserId().equals(aCache.getAsString(HttpConstants.USERID))) {
                            //如果申请人是自己，则跳到住房管理页面
                            startActivity(new Intent(this, HouseManagerActivity.class).putExtra("id", jPushBean.getData().getCommunityId()));
                        } else {
                            startActivity(new Intent(this, ApplicationRecordActivity.class).putExtra(HttpConstants.ROOMID, jPushBean.getData()
                                    .getRoomId()));
                        }
                        break;
                }
            }
        }

        createAccountId();

        getCardList();
    }

    //查询虚拟卡
    private void getCardList(){
        Api.getCardList(aCache.getAsString(HttpConstants.USERID), aCache.getAsString(HttpConstants.COMMUNIYID), new ResponseCallBack<List<CardListBean>>() {
            @Override
            public void onSuccess(List<CardListBean> data) {
                super.onSuccess(data);
                LiteOrmUtil.getInstance().getOrm().save(data);
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });
    }

    private void getAppVersion() {
        Api.getAppVersion(new ResponseCallBack<AppVersionInfo>() {
            @Override
            public void onSuccess(AppVersionInfo data) {
                super.onSuccess(data);
                appVersionInfo = data;
                if (appVersionInfo != null && ossTokenBean != null && oss != null) {
                    try {
                        downloadUrl = oss.presignConstrainedObjectURL(ossTokenBean.getBucket(), appVersionInfo.getUrl(), 30 * 60);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                    showDialog();
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        }, "5a961e7c0cf2c1914073dece", AppInfo.getVersionName(this));
    }

    private void downloadApk() {
        if (appVersionInfo == null || TextUtils.isEmpty(downloadUrl)) {
            Toast.makeText(this, "获取下载链接失败", Toast.LENGTH_SHORT).show();
            return;
        }
        DownloadUtils downloadUtils = new DownloadUtils(this);
        //http://183.240.119.164/imtt.dd.qq.com/16891/0AA5EAE67C378051755E7646A493F822.apk?mkey=5a9606732937e28d&f=b24&c=0&fsname=com.snda
        // .wifilocating_4.2.58_3188.apk&csr=1bbd&p=.apk
        downloadUtils.download(downloadUrl,
                AppInfo.getAppName(this) + appVersionInfo.getSequence() + ".apk");
    }

    private void showDialog() {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("版本更新");
        if (appVersionInfo.isForceUpgrade()) {
            builder.setMessage("发现新版本" + appVersionInfo.getSequence() + "，请下载更新！");
        } else {
            builder.setMessage("发现新版本" + appVersionInfo.getSequence() + "，是否更新？");
            builder.setNegativeButton("取消", null);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.show();
    }

    private void initOssToken() {
        Api.ossToken(new ResponseCallBack<OssToken>() {
            @Override
            public void onSuccess(final OssToken data) {
                if (data != null) {
                    ossTokenBean = data;
                    OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                            .getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken());
                    oss = new OSSClient(MainTabActivity.this, data.getEndPoint(), credentialProvider);
                }
                getAppVersion();
            }

            @Override
            public void onFailure(ServiceException e) {

            }
        });
    }

    private void initTabData() {
        mTabItemList = new ArrayList();

        String[] mTabTitle = new String[]{"首页", "门禁", "梯控", "我的"};
        mTabItemList.add(new TabItem(this, R.mipmap.icon_home_normal, R.mipmap.icon_home_selected, mTabTitle[0], FragmentMain.class));
        mTabItemList.add(new TabItem(this, R.mipmap.icon_door_normal, R.mipmap.icon_door_selected, mTabTitle[1], FragmentDoor.class));
        mTabItemList.add(new TabItem(this, R.mipmap.icon_elevator_normal, R.mipmap.icon_elevator_selected, mTabTitle[2], ElevatorFragment.class));
        mTabItemList.add(new TabItem(this, R.mipmap.icon_user_normal, R.mipmap.icon_user_selected, mTabTitle[3], FragmentMine.class));
    }

    private void initTabHost() {
        mBinding.mainTabhost.setup(this, getSupportFragmentManager(), R.id.main_tab_contents);
        mBinding.mainTabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mTabItemList.size(); i++) {
            TabItem tabItem = mTabItemList.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = mBinding.mainTabhost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView(i));
            mBinding.mainTabhost.addTab(tabSpec, tabItem.getFragmentClass(), null);

            //默认选中第一个tab
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }
        mBinding.mainTabhost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HavaPermission(1);
            }
        });
        mBinding.mainTabhost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HavaPermission(2);
            }
        });
        mBinding.mainTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //重置Tab样式
                for (int i = 0; i < mTabItemList.size(); i++) {
                    TabItem tabitem = mTabItemList.get(i);
                    if (tabId.equals(tabitem.getTitleString())) {
                        currentFragmentPosiont=i;
                        tabitem.setChecked(true);
                    } else {
                        tabitem.setChecked(false);
                    }
                }
                upBlueToothDate();
            }
        });

        upBlueToothDate();
    }



    /**
     * 更新蓝牙数据
     */
    public void upBlueToothDate(){
        miLiUpDate();
        upElevatorDate();
    }

    /**
     * 米粒的数据更新缓存
     */
    private void miLiUpDate() {
        if (bluetoothNetUtils == null) {
            bluetoothNetUtils = new BluetoothNetUtils();
        }
        StoreDoorMILiBeanList doorMILiBeans = bluetoothNetUtils.getBletoothDoorDate();
        if (doorMILiBeans != null) {
            if (doorMILiBeans.isTimeOutNow()) {
                bluetoothNetUtils.getMiLiNetDate(null, 2, null);
            }
        } else {
            bluetoothNetUtils.getMiLiNetDate(null, 2, null);
        }
    }

    /**
     * 电梯蓝牙的数据更新缓存
     */
    private void upElevatorDate() {
        if (bluetoothNetUtils == null) {
            bluetoothNetUtils = new BluetoothNetUtils();
        }
        StoreElevatorListBeans bletoothElevateDate = bluetoothNetUtils.getBletoothElevateDate();
        if (bletoothElevateDate != null) {
            if (bletoothElevateDate.isTimeOutNow()) {
                bluetoothNetUtils.getBluetoothElevatorDate(2, null);
            }
        } else {
            bluetoothNetUtils.getBluetoothElevatorDate( 2, null);
        }
    }

    private void HavaPermission(int currentab) {
        if (aCache.getAsString(HttpConstants.COMMUNIYID) == null || "".equals(aCache.getAsString(HttpConstants.COMMUNIYID))) {
            Toast.makeText(MainTabActivity.this, "请选择小区", Toast.LENGTH_LONG).show();
        } else if (!RoomUtil.hasRoom(aCache.getAsString(HttpConstants.COMMUNIYID))) {
            //   showDigiog();
            mBinding.mainTabhost.setCurrentTab(currentab);
        } else {
            mBinding.mainTabhost.setCurrentTab(currentab);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        final String cid = aCache.getAsString(HttpConstants.COMMUNIYID);
        if (!TextUtils.isEmpty(cid)) {
            Api.getUserRoomsList(cid, new ResponseCallBack<List<GetUserRoomListBean>>() {
                @Override
                public void onSuccess(List<GetUserRoomListBean> data) {
                    if (data != null && !data.isEmpty()) {
                        aCache.put(cid, "1");
                    } else {
                        aCache.put(cid, "0");
                    }
                }
            });
        }
    }

    private void showDigiog() {
        LemonHelloInfo info3 = new LemonHelloInfo()
                .setTitle("权限不足")
                .setTitleFontSize(18)
                .setContentFontSize(16)
                .setContent("该功能暂不开放，需要完成房产认证后方可体验！")
                .addAction(new LemonHelloAction("暂不认证", Color.parseColor("#999999"), new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                        helloView.hide();

                    }
                }))
                .addAction(new LemonHelloAction("立即认证", Color.RED, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                        Intent intent = new Intent(MainTabActivity.this, ReplenishDataActivity.class);
                        startActivity(intent);
                    }
                }));
        info3.show(MainTabActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != toast)
            toast.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
        EventBus.getDefault().unregister(this);
        MobclickAgent.onProfileSignOff();
//        BaseApplication.finishAllActivity();
//        System.exit(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finsh(EvenBusMessage messageEvent) {
        if (messageEvent.getEvent().equals("finish")) {
            finish();
        }
    }

//    private boolean mIsExit;

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mIsExit) {
//                toast.cancel();
//                MobclickAgent.onProfileSignOff();
//                BaseApplication.getInstance().exitApp();
//
//            } else {
//                if (toast == null) {
//                    toast = Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT);
//                }
//                toast.show();
//                mIsExit = true;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mIsExit = false;
//                    }
//                }, 2000);
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

    private void requestPermissions() {
        PermissionUtils.checkMorePermissions(MainTabActivity.this, locPermissions,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        // initLoc();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showExplainDialog(permission, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionUtils.requestMorePermissions(MainTabActivity.this, locPermissions, REQUEST_CODE_LOCATION);
                            }
                        });
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestMorePermissions(MainTabActivity.this, locPermissions, REQUEST_CODE_LOCATION);
                    }
                });
    }

    /**
     * 解释权限的dialog
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(MainTabActivity.this)
                .setTitle("申请权限")
                .setMessage("APP需要相关的权限才能正常运行")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    private static final int REQUEST_CODE_STORAGE = 5;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_STORAGE:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    downloadApk();
                } else {
                    Toast.makeText(MainTabActivity.this, "获取读写SD卡权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCATION:
                PermissionUtils.onRequestMorePermissionsResult(MainTabActivity.this, permissions, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {

                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showExplainDialog(permission, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionUtils.requestMorePermissions(MainTabActivity.this, locPermissions, REQUEST_CODE_LOCATION);
                            }
                        });
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        showToAppSettingDialog();
                    }
                });
                break;
        }

    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(MainTabActivity.this)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(MainTabActivity.this);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    private void createAccountId() {
        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("AppKey", "c7d64ed61462dfac25c0089ab171eaa4");
        requestParams.addHeader("Nonce", "123456");
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        requestParams.addHeader("CurTime", curTime);
        requestParams.addHeader("CheckSum", CheckSumBuilder.getCheckSum("744182fbc16c", "123456", curTime));
        requestParams.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        TokenBean tokenBean = (TokenBean) ACache.get(mContext).getAsObject(HttpConstants.TOKENBEAN);
        if (tokenBean != null) {
            requestParams.addBodyParameter("accid", tokenBean.getPhone());
        }
//        requestParams.addBodyParameter("accid", "15900020005");

//        String url = "https://api.netease.im/nimserver/user/create.action";//{"desc":"already register","code":414}
        String url = "https://api.netease.im/nimserver/user/refreshToken.action";
//        ApiRequester.sendRequest(url0, requestParams, mResponseCallBack);
        ApiRequester.sendRequest(url, requestParams, mResponseCallBack);
    }


    //{"code":200,"info":{"token":"338fdb41436631cd5ced4d73950154d1","accid":"15900010001"}}
    ResponseCallBack mResponseCallBack = new ResponseCallBack<IMToken>(false) {

        @Override
        public void onSuccess(IMToken data) {
            Toast.makeText(mContext, "onSuccess", Toast.LENGTH_SHORT).show();
            NimUIKit.login(new LoginInfo(data.getInfo().getAccid(), data.getInfo().getToken()), new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
                    Toast.makeText(mContext, "login im onSuccess", Toast.LENGTH_SHORT).show();
                    LogUtil.d(TAG, "login im onSuccess");
                }

                @Override
                public void onFailed(int code) {
                    LogUtil.d(TAG, "onFailed:" + code);
                }

                @Override
                public void onException(Throwable exception) {
                    LogUtil.d(TAG, "onException:" + exception.getMessage());
                }
            });

        }

        @Override
        public void onFailure(ServiceException e) {
            LogUtil.d(TAG, e.getMsg());
        }
    };
}
