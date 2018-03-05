package com.bit.fuxingwuye.activities.replenishCar;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CarBean;

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
