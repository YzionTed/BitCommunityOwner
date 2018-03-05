package com.bit.fuxingwuye.activities.fragment.mainFragment;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.Notice;
import com.bit.fuxingwuye.bean.NoticeListBean;
import com.bit.fuxingwuye.bean.UserBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/5.
 * Created time:2017/8/5 9:16
 */

public class FMainContract {

    public interface View extends BaseView {
        void showNotices(NoticeListBean notices, int type);
        void findOne(UserBean userBean);
    }

    public interface Presenter extends BasePresenter<View> {
        void getNotices(String communityId,int page, int type);
        void findOne(FindBean findBean);
    }
}
