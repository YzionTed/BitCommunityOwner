package com.bit.fuxingwuye.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.databinding.ActivityEanimationBinding;

public class EAnimationActivity extends Activity {

    private ActivityEanimationBinding mBinding;
    private AnimationDrawable frameAnim;
    private Handler handler = new Handler();
    Runnable mRunnable;
    private int[] mFrameRess;
    private int mLastFrameNo;
    private int mDuration;
    private long mBreakDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_eanimation);
        final int[] pFrameRess = {R.drawable.ele01,R.drawable.ele02,R.drawable.ele03,R.drawable.ele04,R.drawable.ele05,
                R.drawable.ele06,R.drawable.ele07,R.drawable.ele08,R.drawable.ele09,R.drawable.ele10,
                R.drawable.ele11,R.drawable.ele12,R.drawable.ele13,R.drawable.ele14,R.drawable.ele15,
                R.drawable.ele16,R.drawable.ele17,R.drawable.ele18};
        mFrameRess = pFrameRess;
        mLastFrameNo = pFrameRess.length-1;
        mBinding.ivAnimation.setBackgroundResource(pFrameRess[0]);
        playConstant(1);
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void playConstant(final int i) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.ivAnimation.setBackgroundResource(mFrameRess[i]);

                if (i != mLastFrameNo)
                    playConstant(i + 1);

            }
        }, i == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);
    }
}
