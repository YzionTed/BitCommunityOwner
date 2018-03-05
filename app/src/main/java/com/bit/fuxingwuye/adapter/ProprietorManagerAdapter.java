package com.bit.fuxingwuye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.RoomList;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;

import java.util.List;

/**
 * Created by 23 on 2018/2/26.
 */

public class ProprietorManagerAdapter extends android.widget.BaseAdapter {
    Context context;
    List<ProprietorBean.RecordsBean> records;
    ACache cache;

    public ProprietorManagerAdapter(Context context, List<ProprietorBean.RecordsBean> records) {
        this.context = context;
        cache=ACache.get(context);
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public ProprietorBean.RecordsBean getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
    if(records.get(position).getRelationship()==1){
        return 0;
    }else
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.proprietor_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.name.setText(records.get(position).getName());
        holder.phone.setText(records.get(position).getContractPhone());
        if(records.get(position).getRelationship()==1){
            holder.relationship.setText("业主");
        }else if(records.get(position).getRelationship()==2){
            holder.relationship.setText("家属");
        }else if(records.get(position).getRelationship()==3){
            holder.relationship.setText("租客");
        }

        return convertView;
    }

    class ViewHolder {

        TextView relationship,name,phone,datatv;

        public ViewHolder(View view) {

            relationship = (TextView) view.findViewById(R.id.relationship);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            datatv = (TextView) view.findViewById(R.id.datatv);
            view.setTag(this);
        }
    }

}
