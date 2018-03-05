package com.BIT.fuxingwuye.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.BIT.fuxingwuye.viewholder.BaseViewHolder;

import java.util.List;

/**
 * SmartCommunity-com.BIT.fuxingwuye.adapter
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 *
 * d带 binding 的
 * 使用简单例子
 * 例如： BaseAdapter<ProvinceBean> adapter = new BaseAdapter<>(R.layout.item_base_string, BR.province);
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> datas;
    private int layoutId;
    private int brId;

    public void setData(List<T> datas){
        this.datas = datas;
    }

    public BaseAdapter(int layoutId, int brId){
        this.layoutId = layoutId;
        this.brId = brId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(binding.getRoot());
        viewHolder.setmBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

        holder.getmBinding().setVariable(brId, datas.get(position));
        holder.getmBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

}
