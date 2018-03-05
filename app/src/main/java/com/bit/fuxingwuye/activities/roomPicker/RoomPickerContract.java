package com.BIT.fuxingwuye.activities.roomPicker;


import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.Building;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.Community;
import com.BIT.fuxingwuye.bean.PlotInfoBean;
import com.BIT.fuxingwuye.bean.Room;

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
