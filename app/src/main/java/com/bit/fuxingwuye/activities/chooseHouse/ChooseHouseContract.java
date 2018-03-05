package com.BIT.fuxingwuye.activities.chooseHouse;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.UserBean;

/**
 * Created by Dell on 2017/10/9.
 * Created time:2017/10/9 9:45
 */

public class ChooseHouseContract {

    public interface View extends BaseView{
        void showFloors(UserBean userBean);
    }

    public interface Presenter extends BasePresenter<View>{
        void findone(FindBean findBean);
    }
}
