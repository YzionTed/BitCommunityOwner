package com.bit.fuxingwuye.activities.replenishData;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.HouseBean;
import com.bit.fuxingwuye.bean.ReplenishBean;

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
