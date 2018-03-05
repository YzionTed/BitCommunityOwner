package com.BIT.fuxingwuye.activities.feedback;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FeedbackBean;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:36
 */

public class FeedbackContract {
    public interface View extends BaseView {
        void feedbackSuccess();
    }
    public interface Presenter extends BasePresenter<View> {
        void feedback(FeedbackBean commonBean);
    }
}
