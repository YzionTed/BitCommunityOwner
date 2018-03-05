package com.bit.fuxingwuye.activities.shops;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.MerchantBean;
import com.bit.fuxingwuye.bean.RepairBean;

import java.util.List;

/**
 * Created by Dell on 2017/9/30.
 * Created time:2017/9/30 10:19
 */

public class ShopsContract {

    public interface View extends BaseView {
        void showShops(List<MerchantBean> datas, int type);
        void showSlide(List<String> slides);
    }
    public interface Presenter extends BasePresenter<View> {
        void getShops(MerchantBean merchantBean, int type);
        void getSlide(CommonBean commonBean);
    }
}
