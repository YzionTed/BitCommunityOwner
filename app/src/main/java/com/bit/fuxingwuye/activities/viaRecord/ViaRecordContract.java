package com.bit.fuxingwuye.activities.viaRecord;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.ViaBean;

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
