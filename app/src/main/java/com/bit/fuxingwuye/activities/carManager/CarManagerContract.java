package com.BIT.fuxingwuye.activities.carManager;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.ParkBean;

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
