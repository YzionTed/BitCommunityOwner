package com.bit.fuxingwuye.activities.aboutBit;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;

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
