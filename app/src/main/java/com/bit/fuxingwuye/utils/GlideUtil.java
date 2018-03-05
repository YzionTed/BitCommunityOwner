package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bit.fuxingwuye.R;
import com.bumptech.glide.Glide;

/**
 * Created by DELL60 on 2018/3/5.
 */

public class GlideUtil {
    public static void loadImage(Context mContext, String path, ImageView mImageView){
        Glide.with(mContext).load(new CacheGlideUrl(path))
                .placeholder(R.mipmap.image_default)
                .error(R.mipmap.image_default)
                .into(mImageView);
    }
}
