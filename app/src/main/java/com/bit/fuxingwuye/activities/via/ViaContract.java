package com.BIT.fuxingwuye.activities.via;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.ViaBean;

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
