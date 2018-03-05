package com.bit.fuxingwuye.activities.carManager;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.ParkBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/14.
 */

public class CarManagerContract {

    public interface View extends BaseView {
        void showParks(List<ParkBean> parkBeen);
    }
    public interface Presenter extends BasePresenter<View> {
        void getParks(CommonBean commonBean);
        void deletePark(CommonBean commonBean);
    }
}
