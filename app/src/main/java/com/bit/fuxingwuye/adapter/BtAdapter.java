package com.bit.fuxingwuye.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.fuxingwuye.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017/8/8.
 * Created time:2017/8/8 14:03
 */

public class BtAdapter extends RecyclerView.Adapter<BtAdapter.ViewHolder> implements View.OnClickListener{

    private List<BluetoothDevice> datas;
    private HouseholdAdapter.OnItemClickListener mOnItemClickListener = null;

    public BtAdapter(List<BluetoothDevice> datas) {
        this.datas = datas;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void addBleDevice(BluetoothDevice device){
        if (datas==null){
            datas = new ArrayList<>();
        }
        if(datas.contains(device)||device==null|| TextUtils.isEmpty(device.getAddress())){
            return;
        }
        for (BluetoothDevice de: datas){
            if (de.getAddress().equalsIgnoreCase(device.getAddress())){
                return;
            }
        }
        datas.add(device);
    }

    public List<BluetoothDevice> getDevices(){
        if (this.datas == null){
            datas = new ArrayList<>();
        }
        return datas;
    }

    @Override
    public BtAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(BtAdapter.ViewHolder holder, int position) {
        holder.blue_name.setText(datas.get(position).getName());
        holder.blue_address.setText(datas.get(position).getAddress());
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

    public void setOnItemClickListener(HouseholdAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView blue_name;
        TextView blue_address;

        public ViewHolder(View view){
            super(view);

            blue_name = (TextView) view.findViewById(R.id.blue_name);
            blue_address = (TextView) view.findViewById(R.id.blue_address);
        }
    }
}
