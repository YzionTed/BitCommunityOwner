package com.bit.fuxingwuye.activities.fragment.mainFragment;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.Notice;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.bean.UserBean;
import com.bit.fuxingwuye.bean.request.NoticeBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/5.
 * Created time:2017/8/5 9:16
 */

public class FMainContract {

    public interface View extends BaseView {

        void findOne(UserBean userBean);
    }

    public interface Presenter extends BasePresenter<View> {

        void findOne(FindBean findBean);
    }
}
