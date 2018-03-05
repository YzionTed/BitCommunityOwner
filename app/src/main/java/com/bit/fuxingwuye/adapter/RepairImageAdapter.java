package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.http.HttpProvider;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Dell on 2017/7/5.
 */

public class RepairImageAdapter extends RecyclerView.Adapter<RepairImageAdapter.ViewHolder> implements View.OnClickListener{

    private List<String> datas;
    private Context context;

    public RepairImageAdapter(Context context,List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item ,parent,false);
            ViewHolder vh = new ViewHolder(view);
            view.setOnClickListener(this);
            return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String[] paths = datas.get(position).split("\\\\");
        StringBuffer sb = new StringBuffer().append(HttpProvider.getHttpIpAdds());
        for (int i=0;i<paths.length;i++){
            sb.append("/").append(paths[i]);
        }
        Log.e("name",sb.toString());
        Glide.with(context).load(sb.toString()).into(holder.item_iv);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.size();
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
        TextView service_name;
        ImageView item_iv;
        public ViewHolder(View view){
            super(view);
            item_iv = (ImageView)view.findViewById(R.id.item_iv);
            service_name = (TextView) view.findViewById(R.id.service_name);
        }
    }
}
