package com.BIT.fuxingwuye.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.BIT.communityOwner.net.Api;
import com.BIT.communityOwner.net.ResponseCallBack;
import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.login.LoginActivity;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.GetUserRoomListBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivitySplashBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding mBinding;
    ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

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

    @Override
    protected void initEventAndData() {
        aCache = ACache.get(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        MobclickAgent.setDebugMode(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (aCache.getAsString(HttpConstants.TOKEN) != null && !"".equals(aCache.getAsString(HttpConstants.TOKEN))) {
                    Intent intent = new Intent(SplashActivity.this, MainTabActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent it = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(it);
                    finish();
                }

            }
        }, 3000);
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void toastMsg(String msg) {

    }
}
