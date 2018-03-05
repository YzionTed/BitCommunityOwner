package com.bit.fuxingwuye.activities.parkPicker;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PlotInfoBean;

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
