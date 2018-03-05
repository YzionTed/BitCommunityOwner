package com.BIT.fuxingwuye.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * SmartCommunity-com.BIT.fuxingwuye.viewholder
 * 作者： YanwuTang
 * 时间： 2017/7/14.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getmBinding() {
        return mBinding;
    }

    public void setmBinding(ViewDataBinding mBinding) {
        this.mBinding = mBinding;
    }
}
