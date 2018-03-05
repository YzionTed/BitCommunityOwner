package com.bit.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.HouseholdBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/5.
 */

public class HouseholdAdapter extends RecyclerView.Adapter<HouseholdAdapter.ViewHolder> implements View.OnClickListener{

    private List<HouseholdBean> datas;
    public HouseholdAdapter(List<HouseholdBean> datas) {
        this.datas = datas;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public HouseholdAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_household ,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(HouseholdAdapter.ViewHolder holder, int position) {
        holder.tv_household_type.setText(datas.get(position).getOwner());
        holder.tv_household_name.setText(datas.get(position).getUserName());
//        if(!TextUtils.isEmpty(datas.get(position).getMobilePhone())){
//            holder.tv_household_phone.setText("("+ CommonUtils.replaceStar(datas.get(position).getMobilePhone(),3,6)+")");
//        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null!=datas?datas.size():0;
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
        TextView tv_household_type;
        ImageView iv_household;
        TextView tv_household_name;
        TextView tv_household_phone;
        TextView tv_household_limit;
        public ViewHolder(View view){
            super(view);
            tv_household_type = (TextView) view.findViewById(R.id.tv_household_type);
            iv_household = (ImageView) view.findViewById(R.id.iv_household);
            tv_household_name = (TextView) view.findViewById(R.id.tv_household_name);
            tv_household_phone = (TextView) view.findViewById(R.id.tv_household_phone);
            tv_household_limit = (TextView) view.findViewById(R.id.tv_household_limit);
        }
    }
}
