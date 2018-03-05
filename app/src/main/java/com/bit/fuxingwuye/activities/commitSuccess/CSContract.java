package com.BIT.fuxingwuye.activities.commitSuccess;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:14
 */

public class CSContract {

    public interface View extends BaseView {
        void continue_replenish();
        void complete();
    }
    public interface Presenter extends BasePresenter<View> {
        void continue_replenish();
        void complete();
    }
}
