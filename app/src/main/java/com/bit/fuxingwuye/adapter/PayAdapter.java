package com.bit.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 9:16
 */

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> implements View.OnClickListener {

    private List<PayListBean> datas;

    public PayAdapter(List<PayListBean> datas) {
        this.datas = datas;
    }
    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_pay_name.setText(datas.get(position).getTitle());
        holder.tv_pay_amount.setText("￥"+datas.get(position).getAmount());
        if (datas.get(position).getPayStatus()==1){
            holder.iv_status.setImageResource(R.mipmap.icon_unpay);
        }else if (datas.get(position).getPayStatus()==2){
            holder.iv_status.setImageResource(R.mipmap.icon_payed);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_pay_name,tv_pay_amount;
        ImageView iv_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_pay_name = (TextView)itemView.findViewById(R.id.tv_pay_name);
            tv_pay_amount = (TextView)itemView.findViewById(R.id.tv_pay_amount);
            iv_status = (ImageView)itemView.findViewById(R.id.iv_status);
        }
    }
}
