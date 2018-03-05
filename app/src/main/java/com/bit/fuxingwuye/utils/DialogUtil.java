package com.BIT.fuxingwuye.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.BIT.fuxingwuye.R;


/**
 * Created by 92594 on 2018/1/26.
 */

public class DialogUtil {
    private static CustomDialog customBottomDialog;



    /**
     * 开关frame动画dialog
     *
     * @param context
     * @param animDraw
     */
    public static CustomDialog showFrameAnimDialog(Context context, int animDraw) {
        customBottomDialog = new CustomDialog(context);
        customBottomDialog.show();
        customBottomDialog.addView(R.layout.dialog_frame_anim);
        ImageView ivAnim = (ImageView)customBottomDialog.findViewById(R.id.iv_anim);
        ivAnim.setImageResource(animDraw);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivAnim.getDrawable();
        animationDrawable.start();


        return customBottomDialog;
    }

    public static void dissmiss() {
        if (customBottomDialog != null && customBottomDialog.isShowing()) {
            customBottomDialog.dismiss();
        }
    }
}
