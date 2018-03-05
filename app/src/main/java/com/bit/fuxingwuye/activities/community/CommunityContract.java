package com.BIT.fuxingwuye.activities.community;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.bean.ZanBean;

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
