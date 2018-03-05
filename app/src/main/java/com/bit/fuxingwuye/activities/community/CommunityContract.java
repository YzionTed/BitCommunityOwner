package com.bit.fuxingwuye.activities.community;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.InformationBean;
import com.bit.fuxingwuye.bean.RepairBean;
import com.bit.fuxingwuye.bean.ZanBean;

import java.util.List;

/**
 * Created by Dell on 2017/9/30.
 * Created time:2017/9/30 14:54
 */

public class CommunityContract {

    public interface View extends BaseView{
        void showEvents(InformationBean datas, int type);
        void refresh(int pos,InformationBean.Info info);
        void showReply(List<InformationBean.Info> infos);
    }

    public interface Presenter extends BasePresenter<View>{
        void getEvents(CommonBean commonBean, int type);
        void getReplies(CommonBean commonBean);
        void zan(ZanBean zanBean,int postion);
    }
}
