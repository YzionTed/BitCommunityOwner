package com.BIT.fuxingwuye.activities.videoDevices;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.adapter.VideoAdapter;
import com.BIT.fuxingwuye.base.BaseActivity;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.databinding.ActivityVideoDevicesBinding;
import com.BIT.fuxingwuye.utils.ACache;
import com.ddclient.configuration.DongConfiguration;
import com.ddclient.dongsdk.DeviceInfo;
import com.ddclient.jnisdk.InfoUser;
import com.ddclient.push.DongPushMsgManager;
import com.gViewerX.util.LogUtils;
import com.smarthome.yunintercom.sdk.AbstractIntercomCallbackProxy;
import com.smarthome.yunintercom.sdk.IntercomSDK;
import com.smarthome.yunintercom.sdk.IntercomSDKProxy;

import java.util.ArrayList;
import java.util.List;

public class VideoDevicesActivity extends BaseActivity<VideosPresenterImpl> implements VideosContract.View {

    private ActivityVideoDevicesBinding mBinding;
    private ArrayList<DeviceInfo> deviceInfoList = new ArrayList<>();
    private VideoAdapter mAdapter;

    private ListActivityIntercomAccountProxy mIntercomAccountProxy = new ListActivityIntercomAccountProxy();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_devices);
        mBinding.toolbar.actionBarTitle.setText("视频监控");
        mBinding.toolbar.btnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupVM() {

        mAdapter = new VideoAdapter(this);
        mBinding.xrecyclerview.setAdapter(mAdapter);
        mBinding.xrecyclerview.setOnItemClickListener(mListItemClick);

        boolean initIntercomAccount = IntercomSDKProxy.initCompleteIntercomAccount();
        if (!initIntercomAccount) {
            IntercomSDKProxy.initIntercomAccount(mIntercomAccountProxy);
        }
    }

    @Override
    protected void setupHandlers() {
        mBinding.toolbar.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this,msg+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVideos(List<RepairBean> datas) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntercomSDKProxy.registerIntercomAccountCallback(mIntercomAccountProxy);
        deviceInfoList = IntercomSDKProxy.requestGetDeviceListFromCache(this);
        mAdapter.setData(deviceInfoList);
        mAdapter.notifyDataSetChanged();
        LogUtils.e("ListActivity.clazz--->>>onResume.....deviceList:" + deviceInfoList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IntercomSDKProxy.unRegisterIntercomAccountCallback(mIntercomAccountProxy);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      /*  IntercomSDKProxy.requestSetPushInfo(0);
        IntercomSDK.finishIntercomSDK();*/
    }

    private AdapterView.OnItemClickListener mListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DeviceInfo deviceInfo = mAdapter.getItem(position);
            if (!deviceInfo.isOnline) {
                Toast.makeText(VideoDevicesActivity.this,"请选择在线设备",Toast.LENGTH_SHORT).show();
                return;
            }
            DongConfiguration.mDeviceInfo = deviceInfo;
            LogUtils.e("ListActivity.clazz--->>>onItemClick.....2222...deviceInfo:"
                    + deviceInfo);
//            IntercomSDKProxy.requestUnlock(deviceInfo.dwDeviceID);
            startActivity(new Intent(VideoDevicesActivity.this,
                    VideoViewActivity.class));
        }
    };

    private class ListActivityIntercomAccountProxy extends AbstractIntercomCallbackProxy.IntercomAccountCallbackImp {

        @Override
        public int onAuthenticate(InfoUser tInfo) {
            LogUtils.e("ListActivity.clazz--->>>onAuthenticate........tInfo:"
                    + tInfo);
            return 0;
        }

        @Override
        public int onUserError(int nErrNo) {
            LogUtils.e("ListActivity.clazz--->>>onUserError........nErrNo:"
                    + nErrNo);
            return 0;
        }

        @Override
        public int onNewListInfo() {
            deviceInfoList = IntercomSDKProxy.requestGetDeviceListFromCache(VideoDevicesActivity.this);
            mAdapter.setData(deviceInfoList);
            mAdapter.notifyDataSetChanged();
            LogUtils.e("ListActivity.clazz--->>>onNewListInfo........deviceInfoList.size:"
                    + deviceInfoList.size());
            return 0;
        }

        /**
         * 平台在线推送时回调该方法
         */
        @Override
        public int onCall(ArrayList<DeviceInfo> list) {
            LogUtils.e("ListActivity.clazz--->>>onCall........list.size():" + list.size());
            Toast.makeText(VideoDevicesActivity.this, "平台推送到达!!!", Toast.LENGTH_SHORT).show();
            int size = list.size();
            if (size > 0) {
                DeviceInfo deviceInfo = list.get(0);
                String message = deviceInfo.deviceName + deviceInfo.dwDeviceID
                        + deviceInfo.msg;
                DongPushMsgManager.pushMessageChange(VideoDevicesActivity.this, message);
            }
            return 0;
        }
    }
}
