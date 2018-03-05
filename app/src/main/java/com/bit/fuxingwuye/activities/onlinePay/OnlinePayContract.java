package com.bit.fuxingwuye.activities.onlinePay;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.PayReqBean;

/**
 * Created by Dell on 2017/9/1.
 * Created time:2017/9/1 13:27
 */

public class OnlinePayContract {

    public interface View extends BaseView{
        void wxpay(PayReqBean.cont req);
        void aliPay(PayReqBean.cont req);
        void wxPaySuccess();
        void aliPaySuccess();
    }

    public interface Presenter extends BasePresenter<View>{
        void wechat(PayReqBean payReqBean);
        void alipay(PayReqBean payReqBean);
        void unionpay();
        void wechatQuery(CommonBean commonBean);
        void aliQuery(CommonBean commonBean);
    }
}
