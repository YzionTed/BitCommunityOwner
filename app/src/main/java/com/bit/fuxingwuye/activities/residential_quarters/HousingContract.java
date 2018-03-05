package com.BIT.fuxingwuye.activities.residential_quarters;

import com.BIT.fuxingwuye.activities.roomPicker.RoomPickerContract;
import com.BIT.fuxingwuye.base.BasePresenter;
import com.BIT.fuxingwuye.base.BaseView;
import com.BIT.fuxingwuye.bean.Building;
import com.BIT.fuxingwuye.bean.Community;
import com.BIT.fuxingwuye.bean.Room;

/**
 * Created by 23 on 2018/2/26.
 */

public class HousingContract {
    public interface View extends BaseView {
         void showHousing(Community community);

    }
    public interface Presenter extends BasePresenter<HousingContract.View> {
        void getHousing(String userId);
    }
}
