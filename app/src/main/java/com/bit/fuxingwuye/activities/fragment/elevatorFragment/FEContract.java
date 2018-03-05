package com.BIT.fuxingwuye.activities.fragment.elevatorFragment;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.ElevatorBean;

/**
 * Created by Dell on 2017/9/1.
 * Created time:2017/9/1 10:54
 */

public class FEContract {

    public interface View extends BaseView{
        void showElevator(ElevatorBean elevatorBean);
    }

    public interface Presenter extends BasePresenter<View>{
        void getElevator(CommonBean commonBean);
    }
}
