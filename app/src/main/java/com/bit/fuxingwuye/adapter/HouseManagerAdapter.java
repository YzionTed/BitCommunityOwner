package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.RoomList;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.ACache;

import java.util.List;

/**
 * Created by 23 on 2018/2/26.
 */

public class HouseManagerAdapter extends android.widget.BaseAdapter {
    Context context;
    List<RoomList> floorBeen;
    ACache cache;

    public HouseManagerAdapter(Context context, List<RoomList> floorBeen) {
        this.context = context;
        cache=ACache.get(context);
        this.floorBeen = floorBeen;
    }

    @Override
    public int getCount() {
        return floorBeen.size();
    }

    @Override
    public RoomList getItem(int position) {
        return floorBeen.get(position);
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

        if (floorBeen.get(position).getAuditStatus() == 1) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.communityname.setText(cache.getAsString(HttpConstants.village));
        if(floorBeen.get(position).getRelationship()==1){
            holder.relationship.setText("业主");
        }else if(floorBeen.get(position).getRelationship()==2){
            holder.relationship.setText("家属");
        }else{
            holder.relationship.setText("租客");
        }
        holder.auditStatustv.setVisibility(View.GONE);
        holder.roomLocation.setText(floorBeen.get(position).getRoomLocation());
        if(floorBeen.get(position).getAuditStatus()==0){
            holder.auditStatus.setText("未审核");
        }else if(floorBeen.get(position).getAuditStatus()==1){
            holder.auditStatus.setText("审核通过");

        }else if(floorBeen.get(position).getAuditStatus()==-1){
            holder.auditStatus.setText("驳回");
        }else if(floorBeen.get(position).getAuditStatus()==-2){
            holder.auditStatus.setText("违规");
        }
        if(floorBeen.get(position).getRelationship()==1&&floorBeen.get(position).getAuditStatus()==1){
            holder.auditStatustv.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {

        TextView communityname,relationship,roomLocation,auditStatus,auditStatustv;

        public ViewHolder(View view) {
            communityname = (TextView) view.findViewById(R.id.communityname);
            relationship = (TextView) view.findViewById(R.id.relationship);
            roomLocation = (TextView) view.findViewById(R.id.roomLocation);
            auditStatus = (TextView) view.findViewById(R.id.auditStatus);
            auditStatustv = (TextView) view.findViewById(R.id.auditStatustv);
            view.setTag(this);
        }
    }

}
