package com.bit.fuxingwuye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.HouseholdBean;
import com.bit.fuxingwuye.bean.RecordData;

import java.util.List;

/**
 * Created by Dell on 2017/7/5.
 */

public class RecordAdapter extends android.widget.BaseAdapter{

    private List<ProprietorBean.RecordsBean> datas;
    Context context;
    public RecordAdapter(Context context, List<ProprietorBean.RecordsBean> datas) {
        this.datas = datas;
        this.context=context;
    }



    @Override
    public int getCount() {
        return null!=datas?datas.size():0;
    }

    @Override
    public ProprietorBean.RecordsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(context,
                    R.layout.record_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        if(datas.get(position).getRelationship()==1){
            holder.relationship.setText("业主");
        }else if(datas.get(position).getRelationship()==2){
            holder.relationship.setText("家属");
        }else if(datas.get(position).getRelationship()==3){
            holder.relationship.setText("租客");
        }
        holder.name.setText(""+datas.get(position).getName());
        holder.address.setText(""+datas.get(position).getRoomLocation());
        if(datas.get(position).getAuditStatus()==0){
            holder.status.setText("未审核");
        }else if(datas.get(position).getAuditStatus()==1){
            holder.status.setText("审核通过");
        }else if(datas.get(position).getAuditStatus()==-1){
            holder.status.setText("驳回");
        }else if(datas.get(position).getAuditStatus()==-2){
            holder.status.setText("违规");
        }else if(datas.get(position).getAuditStatus()==3){
            holder.status.setText("已解绑");
        }else if(datas.get(position).getAuditStatus()==2){
            holder.status.setText("已注销");
        }else{
            holder.status.setText("未知");
        }
        return convertView;
    }


    class ViewHolder {

        TextView relationship;
        TextView name;
        TextView address;
        TextView status;

        public ViewHolder(View view) {
            relationship = (TextView) view.findViewById(R.id.relationship);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            status = (TextView) view.findViewById(R.id.status);
            view.setTag(this);
        }
    }
}
