package com.BIT.fuxingwuye.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.PayListBean;
import com.BIT.fuxingwuye.bean.ViaBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 9:16
 */

public class ViaAdapter extends RecyclerView.Adapter<ViaAdapter.ViewHolder> implements View.OnClickListener {

    private List<ViaBean> datas;

    public ViaAdapter(List<ViaBean> datas) {
        this.datas = datas;
    }
    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_via,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date date1 = new Date(Long.parseLong(datas.get(position).getBeginTime()));
        Date date2 = new Date(Long.parseLong(datas.get(position).getEndTime()));
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd HH:mm");
        holder.tv_via_time.setText(sdf.format(date1)+"-"+sdf.format(date2));
        if (datas.get(position).getViaStatus()==-1){
            holder.tv_status.setText("已过期");
            holder.tv_status.setTextColor(Color.RED);
        }if (datas.get(position).getViaStatus()==1){
            holder.tv_status.setText("可使用");
            holder.tv_status.setTextColor(Color.BLUE);
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

        TextView tv_via_name,tv_via_time,tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_via_name = (TextView)itemView.findViewById(R.id.tv_via_name);
            tv_via_time = (TextView)itemView.findViewById(R.id.tv_via_time);
            tv_status = (TextView)itemView.findViewById(R.id.tv_status);
        }
    }
}
