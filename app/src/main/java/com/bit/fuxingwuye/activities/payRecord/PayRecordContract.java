package com.bit.fuxingwuye.activities.payRecord;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayListBean;

import java.util.List;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 13:27
 */

public class PayRecordContract {

    public interface View extends BaseView {
        void showPayList(List<PayListBean> payListBeanList, int type);
    }
    public interface Presenter extends BasePresenter<View> {
        void showPayList(CommonBean commonBean, int type);
    }
}
