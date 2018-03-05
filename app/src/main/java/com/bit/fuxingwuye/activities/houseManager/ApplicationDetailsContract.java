package com.BIT.fuxingwuye.activities.houseManager;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.base.ProprietorBean;

/**
 * Created by 23 on 2018/3/1.
 */

public class ApplicationDetailsContract {
    public interface View extends BaseView {
        void ShowApplication();
        void ShowDismissApplication();
    }
    public interface Presenter extends BasePresenter<ApplicationDetailsContract.View> {
        void GetApplication(ProprietorBean.RecordsBean bean);
        void DismissApplication(ProprietorBean.RecordsBean bean);
    }
}
