package com.bit.fuxingwuye.activities.householdManager;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.HouseholdBean;

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
