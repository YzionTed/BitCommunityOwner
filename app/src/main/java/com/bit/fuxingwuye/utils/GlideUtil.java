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
                .fallback(R.mipmap.image_default_small);
        Glide.with(mContext).load(new CacheGlideUrl(path)).listener(new RequestListener<CacheGlideUrl, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, CacheGlideUrl model, Target<GlideDrawable> target, boolean isFirstResource) {
                new Runnable(){
                    @Override
                    public void run() {
                        Glide.with(mContext).load(path).into(mImageView);
                    }
                }.run();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, CacheGlideUrl model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        })
                .apply(options)
                .into(mImageView);
    }
}
