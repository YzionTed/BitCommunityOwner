package com.bit.fuxingwuye.activities.roomPicker;


import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.Building;
import com.bit.fuxingwuye.bean.CommonBean;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.PlotInfoBean;
import com.bit.fuxingwuye.bean.Room;

import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 2017/7/27.
 * Created time:2017/7/27 17:33
 */

public class RoomPickerContract {

    public interface View extends BaseView {

        void showcommunity(Community community);
        void showbuilding(Building building);
        void showroom(Room room);
    }
    public interface Presenter extends BasePresenter<View> {

        void getcommunity(String userid);
        void getbuilding(String map);
        void getroom(String buildingId);
    }

}
