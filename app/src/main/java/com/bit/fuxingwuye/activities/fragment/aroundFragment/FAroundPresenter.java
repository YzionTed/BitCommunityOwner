package com.BIT.fuxingwuye.activities.fragment.aroundFragment;

import android.content.Context;

import com.BIT.fuxingwuye.base.BaseRxPresenter;


/**
 * Created by Dell on 2017/8/5.
 * Created time:2017/8/5 11:43
 */

public class FAroundPresenter extends BaseRxPresenter<FAroundContract.View> implements FAroundContract.Presenter {

    private Context context;

    public FAroundPresenter(Context context) {
        this.context = context;
    }
}
