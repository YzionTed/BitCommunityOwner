package com.BIT.fuxingwuye.activities.replenishCar;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CarBean;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 14:26
 */

public class RCContract {

    public interface View extends BaseView {
        void commitSuccess();
    }
    public interface Presenter extends BasePresenter<View> {
        void commitData(CarBean CarBean);
    }
}
