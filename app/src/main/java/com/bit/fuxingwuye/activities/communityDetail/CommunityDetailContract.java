package com.BIT.fuxingwuye.activities.communityDetail;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.CommunityBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.ReplyBean;
import com.BIT.fuxingwuye.bean.ZanBean;

/**
 * Created by Dell on 2017/11/6.
 * Created time:2017/11/6 14:51
 */

public class CommunityDetailContract {

    public interface View extends BaseView{
        void showDetail(CommunityBean communityBean);
        void deleteSuccess();
        void refresh();
        void refreshZan(String s);
    }

    public interface Presenter extends BasePresenter<View>{
        void getCommunity(CommonBean commonBean);
        void delete(CommonBean commonBean);
        void saveReply(ReplyBean replyBean);
        void zan(ZanBean zanBean);
    }
}
