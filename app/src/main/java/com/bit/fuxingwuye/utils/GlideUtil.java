package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bit.fuxingwuye.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by DELL60 on 2018/3/5.
 */

public class GlideUtil {
    public static void loadImage(Context mContext, String path, ImageView mImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.mipmap.image_default_small)
                .error(R.mipmap.image_default_small)
                .circleCrop()
                .fallback(R.mipmap.image_default_small);
        Glide.with(mContext).load(new CacheGlideUrl(path))
                .apply(options)
                .into(mImageView);
    }
}
