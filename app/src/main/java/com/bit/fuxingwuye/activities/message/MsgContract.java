package com.BIT.fuxingwuye.activities.message;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.NoticeBean;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 17:01
 */

public class MsgContract {

    public interface View extends BaseView {
        void showMsg(NoticeBean notice);
    }

    public interface Presenter extends BasePresenter<View> {
        void getNotice(CommonBean commonBean);
    }
}
