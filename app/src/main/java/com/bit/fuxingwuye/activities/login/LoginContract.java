package com.BIT.fuxingwuye.activities.login;


import android.content.Context;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.LoginBean;
import com.BIT.fuxingwuye.bean.TokenBean;
import com.BIT.fuxingwuye.bean.VersionBean;

/**
 * Created by Dell on 2017/7/3.
 */

public class LoginContract {

    public interface View extends BaseView {
        void loginSuccess(TokenBean tokenBean);
        void hasUpgrade(VersionBean versionBean);
        void runTimerTask();   //验证码开启计时器
        void unableTimerTask();   //销毁计时器
        void initProgressDialog();
        void showProgressDialog(int i);
        void gotoRegister();
        void gotoReset();
        void emchat();
    }
    public interface Presenter extends BasePresenter<View> {
        void getCode(CodeBean codeBean);
        void login(LoginBean loginBean, Context ctx);
        void checkUpgrade(CommonBean commonBean);    //检查更新
        void downLoad(Context ctx, String url);
        void gotoRegister();
        void gotoReset();
        void existEmchat(LoginBean loginBean);
    }
}
