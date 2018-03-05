package com.BIT.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.ParkBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/5.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> implements View.OnClickListener{

    private List<ParkBean>  datas;

    public CarAdapter(List<ParkBean> datas) {
        this.datas = datas;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_housemanager ,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(CarAdapter.ViewHolder holder, int position) {
        holder.tv_type.setVisibility(View.GONE);
        holder.ll_household_manager.setVisibility(View.GONE);
        holder.iv_type.setImageResource(R.mipmap.icon_car_black);
        holder.tv_community.setText(datas.get(position).getAddress().split(",")[0]);
        holder.tv_address.setText(datas.get(position).getAddress());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null == datas?0:datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_community;
        ImageView iv_type;
        TextView tv_type;
        TextView tv_address;
        TextView tv_limitetime;
        LinearLayout ll_household_manager;
        public ViewHolder(View view){
            super(view);
            tv_community = (TextView) view.findViewById(R.id.tv_community);
            iv_type = (ImageView) view.findViewById(R.id.iv_type);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_limitetime = (TextView) view.findViewById(R.id.tv_limitetime);
            ll_household_manager = (LinearLayout)view.findViewById(R.id.ll_household_manager);
        }
    }
}
