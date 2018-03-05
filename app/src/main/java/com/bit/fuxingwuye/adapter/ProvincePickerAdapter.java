package com.bit.fuxingwuye.adapter;

import android.content.Context;

import com.bit.fuxingwuye.bean.CityBean;
import com.bit.fuxingwuye.bean.DistrictBean;
import com.bit.fuxingwuye.bean.ProvinceBean;
import com.bit.fuxingwuye.viewholder.BaseStringViewHolder;

/**
 * SmartCommunity-com.bit.fuxingwuye.adapter
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class ProvincePickerAdapter extends BaseStringAdapter {
    public ProvincePickerAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(BaseStringViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (datas.get(position) instanceof ProvinceBean) {
            holder.tv_spinner.setText(((ProvinceBean) datas.get(position)).getName());
        } else if (datas.get(position) instanceof CityBean) {
            holder.tv_spinner.setText(((CityBean) datas.get(position)).getName());
        } else if (datas.get(position) instanceof DistrictBean) {
            holder.tv_spinner.setText(((DistrictBean) datas.get(position)).getName());
        }
    }
}
