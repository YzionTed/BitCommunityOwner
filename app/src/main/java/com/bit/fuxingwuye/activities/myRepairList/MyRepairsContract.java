package com.bit.fuxingwuye.activities.myRepairList;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RepairBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 17:09
 */

public class MyRepairsContract {

    public interface View extends BaseView{
        void showRepairs(List<RepairBean> datas,int type);
    }
    public interface Presenter extends BasePresenter<View>{
        void getRepairs(CommonBean commonBean,int type);
    }
}
