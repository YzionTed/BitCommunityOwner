package com.BIT.fuxingwuye.activities.payList;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.PayListBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 8:54
 */

public class PayListContract {

    public interface View extends BaseView{
        void showPayList(List<PayListBean> payListBeanList);
    }
    public interface Presenter extends BasePresenter<View>{
        void showPayList(CommonBean commonBean);
    }
}
