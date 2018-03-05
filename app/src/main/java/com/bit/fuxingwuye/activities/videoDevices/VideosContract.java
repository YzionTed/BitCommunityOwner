package com.bit.fuxingwuye.activities.videoDevices;

import com.bit.fuxingwuye.activities.shops.ShopsContract;
import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.RepairBean;

import java.util.List;

/**
 * Created by Dell on 2017/10/30.
 * Created time:2017/10/30 14:52
 */

public class VideosContract {

    public interface View extends BaseView {
        void showVideos(List<RepairBean> datas);
    }
    public interface Presenter extends BasePresenter<VideosContract.View> {
        void getVideos(CommonBean commonBean);
    }
}
