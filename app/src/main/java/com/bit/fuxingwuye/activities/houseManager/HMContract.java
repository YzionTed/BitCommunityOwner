package com.BIT.fuxingwuye.activities.houseManager;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.FloorBean;
import com.BIT.fuxingwuye.bean.RoomList;

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
    }
    public interface Presenter extends BasePresenter<View> {
        void getFloors(Map<String,String> commonBean);
        void deleteFloor(String commonBean);
    }
}
