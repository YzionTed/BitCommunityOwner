package com.bit.fuxingwuye.activities.houseManager;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.FloorBean;
import com.bit.fuxingwuye.bean.RoomList;

import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 15:28
 */

public class HMContract {
    public interface View extends BaseView {
        void showFloors(List<RoomList> floorBeen);
        void deleteSuccess();
        void onError();
    }
    public interface Presenter extends BasePresenter<View> {
        void getFloors(Map<String,String> commonBean);
        void deleteFloor(String commonBean);
    }
}
