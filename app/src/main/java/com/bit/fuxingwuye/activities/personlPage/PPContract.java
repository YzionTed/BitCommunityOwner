package com.BIT.fuxingwuye.activities.personlPage;

import android.content.Context;

import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.bean.VersionBean;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 17:56
 */

public class PPContract {

    public interface View extends BaseView {
        void findPersonal(UserBean userBean);
        void editPersonalPage();
        void houseManager();     //住房管理
        void householdManager();    //住户管理
        void carManager();    //车位管理
        void aboutBit();
        void feedback();     //建议反馈
        void hasUpgrade(VersionBean versionBean);
        void initProgressDialog();
        void showProgressDialog(int i);
    }
    public interface Presenter extends BasePresenter<View> {
        void findPersonal(FindBean findBean);
        void editPersonalPage();
        void houseManager();     //住房管理
        void carManager();    //车位管理
        void aboutBit();     //关于府兴
        void feedback();     //建议反馈
        void checkUpgrade(CommonBean commonBean);    //检查更新
        void downLoad(Context ctx, String url);
    }
}
