package com.BIT.fuxingwuye.activities.register;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.RegisterBean;
import com.BIT.fuxingwuye.bean.TokenBean;

/**
 * Created by Dell on 2017/7/4.
 */

public class RegisterContract {

    public interface View extends BaseView {
        void registerSuccess(TokenBean tokenBean);
        void runTimerTask();   //验证码开启计时器
        void unableTimerTask();   //销毁计时器
    }
    public interface Presenter extends BasePresenter<View> {
        void getCode(CodeBean codeBean);
        void register(RegisterBean registerBean);
    }
}
