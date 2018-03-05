package com.BIT.fuxingwuye.activities.addReply;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.ReplyBean;

import java.io.File;
import java.util.List;

/**
 * Created by Dell on 2017/11/3.
 * Created time:2017/11/3 13:36
 */

public class AddReplyContract {

    public interface View extends BaseView{
        void addSuccess();
        void upload(List<String> urls);
    }

    public interface Presenter extends BasePresenter<View>{
        void upload(List<File> files);
        void addFault(ReplyBean replyBean);
    }
}
