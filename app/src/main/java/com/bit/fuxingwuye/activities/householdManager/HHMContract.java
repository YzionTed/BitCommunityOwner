package com.BIT.fuxingwuye.activities.householdManager;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.HouseholdBean;

import java.util.List;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:29
 */

public class HHMContract {
    public interface View extends BaseView {
        void showUserAff(List<HouseholdBean> householdBeen);
        void deleteSuccess();
        void showNo();
    }
    public interface Presenter extends BasePresenter<View> {
        void getUserAff(CommonBean commonBean);
        void deleteUser(CommonBean commonBean);
    }
}
