package com.BIT.fuxingwuye.activities.addNew;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.HouseholdsBean;

/**
 * Created by Dell on 2017/7/13.
 */

public class AddNewContract {

    public interface View extends BaseView {
        void addSuccess();
    }
    public interface Presenter extends BasePresenter<View> {
        void addNew(HouseholdsBean householdsBean);
    }
}
