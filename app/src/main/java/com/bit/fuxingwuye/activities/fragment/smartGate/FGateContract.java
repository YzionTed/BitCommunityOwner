package com.BIT.fuxingwuye.activities.fragment.smartGate;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FacilityBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/11.
 * Created time:2017/8/11 8:41
 */

public class FGateContract {

    public interface View extends BaseView{
        void showDoors(List<FacilityBean> facilityBeanList);
    }

    public interface Presenter extends BasePresenter<View>{
        void getDoors(CommonBean commonBean);
    }
}
