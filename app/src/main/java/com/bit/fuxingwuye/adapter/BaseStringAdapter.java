package com.bit.fuxingwuye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.interfaces.OnCommon2Listener;
import com.bit.fuxingwuye.interfaces.OnCommonListener;
import com.bit.fuxingwuye.viewholder.BaseStringViewHolder;

import java.util.List;

/**
 * SmartCommunity-com.bit.fuxingwuye.adapter
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 *
 * no  binding  特殊情况处理
 */

public class BaseStringAdapter<T> extends RecyclerView.Adapter<BaseStringViewHolder> {
    private Context context;
    private OnCommon2Listener listner2;
    private OnCommonListener listner;
    protected List<T> datas;
    private int choosedId = -1;

    public void setData(List<T> datas){
        this.datas = datas;
    }

    public BaseStringAdapter(Context context){
        this.context = context;
    }

    @Override
    public BaseStringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_string, parent, false);
        return new BaseStringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseStringViewHolder holder, final int position) {

        if (choosedId == position){
            holder.tv_spinner.setTextColor(context.getResources().getColor(R.color.common_bg_blue));
        } else {
            holder.tv_spinner.setTextColor(context.getResources().getColor(R.color.bs_grary2));
        }

        holder.rl_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosedId != position) {
                    int lastItem = choosedId;
                    choosedId = position;
                    if (choosedId >= 0) {
                        notifyItemChanged(lastItem);
                    }
                    notifyItemChanged(position);
                    if (listner != null) {
                        listner.OnCallBack(datas.get(position));
                    }
                    if (listner2 != null) {
                        listner2.OnCallBack(position, datas.get(position));
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setOnItemClickListner(OnCommonListener listner){
        this.listner = listner;
    }
    public void setOnItemClickListner(OnCommon2Listener listner){
        this.listner2 = listner;
    }

    public void setChoosedId(int postion){
        choosedId = postion;
    }
}
