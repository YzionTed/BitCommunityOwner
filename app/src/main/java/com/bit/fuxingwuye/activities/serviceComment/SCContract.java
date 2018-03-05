package com.bit.fuxingwuye.activities.serviceComment;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.EvaluationBean;

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
