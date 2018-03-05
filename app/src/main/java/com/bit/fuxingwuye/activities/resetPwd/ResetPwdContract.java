package com.bit.fuxingwuye.activities.resetPwd;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CodeBean;
import com.bit.fuxingwuye.bean.ResetPwdBean;

/**
 * Created by Dell on 2017/7/12.
 */

public class ResetPwdContract {

    public interface View extends BaseView {
        void resetSuccess(String s);
        void runTimerTask();   //验证码开启计时器
        void unableTimerTask();   //销毁计时器
    }
    public interface Presenter extends BasePresenter<View> {
        void getCode(CodeBean codeBean);
        void resetPwd(ResetPwdBean resetPwdBean);
    }
}
