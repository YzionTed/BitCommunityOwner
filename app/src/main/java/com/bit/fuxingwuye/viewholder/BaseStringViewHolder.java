package com.bit.fuxingwuye.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bit.fuxingwuye.R;

/**
 * SmartCommunity-com.bit.fuxingwuye.viewholder
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 *
 * no binding  复杂的情况
 */
public class BaseStringViewHolder extends RecyclerView.ViewHolder {

    public View rl_spinner;
    public TextView tv_spinner;

    public BaseStringViewHolder(View itemView) {
        super(itemView);

        rl_spinner = itemView.findViewById(R.id.rl_spinner);
        tv_spinner = (TextView) itemView.findViewById(R.id.tv_spinner);
    }
}
