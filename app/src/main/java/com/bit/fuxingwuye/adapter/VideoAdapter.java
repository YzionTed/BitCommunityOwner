package com.bit.fuxingwuye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;


import com.bit.fuxingwuye.R;
import com.ddclient.dongsdk.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 9:42
 */

public class VideoAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DeviceInfo> mDeviceInfoList = new ArrayList<>();

    public VideoAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    public void setData(ArrayList<DeviceInfo> deviceList) {
        mDeviceInfoList.clear();
        for (DeviceInfo deviceInfo : deviceList) {
            if (deviceInfo != null) {
                mDeviceInfoList.add(deviceInfo);
            }
        }
    }
    public ArrayList<DeviceInfo> getData() {
        return mDeviceInfoList;
    }

    @Override
    public int getCount() {
        return mDeviceInfoList.size();
    }

    @Override
    public DeviceInfo getItem(int position) {
        return mDeviceInfoList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder hold;
        if (convertView == null) {
            hold = new Holder();
            convertView = mInflater
                    .inflate(R.layout.item_video, null);
            convertView.setTag(hold);
            hold.tv_videoname = (TextView) convertView
                    .findViewById(R.id.tv_videoname);
            hold.tv_videocode = (TextView) convertView.findViewById(R.id.tv_videocode);
            hold.tv_videostate = (TextView) convertView.findViewById(R.id.tv_videostate);
        } else {
            hold = (Holder) convertView.getTag();
        }

        hold.tv_videoname.setText(getData().get(position).deviceName);
        hold.tv_videocode.setText(getData().get(position).dwDeviceID+"");
        DeviceInfo infoDevice = getData().get(position);
        if (infoDevice.isOnline) {
            if (DeviceInfo.isAuthDeviceType(infoDevice, 23)) {
                hold.tv_videostate.setText("授权设备(在线)");
            } else {
                hold.tv_videostate.setText("我的设备(在线)");
            }
        } else {
            if (DeviceInfo.isAuthDeviceType(infoDevice, 23)) {
                hold.tv_videostate.setText("授权设备(离线)");
            } else {
                hold.tv_videostate.setText("我的设备(离线)");
            }
        }
        return convertView;
    }

    private static class Holder {
        TextView tv_videoname;
        TextView tv_videocode;
        TextView tv_videostate;
    }
}
