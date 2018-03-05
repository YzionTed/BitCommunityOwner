package com.BIT.fuxingwuye.dagger.component;

import android.app.Activity;

import com.BIT.fuxingwuye.activities.addReply.AddReplyActivity;
import com.BIT.fuxingwuye.activities.brake.BrakeActivity;
import com.BIT.fuxingwuye.activities.chooseHouse.ChooseHouseActivity;
import com.BIT.fuxingwuye.activities.community.CommunityActivity;
import com.BIT.fuxingwuye.activities.aboutBit.AboutBitActivity;
import com.BIT.fuxingwuye.activities.addFaultRepair.FaultRepairActivity;
import com.BIT.fuxingwuye.activities.addNew.AddNewActivity;
import com.BIT.fuxingwuye.activities.callEle.CallEleActivity;
import com.BIT.fuxingwuye.activities.carManager.CarManagerActivity;
import com.BIT.fuxingwuye.activities.commitSuccess.CommitSuccessActivity;
import com.BIT.fuxingwuye.activities.communityDetail.CommunityDetailActivity;
import com.BIT.fuxingwuye.activities.editPersonal.EditPersonalActivity;
import com.BIT.fuxingwuye.activities.feedback.FeedbackActivity;
import com.BIT.fuxingwuye.activities.goods.GoodsActivity;
import com.BIT.fuxingwuye.activities.houseManager.ApplicationDetailsActivity;
import com.BIT.fuxingwuye.activities.houseManager.ApplicationDetailsContract;
import com.BIT.fuxingwuye.activities.houseManager.ApplicationRecordActivity;
import com.BIT.fuxingwuye.activities.houseManager.HouseManagerActivity;
import com.BIT.fuxingwuye.activities.houseManager.ProprietorManagementActivity;
import com.BIT.fuxingwuye.activities.householdManager.HouseholdManagerActivity;
import com.BIT.fuxingwuye.activities.login.LoginActivity;
import com.BIT.fuxingwuye.activities.message.MessageActivity;
import com.BIT.fuxingwuye.activities.myRepairList.MyRepairsActivity;
import com.BIT.fuxingwuye.activities.onlinePay.OnlinePayActivity;
import com.BIT.fuxingwuye.activities.parkPicker.ParkPickerActivity;
import com.BIT.fuxingwuye.activities.payContent.PayContentActivity;
import com.BIT.fuxingwuye.activities.payList.PayListActivity;
import com.BIT.fuxingwuye.activities.payRecord.PayRecordActivity;
import com.BIT.fuxingwuye.activities.payResult.PayResultActivity;
import com.BIT.fuxingwuye.activities.payment.PaymentActivity;
import com.BIT.fuxingwuye.activities.personalEdit.PersonalEditActivity;
import com.BIT.fuxingwuye.activities.personlPage.PersonalPageActivity;
import com.BIT.fuxingwuye.activities.register.RegisterActivity;
import com.BIT.fuxingwuye.activities.repairDetail.RepairDetailActivity;
import com.BIT.fuxingwuye.activities.replenishCar.ReplenishCarActivity;
import com.BIT.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.BIT.fuxingwuye.activities.replenishHouse.ReplenishHouseActivity;
import com.BIT.fuxingwuye.activities.resetPwd.ResetPwdActivity;
import com.BIT.fuxingwuye.activities.residential_quarters.Housing;
import com.BIT.fuxingwuye.activities.roomPicker.RoomPickerActivity;
import com.BIT.fuxingwuye.activities.serviceComment.ServiceCommentActivity;
import com.BIT.fuxingwuye.activities.setting.SettingActivity;
import com.BIT.fuxingwuye.activities.shops.ShopsActivity;
import com.BIT.fuxingwuye.activities.via.ViaActivity;
import com.BIT.fuxingwuye.activities.viaRecord.ViaRecordActivity;
import com.BIT.fuxingwuye.activities.videoDevices.VideoDevicesActivity;
import com.BIT.fuxingwuye.bean.GoodsBean;
import com.BIT.fuxingwuye.dagger.ActivityScope;
import com.BIT.fuxingwuye.dagger.module.ActivityModule;

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
