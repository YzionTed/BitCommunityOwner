package com.bit.fuxingwuye.activities.addFaultRepair;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.FindBean;
import com.bit.fuxingwuye.bean.RepairBean;
import com.bit.fuxingwuye.bean.UserBean;

import java.io.File;
import java.util.List;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 14:05
 */

public class FaultRepairContract {

    public interface View extends BaseView{
        void addSuccess();
        void upload(List<String> urls);
        void showFloors(UserBean userBean);
    }

    public interface Presenter extends BasePresenter<View>{
        void addFault(RepairBean repairBean);
        void findone(FindBean findBean);
        void upload(List<File> files);
    }
}
