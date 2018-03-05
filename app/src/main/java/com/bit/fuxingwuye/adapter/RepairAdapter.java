package com.BIT.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.RepairBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 9:42
 */

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> implements View.OnClickListener{

    private List<RepairBean> datas;
    private OnItemClickListener mOnItemClickListener = null;

    public RepairAdapter(List<RepairBean> datas) {
        this.datas = datas;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , String position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repair,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(datas.get(position).getRepairType().equals("住户")){      //住户
            if(datas.get(position).getFaultType().equals("水电燃气")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_water);
            }else if(datas.get(position).getFaultType().equals("安防消防")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_fire);
            }else if(datas.get(position).getFaultType().equals("房屋结构")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_house);
            }else if(datas.get(position).getFaultType().equals("其他")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_personalother);
            }
        }else if(datas.get(position).getRepairType().equals("公共物业")){    //公共
            if(datas.get(position).getFaultType().equals("门禁")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_gate);
            }else if(datas.get(position).getFaultType().equals("电梯")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_elevator);
            }else if(datas.get(position).getFaultType().equals("其他")){
                holder.iv_type.setImageResource(R.mipmap.icon_small_publicother);
            }
        }
        holder.repair_type.setText(datas.get(position).getRepairType()+" "+datas.get(position).getFaultType());
        holder.repair_time.setText(datas.get(position).getRepairTime());
        holder.repair_status.setText(datas.get(position).getRepairStatus());
        holder.itemView.setTag(datas.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView repair_type,repair_time,repair_status;
        ImageView iv_type;

        public ViewHolder(View itemView) {
            super(itemView);
            repair_type = (TextView)itemView.findViewById(R.id.repair_type);
            repair_time = (TextView)itemView.findViewById(R.id.repair_time);
            repair_status = (TextView)itemView.findViewById(R.id.repair_status);
            iv_type = (ImageView)itemView.findViewById(R.id.iv_type);
        }
    }
}
