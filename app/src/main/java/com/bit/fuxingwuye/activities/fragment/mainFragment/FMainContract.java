package com.BIT.fuxingwuye.activities.fragment.mainFragment;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.Notice;
import com.BIT.fuxingwuye.bean.NoticeListBean;
import com.BIT.fuxingwuye.bean.UserBean;

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
