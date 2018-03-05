package com.BIT.fuxingwuye.activities.parkPicker;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.PlotInfoBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 17:32
 */

public class ParkPickerContract {

    public interface View extends BaseView {
        void showParks(List<PlotInfoBean> plotInfoBeanList);
    }
    public interface Presenter extends BasePresenter<View> {
        void getParks(CommonBean commonBean);
    }

}
