package com.bit.fuxingwuye.activities.feedback;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FeedbackBean;

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
