package com.BIT.fuxingwuye.activities.repairDetail;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.EvaluationBean;
import com.BIT.fuxingwuye.bean.ImagePathBean;
import com.BIT.fuxingwuye.bean.RepairBean;

import java.util.List;

/**
 * Created by Dell on 2017/8/2.
 * Created time:2017/8/2 13:44
 */

public class RepairDetailContract {

    public interface View extends BaseView{
        void showRepair(RepairBean repairBean);
        void deleteSuccess();
        void updateSuccess();
        void delete();
        void update();
        void comment();
        void showComment(EvaluationBean evaluationBean);
        void showImage(List<ImagePathBean> path);
    }
    public interface Presenter extends BasePresenter<View>{
        void getRepair(CommonBean commonBean);
        void deleteRepair(CommonBean commonBean);
        void updateRepair(RepairBean repairBean);
        void delete();
        void update();
        void comment();
        void getComment(EvaluationBean evaluationBean);
        void getImages(RepairBean.ImageBean imageBean);
    }
}
