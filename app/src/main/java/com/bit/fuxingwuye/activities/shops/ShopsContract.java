package com.BIT.fuxingwuye.activities.shops;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.MerchantBean;
import com.BIT.fuxingwuye.bean.RepairBean;

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
