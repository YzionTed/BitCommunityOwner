package com.BIT.fuxingwuye.activities.viaRecord;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.ViaBean;

import java.util.List;

/**
 * Created by Dell on 2017/11/15.
 * Created time:2017/11/15 13:30
 */

public class ViaRecordContract {

    public interface View extends BaseView{
        void showList(List<ViaBean> viaBeanList,int type);
    }

    public interface Presenter extends BasePresenter<View>{
        void getVias(CommonBean commonBean,int type);
    }
}
