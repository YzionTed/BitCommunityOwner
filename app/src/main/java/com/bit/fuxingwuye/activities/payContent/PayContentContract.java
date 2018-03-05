package com.bit.fuxingwuye.activities.payContent;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;

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
