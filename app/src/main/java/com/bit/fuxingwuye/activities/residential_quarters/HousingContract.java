package com.bit.fuxingwuye.activities.residential_quarters;

import com.bit.fuxingwuye.activities.roomPicker.RoomPickerContract;
import com.bit.fuxingwuye.base.BasePresenter;
import com.bit.fuxingwuye.base.BaseView;
import com.bit.fuxingwuye.bean.Building;
import com.bit.fuxingwuye.bean.Community;
import com.bit.fuxingwuye.bean.Room;

/**
 * Created by 23 on 2018/2/26.
 */

public class HousingContract {
    public interface View extends BaseView {
         void showHousing(Community community);
        void showError();

    }
    public interface Presenter extends BasePresenter<View> {
        void getHousing(String userId);
    }
}
