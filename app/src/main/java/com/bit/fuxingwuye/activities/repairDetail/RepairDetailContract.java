package com.bit.fuxingwuye.activities.repairDetail;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.EvaluationBean;
import com.bit.fuxingwuye.bean.ImagePathBean;
import com.bit.fuxingwuye.bean.RepairBean;

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
