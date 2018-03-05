package com.bit.fuxingwuye.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.bean.CommunityBean;
import com.bit.fuxingwuye.bean.InformationBean;
import com.bit.fuxingwuye.bean.MerchantBean;
import com.bit.fuxingwuye.http.HttpProvider;
import com.bit.fuxingwuye.utils.ImageLoaderUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 9:42
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> implements View.OnClickListener{

    private List<CommunityBean.InfoReply> datas;
    private OnItemClickListener mOnItemClickListener = null;

    public ReplyAdapter(List<CommunityBean.InfoReply> datas) {
        this.datas = datas;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+datas.get(position).getHeadImg(),holder.iv_avatar);

        holder.tv_firstname.setText(datas.get(position).getUserName());
        if (!TextUtils.isEmpty(datas.get(position).getSendee())){
            holder.tv_action.setVisibility(View.VISIBLE);
            holder.tv_secondname.setVisibility(View.VISIBLE);
            holder.tv_secondname.setText(datas.get(position).getSendee());
        }else{
            holder.tv_action.setVisibility(View.GONE);
            holder.tv_secondname.setVisibility(View.GONE);
        }
        Date date = new Date(datas.get(position).getReplyTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd HH:mm");
        holder.tv_time.setText(sdf.format(date));
        holder.tv_reply.setText(datas.get(position).getContent());
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

        TextView tv_firstname,tv_action,tv_secondname,tv_time,tv_reply;
        ImageView iv_avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_firstname = (TextView)itemView.findViewById(R.id.tv_firstname);
            tv_action = (TextView)itemView.findViewById(R.id.tv_action);
            tv_secondname = (TextView)itemView.findViewById(R.id.tv_secondname);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            tv_reply = (TextView)itemView.findViewById(R.id.tv_reply);
            iv_avatar = (ImageView)itemView.findViewById(R.id.iv_avatar);
        }
    }
}
