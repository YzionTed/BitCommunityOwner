package com.BIT.fuxingwuye.activities.serviceComment;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.EvaluationBean;

/**
 * Created by Dell on 2017/8/14.
 * Created time:2017/8/14 10:40
 */

public class SCContract {
    public interface View extends BaseView{
        void conmmentSuccess();
    }
    public interface Presenter extends BasePresenter<View>{
        void comment(EvaluationBean evaluationBean);
    }
}
