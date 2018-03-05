package com.BIT.fuxingwuye.activities.personalEdit;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.EditUserBean;
import com.BIT.fuxingwuye.bean.ResetPwdBean;
import com.BIT.fuxingwuye.bean.UserBean;

import java.io.File;
import java.util.List;

/**
 * Created by Dell on 2017/11/9.
 * Created time:2017/11/9 14:10
 */

public class PersonalEditContract {

    public interface View extends BaseView{
        void editSuccess(UserBean userBean);
        void runTimerTask();   //验证码开启计时器
        void unableTimerTask();   //销毁计时器
        void upload(List<String> urls);
    }

    public interface Presenter extends BasePresenter<View>{
        void editUser(EditUserBean editUserBean);
        void getCode(CodeBean codeBean);
        void upload(File file);
    }
}
