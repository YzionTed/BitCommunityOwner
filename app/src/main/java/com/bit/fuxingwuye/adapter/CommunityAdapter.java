package com.BIT.fuxingwuye.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.bean.ImagePathBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.http.HttpProvider;
import com.BIT.fuxingwuye.utils.ImageLoaderUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 9:42
 */

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<InformationBean.Info> datas;
    private OnItemClickListener mOnItemClickListener = null;
    private ReplyCallBack replyCallBackback;
    private ZanCallBack zanCallBack;


    public CommunityAdapter(Context context, List<InformationBean.Info> datas,ReplyCallBack replyCallBack,ZanCallBack zanCallBack) {
        this.context = context;
        this.datas = datas;
        this.replyCallBackback = replyCallBack;
        this.zanCallBack = zanCallBack;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface ReplyCallBack{
        void onReplyclick(View view,String position);
    }
    public interface ZanCallBack{
        void onZanClick(View view,String position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+datas.get(position).getHeadImg(),holder.iv_avatar);
        holder.tv_name.setText(datas.get(position).getUserName());
        holder.tv_content.setText(datas.get(position).getContent());
        holder.tv_reply_num.setText(datas.get(position).getReplyNumber());
        holder.tv_zan_num.setText(datas.get(position).getZanNumber());
        Date date = new Date(datas.get(position).getPasteTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd HH:mm");
        holder.tv_time.setText(sdf.format(date));
        if (null!=datas.get(position).getImgList()){
            holder.ll_images.setVisibility(View.VISIBLE);
            List<ImagePathBean> images = datas.get(position).getImgList();
            StringBuffer sb = new StringBuffer().append(HttpProvider.getHttpIpAdds());
            if (images.size()==1){
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),holder.iv_content1);
                holder.iv_content2.setVisibility(View.GONE);
                holder.iv_content3.setVisibility(View.GONE);
            }else if (images.size()==2){
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),holder.iv_content1);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(1).getImgUrl(),holder.iv_content2);
                holder.iv_content2.setVisibility(View.VISIBLE);
                holder.iv_content3.setVisibility(View.GONE);
            }else {
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(0).getImgUrl(),holder.iv_content1);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(1).getImgUrl(),holder.iv_content2);
                ImageLoaderUtil.setImageWithCache(HttpProvider.getHttpIpAdds()+"/"+images.get(2).getImgUrl(),holder.iv_content3);
                holder.iv_content2.setVisibility(View.VISIBLE);
                holder.iv_content3.setVisibility(View.VISIBLE);
            }
        }else {
            holder.ll_images.setVisibility(View.GONE);
        }

        holder.iv_reply.setOnClickListener(this);
        holder.iv_zan.setOnClickListener(this);
        holder.iv_reply.setTag(datas.get(position).getId());
        holder.iv_zan.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return null==datas?0:datas.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_reply:
                if (replyCallBackback!=null){
                    replyCallBackback.onReplyclick(v,(String)v.getTag());
                }
                break;
            case R.id.iv_zan:
                if (zanCallBack!=null){
                    zanCallBack.onZanClick(v,v.getTag().toString());
                }
                break;
            default:
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,(int)v.getTag());
                }
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_content,tv_time,tv_reply_num,tv_zan_num;
        ImageView iv_avatar,iv_content1,iv_content2,iv_content3;
        LinearLayout iv_reply,iv_zan;
        LinearLayout ll_images;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_content = (TextView)itemView.findViewById(R.id.tv_content);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            tv_reply_num = (TextView)itemView.findViewById(R.id.tv_reply_num);
            tv_zan_num = (TextView)itemView.findViewById(R.id.tv_zan_num);
            iv_avatar = (ImageView)itemView.findViewById(R.id.iv_avatar);
            iv_content1 = (ImageView)itemView.findViewById(R.id.iv_content1);
            iv_content2 = (ImageView)itemView.findViewById(R.id.iv_content2);
            iv_content3 = (ImageView)itemView.findViewById(R.id.iv_content3);
            iv_reply = (LinearLayout)itemView.findViewById(R.id.iv_reply);
            iv_zan = (LinearLayout)itemView.findViewById(R.id.iv_zan);
            ll_images = (LinearLayout)itemView.findViewById(R.id.ll_images);
        }
    }
}
