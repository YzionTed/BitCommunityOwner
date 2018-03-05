package com.BIT.fuxingwuye.activities.replenishData;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.HouseBean;
import com.BIT.fuxingwuye.bean.ReplenishBean;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 14:26
 */

public class RDContract {

    public interface View extends BaseView {
        void commitSuccess(HouseBean str);
        void getMemberSuccess(HouseBean str);
    }

    public interface Presenter extends BasePresenter<View> {
        void commitData(ReplenishBean replenishBean);
        void commitMember(ReplenishBean replenishBean);
    }
}
