package com.BIT.fuxingwuye.activities.brake;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;

/**
 * Created by Dell on 2017/11/16.
 * Created time:2017/11/16 10:41
 */

public class BrakeContract {

    public interface View extends BaseView{
        void openSuccess();
    }

    public interface Presenter extends BasePresenter<View>{
        void open();
    }
}
