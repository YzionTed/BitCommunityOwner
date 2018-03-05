package com.bit.fuxingwuye.activities.chooseHouse;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.UserBean;

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
