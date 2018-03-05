package com.BIT.fuxingwuye.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.databinding.ActivityGanimationBinding;

public class GAnimationActivity extends Activity {

    private ActivityGanimationBinding mBinding;
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
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_ganimation);

        final int[] pFrameRess = {R.drawable.gate01,R.drawable.gate02,R.drawable.gate03,R.drawable.gate04,R.drawable.gate05,
                R.drawable.gate06,R.drawable.gate07,R.drawable.gate08,R.drawable.gate09,R.drawable.gate10,
                R.drawable.gate11,R.drawable.gate12,R.drawable.gate13,R.drawable.gate14,R.drawable.gate15,
                R.drawable.gate16,R.drawable.gate17,R.drawable.gate18};
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
