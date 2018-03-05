package com.bit.fuxingwuye.dagger.component;

import android.app.Activity;

import com.bit.fuxingwuye.activities.addReply.AddReplyActivity;
import com.bit.fuxingwuye.activities.brake.BrakeActivity;
import com.bit.fuxingwuye.activities.chooseHouse.ChooseHouseActivity;
import com.bit.fuxingwuye.activities.community.CommunityActivity;
import com.bit.fuxingwuye.activities.aboutBit.AboutBitActivity;
import com.bit.fuxingwuye.activities.addFaultRepair.FaultRepairActivity;
import com.bit.fuxingwuye.activities.addNew.AddNewActivity;
import com.bit.fuxingwuye.activities.callEle.CallEleActivity;
import com.bit.fuxingwuye.activities.carManager.CarManagerActivity;
import com.bit.fuxingwuye.activities.commitSuccess.CommitSuccessActivity;
import com.bit.fuxingwuye.activities.communityDetail.CommunityDetailActivity;
import com.bit.fuxingwuye.activities.editPersonal.EditPersonalActivity;
import com.bit.fuxingwuye.activities.feedback.FeedbackActivity;
import com.bit.fuxingwuye.activities.goods.GoodsActivity;
import com.bit.fuxingwuye.activities.houseManager.ApplicationDetailsActivity;
import com.bit.fuxingwuye.activities.houseManager.ApplicationDetailsContract;
import com.bit.fuxingwuye.activities.houseManager.ApplicationRecordActivity;
import com.bit.fuxingwuye.activities.houseManager.HouseManagerActivity;
import com.bit.fuxingwuye.activities.houseManager.ProprietorManagementActivity;
import com.bit.fuxingwuye.activities.householdManager.HouseholdManagerActivity;
import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.activities.message.MessageActivity;
import com.bit.fuxingwuye.activities.myRepairList.MyRepairsActivity;
import com.bit.fuxingwuye.activities.onlinePay.OnlinePayActivity;
import com.bit.fuxingwuye.activities.parkPicker.ParkPickerActivity;
import com.bit.fuxingwuye.activities.payContent.PayContentActivity;
import com.bit.fuxingwuye.activities.payList.PayListActivity;
import com.bit.fuxingwuye.activities.payRecord.PayRecordActivity;
import com.bit.fuxingwuye.activities.payResult.PayResultActivity;
import com.bit.fuxingwuye.activities.payment.PaymentActivity;
import com.bit.fuxingwuye.activities.personalEdit.PersonalEditActivity;
import com.bit.fuxingwuye.activities.personlPage.PersonalPageActivity;
import com.bit.fuxingwuye.activities.register.RegisterActivity;
import com.bit.fuxingwuye.activities.repairDetail.RepairDetailActivity;
import com.bit.fuxingwuye.activities.replenishCar.ReplenishCarActivity;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.replenishHouse.ReplenishHouseActivity;
import com.bit.fuxingwuye.activities.resetPwd.ResetPwdActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;
import com.bit.fuxingwuye.activities.roomPicker.RoomPickerActivity;
import com.bit.fuxingwuye.activities.serviceComment.ServiceCommentActivity;
import com.bit.fuxingwuye.activities.setting.SettingActivity;
import com.bit.fuxingwuye.activities.shops.ShopsActivity;
import com.bit.fuxingwuye.activities.via.ViaActivity;
import com.bit.fuxingwuye.activities.viaRecord.ViaRecordActivity;
import com.bit.fuxingwuye.activities.videoDevices.VideoDevicesActivity;
import com.bit.fuxingwuye.bean.GoodsBean;
import com.bit.fuxingwuye.dagger.ActivityScope;
import com.bit.fuxingwuye.dagger.module.ActivityModule;

import dagger.Component;

/**
 * Created by Dell on 2017/8/1.
 * Created time:2017/8/1 10:14
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MessageActivity messageActivity);
    void inject(PersonalPageActivity personalPageActivity);
    void inject(LoginActivity loginActivity);
    void inject(AboutBitActivity aboutBitActivity);
    void inject(EditPersonalActivity editPersonalActivity);
    void inject(SettingActivity settingActivity);
    void inject(RegisterActivity registerActivity);
    void inject(ResetPwdActivity resetPwdActivity);
    void inject(ReplenishDataActivity replenishDataActivity);
    void inject(ReplenishHouseActivity replenishHouseActivity);
    void inject(ReplenishCarActivity replenishCarActivity);
    void inject(CommitSuccessActivity commitSuccessActivity);
    void inject(AddNewActivity addNewActivity);
    void inject(CarManagerActivity carManagerActivity);
    void inject(HouseManagerActivity houseManagerActivity);
    void inject(HouseholdManagerActivity householdManagerActivity);
    void inject(FeedbackActivity feedbackActivity);
    void inject(PaymentActivity paymentActivity);
    void inject(ParkPickerActivity parkPickerActivity);
    void inject(RoomPickerActivity roomPickerActivity);
    void inject(PayResultActivity payResultActivity);
    void inject(FaultRepairActivity faultRepairActivity);
    void inject(MyRepairsActivity myRepairsActivity);
    void inject(RepairDetailActivity repairDetailActivity);
    void inject(ServiceCommentActivity serviceCommentActivity);
    void inject(CallEleActivity callEleActivity);
    void inject(PayListActivity payListActivity);
    void inject(PayRecordActivity payRecordActivity);
    void inject(PayContentActivity payContentActivity);
    void inject(OnlinePayActivity onlinePayActivity);
    void inject(ShopsActivity shopsActivity);
    void inject(CommunityActivity communityActivity);
    void inject(ChooseHouseActivity chooseHouseActivity);
    void inject(VideoDevicesActivity videoDevicesActivity);
    void inject(GoodsActivity goodsActivity);
    void inject(AddReplyActivity addReplyActivity);
    void inject(CommunityDetailActivity communityDetailActivity);
    void inject(PersonalEditActivity personalEditActivity);
    void inject(ViaActivity viaActivity);
    void inject(ViaRecordActivity viaRecordActivity);
    void inject(BrakeActivity brakeActivity);
    void inject(Housing housing);
    void inject(ProprietorManagementActivity proprietorManagementActivity);
    void inject(ApplicationRecordActivity applicationRecordActivity);
    void inject(ApplicationDetailsActivity applicationDetailsActivity);
}
