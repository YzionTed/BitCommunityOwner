package com.bit.fuxingwuye.activities.addNew;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.HouseholdsBean;

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
