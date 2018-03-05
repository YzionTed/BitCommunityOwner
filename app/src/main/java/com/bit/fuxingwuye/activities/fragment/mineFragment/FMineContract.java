package com.bit.fuxingwuye.activities.fragment.mineFragment;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.OutLogin;
import com.bit.fuxingwuye.bean.UserBean;

/**
 * Created by Dell on 2017/8/9.
 * Created time:2017/8/9 14:33
 */

public class FMineContract {

    public interface View extends BaseView{
        void findPersonal(UserBean userBean);
        void outloginSucess(OutLogin str);
    }

    public interface Presenter extends BasePresenter<View>{
        void findPersonal(FindBean findBean);
        void outlogin();
    }
}
