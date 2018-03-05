package com.BIT.fuxingwuye.activities.replenishHouse;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.bean.ReplenishHouseBean;

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
