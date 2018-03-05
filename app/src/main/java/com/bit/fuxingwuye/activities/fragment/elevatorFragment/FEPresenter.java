package com.BIT.fuxingwuye.activities.fragment.elevatorFragment;

import com.BIT.fuxingwuye.base.BaseRxPresenter;
import com.BIT.fuxingwuye.bean.CommonBean;

import javax.inject.Inject;

/**
 * Created by Dell on 2017/9/1.
 * Created time:2017/9/1 10:55
 */

public class FEPresenter extends BaseRxPresenter<FEContract.View> implements FEContract.Presenter {

    private ElevatorFragment elevatorFragment;

    @Inject
    public FEPresenter(ElevatorFragment elevatorFragment) {
        this.elevatorFragment = elevatorFragment;
    }

    @Override
    public void getElevator(CommonBean commonBean) {

    }
}
