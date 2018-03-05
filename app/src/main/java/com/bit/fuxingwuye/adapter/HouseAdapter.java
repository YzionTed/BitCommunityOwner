package com.BIT.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.FloorBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/5.
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> implements View.OnClickListener{

    private List<FloorBean>  datas;
    public HouseAdapter(List<FloorBean> datas) {
        this.datas = datas;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public HouseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_housemanager ,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(HouseAdapter.ViewHolder holder, int position) {
        holder.tv_type.setVisibility(View.VISIBLE);
        holder.iv_type.setImageResource(R.mipmap.icon_house_black);
        holder.tv_community.setText(datas.get(position).getPlotName());
        holder.tv_address.setText(datas.get(position).getBanName()+"栋"+datas.get(position).getFloorName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
        public ViewHolder(View view){
            super(view);
            tv_community = (TextView) view.findViewById(R.id.tv_community);
            iv_type = (ImageView) view.findViewById(R.id.iv_type);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_limitetime = (TextView) view.findViewById(R.id.tv_limitetime);
        }
    }
}
