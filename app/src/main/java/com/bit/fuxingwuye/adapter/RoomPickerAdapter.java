package com.bit.fuxingwuye.adapter;

import android.content.Context;

import com.bit.fuxingwuye.bean.PlotInfoBean;
import com.bit.fuxingwuye.viewholder.BaseStringViewHolder;

/**
 * SmartCommunity-com.bit.fuxingwuye.adapter
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class RoomPickerAdapter extends BaseStringAdapter {
    public RoomPickerAdapter(Context context) {
        super(context);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseStringViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (datas.get(position) instanceof PlotInfoBean){
            //holder.tv_spinner.setText(((PlotInfoBean) datas.get(position)).getValue());
        }
    }
}
