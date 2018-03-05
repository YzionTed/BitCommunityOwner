package com.bit.fuxingwuye.activities.houseManager;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.base.ProprietorBean;

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
