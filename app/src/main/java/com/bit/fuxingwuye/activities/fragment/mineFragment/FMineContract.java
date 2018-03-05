package com.BIT.fuxingwuye.activities.fragment.mineFragment;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.OutLogin;
import com.BIT.fuxingwuye.bean.UserBean;

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
