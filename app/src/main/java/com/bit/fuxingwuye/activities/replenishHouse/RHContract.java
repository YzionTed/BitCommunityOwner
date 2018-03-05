package com.bit.fuxingwuye.activities.replenishHouse;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.HouseholdsBean;
import com.bit.fuxingwuye.bean.ReplenishHouseBean;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 14:27
 */

public class RHContract {

    public interface View extends BaseView {
        void commitSuccess(String str);
        void commithhSuccess(String str);
        void addHousehold();
    }
    public interface Presenter extends BasePresenter<View> {
        void commitData(ReplenishHouseBean replenishHouseBean);
        void commitHousehold(HouseholdsBean householdBeans);
        void addHousehold();
    }
}
