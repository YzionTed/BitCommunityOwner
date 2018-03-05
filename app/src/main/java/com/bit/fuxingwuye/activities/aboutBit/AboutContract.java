package com.BIT.fuxingwuye.activities.aboutBit;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;

/**
 * Created by Dell on 2017/7/25.
 * Created time:2017/7/25 10:07
 */

public class AboutContract {
    public interface View extends BaseView {
        void showQr(String path);
    }
    public interface Presenter extends BasePresenter<View> {
        void getStores();
    }
}
