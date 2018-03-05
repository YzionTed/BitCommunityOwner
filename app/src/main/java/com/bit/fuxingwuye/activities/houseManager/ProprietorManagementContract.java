package com.BIT.fuxingwuye.activities.houseManager;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.base.ProprietorBean;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.RoomList;

import java.util.List;
import java.util.Map;

/**
 * Created by 23 on 2018/2/28.
 */

public class ProprietorManagementContract {
    public interface View extends BaseView {
        void showProprietorData(ProprietorBean proprietorBean);
        void showRelieveSuccess();

    }
    public interface Presenter extends BasePresenter<ProprietorManagementContract.View> {
       void GetProprietorData(String roomid);
       void Relieve(String id);
    }
}
