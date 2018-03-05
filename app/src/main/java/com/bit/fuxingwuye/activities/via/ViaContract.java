package com.bit.fuxingwuye.activities.via;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.ViaBean;

/**
 * Created by Dell on 2017/11/13.
 * Created time:2017/11/13 14:32
 */

public class ViaContract {

    public interface View extends BaseView{
        void addSuccess(String url);
    }

    public interface Presenter extends BasePresenter<View>{
        void addVia(ViaBean viaBean);
    }
}
