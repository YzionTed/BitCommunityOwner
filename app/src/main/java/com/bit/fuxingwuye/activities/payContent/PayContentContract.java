package com.BIT.fuxingwuye.activities.payContent;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;

/**
 * Created by Dell on 2017/8/16.
 * Created time:2017/8/16 10:18
 */

public class PayContentContract {

    public interface View extends BaseView{

    }

    public interface Presenter extends BasePresenter<View>{
        void getPayContent(CommonBean commonBean);
    }
}
