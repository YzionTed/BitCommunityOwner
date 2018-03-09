package com.bit.fuxingwuye.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.bean.request.NoticeBean;
import com.bit.fuxingwuye.utils.LogUtil;
import com.bit.fuxingwuye.utils.Tag;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.bit.fuxingwuye.activities.houseManager.ApplicationDetailsActivity.getFormatTime;

/**
 * Created by Dell on 2017/7/5.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>{

    private List<NoticeBean> datas;

    public ServicesAdapter(List<NoticeBean> datas) {
        this.datas = datas;
    }
    public ServicesAdapter() {

    }
    public void LoadMore(List<NoticeBean> datas){
        datas.addAll(datas);

    }
    public void setDatas(List<NoticeBean> datas){
        this.datas = datas;
    }
    public NoticeBean getData(int position){
        return datas.get(position);
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, String id,int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       holder.tv_infotitle.setText(datas.get(position).getBody());
        holder.tv_infotype.setText(datas.get(position).getTitle());

        holder.tv_infotime.setText(getFormatTime(datas.get(position).getUpdateAt()));
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(datas.get(position).getId());
        holder.item_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, (String) v.getTag(),position);
               LogUtil.e(Tag.tag,"position:"+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }




    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView item_info;
        TextView tv_infotype;
        TextView tv_infotime;
        TextView tv_infotitle;

        public ViewHolder(View view) {
            super(view);
            item_info = (CardView) view.findViewById(R.id.item_info);
            tv_infotype = (TextView) view.findViewById(R.id.tv_infotype);
            tv_infotime = (TextView) view.findViewById(R.id.tv_infotime);
            tv_infotitle = (TextView) view.findViewById(R.id.tv_infotitle);
        }
    }
}
