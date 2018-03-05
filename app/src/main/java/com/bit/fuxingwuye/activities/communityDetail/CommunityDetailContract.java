package com.bit.fuxingwuye.activities.communityDetail;

import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.CommunityBean;
import com.bit.fuxingwuye.bean.InformationBean;
import com.bit.fuxingwuye.bean.ReplyBean;
import com.bit.fuxingwuye.bean.ZanBean;

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
