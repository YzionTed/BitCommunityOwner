package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.utils.CommonUtils;

import java.util.List;

/**
 * Created by Dell on 2017/11/7.
 * Created time:2017/11/7 14:36
 */

public class PhotoAdapter extends android.widget.BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Integer> bitmaps;

    public PhotoAdapter(Context context, List<Integer> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder hold;
        if (convertView == null) {
            hold = new Holder();
            convertView = mInflater
                    .inflate(R.layout.service_item, null);
            convertView.setTag(hold);
            hold.item_iv = (ImageView) convertView
                    .findViewById(R.id.item_iv);
        } else {
            hold = (Holder) convertView.getTag();
        }
        hold.item_iv.setImageResource(R.mipmap.avatar_default);
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                CommonUtils.dp2px(context,40));
        convertView.setLayoutParams(param);
        return convertView;
    }

    private static class Holder {
        ImageView item_iv;
    }
}
