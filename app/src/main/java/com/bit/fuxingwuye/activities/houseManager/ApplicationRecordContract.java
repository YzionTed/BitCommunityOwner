package com.bit.fuxingwuye.activities.houseManager;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.base.ProprietorBean;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RecordData;
import com.bit.fuxingwuye.bean.RoomList;

import java.util.List;
import java.util.Map;

/**
 * Created by 23 on 2018/2/28.
 */

public class ApplicationRecordContract {
    public interface View extends BaseView {
        void ShowRecord(ProprietorBean recordData);
    }
    public interface Presenter extends BasePresenter<ApplicationRecordContract.View> {
        void GetRecord(String str);
    }
}
