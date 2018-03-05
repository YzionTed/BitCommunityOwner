package com.BIT.fuxingwuye.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.MerchantBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 9:42
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements View.OnClickListener{

    private List<MerchantBean> datas;
    private OnItemClickListener mOnItemClickListener = null;

    public ShopAdapter(List<MerchantBean> datas) {
        this.datas = datas;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoaderUtil.setImageWithCache(datas.get(position).getPhoto(),holder.iv_shopicon);

        holder.tv_shopname.setText(datas.get(position).getMerchantName());
        holder.tv_shopaddr.setText(datas.get(position).getAddress());
        holder.tv_shopphone.setText(datas.get(position).getTelPhone());
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

        TextView tv_shopname,tv_shopaddr,tv_shopphone;
        ImageView iv_shopicon;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_shopname = (TextView)itemView.findViewById(R.id.tv_shopname);
            tv_shopaddr = (TextView)itemView.findViewById(R.id.tv_shopaddr);
            tv_shopphone = (TextView)itemView.findViewById(R.id.tv_shopphone);
            iv_shopicon = (ImageView)itemView.findViewById(R.id.iv_shopicon);
        }
    }
}
