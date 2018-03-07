package com.bit.fuxingwuye.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.PayListBean;
import com.bit.fuxingwuye.bean.ViaBean;
import com.bit.fuxingwuye.bean.request.PassCodeBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 9:16
 */

public class ViaAdapter extends RecyclerView.Adapter<ViaAdapter.ViewHolder> implements View.OnClickListener {

    private List<PassCodeBean> datas;

    public ViaAdapter(List<PassCodeBean> datas) {
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
        Date date1 = new Date(datas.get(position).getBeginAt());
        Date date2 = new Date(datas.get(position).getEndAt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        holder.tv_via_time.setText(sdf.format(date1)+" ~ "+sdf.format(date2));
        holder.tv_via_name.setText(datas.get(position).getItems()+"");
        if (datas.get(position).getAuditStatus()==-1){
            holder.tv_status.setText("已过期");
            holder.tv_status.setTextColor(Color.RED);
        }if (datas.get(position).getAuditStatus()==1){
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
