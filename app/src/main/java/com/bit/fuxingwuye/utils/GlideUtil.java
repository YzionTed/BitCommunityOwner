package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bit.fuxingwuye.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by DELL60 on 2018/3/5.
 */

public class GlideUtil {
    public static void loadImage(final Context mContext, final String path, final ImageView mImageView) {
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
                .placeholder(R.mipmap.image_default_small)
                .error(R.mipmap.image_default_small)
                .into(mImageView);
    }
}
