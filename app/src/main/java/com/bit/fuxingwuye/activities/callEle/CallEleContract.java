package com.BIT.fuxingwuye.activities.callEle;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.ElevatorBean;

/**
 * Created by Dell on 2017/8/14.
 * Created time:2017/8/14 11:44
 */

public class CallEleContract {

    public interface View extends BaseView{
        void showElevator(ElevatorBean elevatorBean);
    }

    public interface Presenter extends BasePresenter<View>{
        void getElevator(CommonBean commonBean);
    }
}
