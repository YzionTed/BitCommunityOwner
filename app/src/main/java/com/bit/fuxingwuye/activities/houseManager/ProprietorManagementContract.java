package com.bit.fuxingwuye.activities.houseManager;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RoomList;

import java.util.List;
import java.util.Map;

/**
 * Created by 23 on 2018/2/28.
 */

public class ProprietorManagementContract {
    public interface View extends BaseView {
        void showProprietorData(ProprietorBean proprietorBean);
        void showRelieveSuccess();
        void onNetEroor();

    }
    public interface Presenter extends BasePresenter<ProprietorManagementContract.View> {
       void GetProprietorData(String roomid);
       void Relieve(String id);
    }
}
