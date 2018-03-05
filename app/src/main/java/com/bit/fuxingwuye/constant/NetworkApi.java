package com.BIT.fuxingwuye.constant;


import com.BIT.communityOwner.net.Url;
import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.base.ProprietorBean;
import com.BIT.fuxingwuye.bean.Building;
import com.BIT.fuxingwuye.bean.CallPoliceBean;
import com.BIT.fuxingwuye.bean.CarBean;
import com.BIT.fuxingwuye.bean.CodeBean;
import com.BIT.fuxingwuye.bean.CommonBean;
import com.BIT.fuxingwuye.bean.Community;
import com.BIT.fuxingwuye.bean.CommunityBean;
import com.BIT.fuxingwuye.bean.EditUserBean;
import com.BIT.fuxingwuye.bean.ElevatorBean;
import com.BIT.fuxingwuye.bean.EvaluationBean;
import com.BIT.fuxingwuye.bean.FacilityBean;
import com.BIT.fuxingwuye.bean.FeedbackBean;
import com.BIT.fuxingwuye.bean.FindBean;
import com.BIT.fuxingwuye.bean.FloorBean;
import com.BIT.fuxingwuye.bean.GoodsBean;
import com.BIT.fuxingwuye.bean.HouseBean;
import com.BIT.fuxingwuye.bean.HouseholdBean;
import com.BIT.fuxingwuye.bean.HouseholdsBean;
import com.BIT.fuxingwuye.bean.ImagePathBean;
import com.BIT.fuxingwuye.bean.InformationBean;
import com.BIT.fuxingwuye.bean.LoginBean;
import com.BIT.fuxingwuye.bean.MerchantBean;
import com.BIT.fuxingwuye.bean.MerchantsBean;
import com.BIT.fuxingwuye.bean.Notice;
import com.BIT.fuxingwuye.bean.NoticeBean;
import com.BIT.fuxingwuye.bean.NoticeListBean;
import com.BIT.fuxingwuye.bean.OutLogin;
import com.BIT.fuxingwuye.bean.ParkBean;
import com.BIT.fuxingwuye.bean.PayListBean;
import com.BIT.fuxingwuye.bean.PayReqBean;
import com.BIT.fuxingwuye.bean.PlotInfoBean;
import com.BIT.fuxingwuye.bean.RegisterBean;
import com.BIT.fuxingwuye.bean.RepairBean;
import com.BIT.fuxingwuye.bean.ReplenishBean;
import com.BIT.fuxingwuye.bean.ReplenishHouseBean;
import com.BIT.fuxingwuye.bean.ReplyBean;
import com.BIT.fuxingwuye.bean.ResetPwdBean;
import com.BIT.fuxingwuye.bean.Room;
import com.BIT.fuxingwuye.bean.RoomList;
import com.BIT.fuxingwuye.bean.TokenBean;
import com.BIT.fuxingwuye.bean.TradeBean;
import com.BIT.fuxingwuye.bean.UserBean;
import com.BIT.fuxingwuye.bean.VersionBean;
import com.BIT.fuxingwuye.bean.ViaBean;
import com.BIT.fuxingwuye.bean.ZanBean;
import com.BIT.fuxingwuye.bean.request.Code;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Dell on 2017/7/7.
 */

public interface NetworkApi {

    @POST(HttpConstants.GET_CODE)
    Observable<BaseEntity<Code>> getCode(@Body CodeBean codeBean);

    @POST(HttpConstants.VERIFY_CODE_BY_PHONE)
    Observable<BaseEntity<Integer>> verifyCode(@Body CodeBean codeBean);

    @POST(HttpConstants.LOGIN)
    Observable<BaseEntity<TokenBean>> login(@Body LoginBean loginBean);

    @POST(HttpConstants.REGISTER)
    Observable<BaseEntity<TokenBean>> register(@Body RegisterBean registerBean);

    @POST(HttpConstants.CHANGE_PWD)
    Observable<BaseEntity<String>> resetPwd(@Body ResetPwdBean resetPwdBean);

    @POST(HttpConstants.REPLENISH)
    Observable<BaseEntity<HouseBean>> replenish(@Body ReplenishBean replenishBean);
    @POST(HttpConstants.MEMBER)
    Observable<BaseEntity<HouseBean>> member(@Body ReplenishBean replenishBean);

    @POST(HttpConstants.EDITWPFLOOR)
    Observable<BaseEntity<String>> replenishHouse(@Body ReplenishHouseBean replenishHouseBean);
    @POST(HttpConstants.BIND_CAR_INFO)
    Observable<BaseEntity<String>> replenishCar(@Body CarBean carBean);

    @POST(HttpConstants.FINDONE)
    Observable<BaseEntity<UserBean>> findOne(@Body FindBean findBean);
    @POST(HttpConstants.ADDHOUSSEHOLD)
    Observable<BaseEntity<String>> addHouseholds(@Body HouseholdsBean householdBeans);
    @POST(HttpConstants.GET_USER_AFFILIATE)
    Observable<BaseEntity<List<HouseholdBean>>> getUserAffiliate(@Body CommonBean commonBean);
    @POST(HttpConstants.GET_PARK_BY_UID)
    Observable<BaseEntity<List<ParkBean>>> getParkByUser(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_FLOORS)

    Observable<BaseEntity<List<RoomList>>> getFloors(@Body Map<String,String> f);
    @GET(Url.V1_PROPERTY_NOTICE_PAGE)
    Observable<BaseEntity<NoticeListBean>> getNotices(@Path("communityId") String communityId,@Query("page") int page);
    @POST(HttpConstants.GET_NOTICE)
    Observable<BaseEntity<NoticeBean>> getNotice(@Body CommonBean commonBean);
    @POST(HttpConstants.CHECK_VERSION)
    Observable<BaseEntity<VersionBean>> checkVersion(@Body CommonBean commonBean);
    @POST(HttpConstants.GET_TALIKING)
    Observable<BaseEntity<String>> feedback(@Body FeedbackBean feedbackBean);

    @POST(HttpConstants.DEL_USER)
    Observable<BaseEntity<String>> deleteuesr(@Body CommonBean commonBean);
    @POST(HttpConstants.DEL_FLOOR)
    Observable<BaseEntity<String>> deletefloor(@Body CommonBean commonBean);
    @POST(HttpConstants.DEL_PARK)
    Observable<BaseEntity<String>> deletepark(@Body CommonBean commonBean);
    @POST(HttpConstants.GET_STORES)
    Observable<BaseEntity<String>> getStores();

    @POST(HttpConstants.GET_CAR_INFO)
    Observable<BaseEntity<List<PlotInfoBean>>> getParks(@Body CommonBean commonBean);


    @POST(HttpConstants.GET_PLOT_INFO)
    Observable<BaseEntity<List<PlotInfoBean>>> getPlots(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_REPAIRS)
    Observable<BaseEntity<List<RepairBean>>> getRepairs(@Body CommonBean commonBean);

    @POST(HttpConstants.ADD_REPAIR)
    Observable<BaseEntity<String>> addRepair(@Body RepairBean repairBean);


    @POST(HttpConstants.UPLOAD)
    @Multipart
    Observable<BaseEntity<List<String>>> upload(@PartMap Map<String, RequestBody> files);

    @POST(HttpConstants.DOWNLOAD)
    Observable<BaseEntity<RepairBean.ImageBean>> download(@Body CommonBean commonBean);

    @POST(HttpConstants.GETIMAGES)
    Observable<BaseEntity<List<ImagePathBean>>> getImages(@Body RepairBean.ImageBean imageBean);

    @POST(HttpConstants.UPDATE_REPAIR)
    Observable<BaseEntity<String>> updateRepair(@Body RepairBean repairBean);

    @POST(HttpConstants.DELETE_REPAIR)
    Observable<BaseEntity<String>> deleteRepair(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_REPAIR)
    Observable<BaseEntity<RepairBean>> getRepair(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_ELEVATOR)
    Observable<BaseEntity<ElevatorBean>> getElevator(@Body CommonBean commonBean);

    @POST(HttpConstants.ADD_EVALUATION)
    Observable<BaseEntity<String>> addEvaluation(@Body EvaluationBean evaluationBean);

    @POST(HttpConstants.GET_EVALUATION)
    Observable<BaseEntity<EvaluationBean>> getEvaluation(@Body EvaluationBean evaluationBean);

    @POST(HttpConstants.CALLPOLICE)
    Observable<BaseEntity<String>> callPolice(@Body CallPoliceBean callPoliceBean);

    @POST(HttpConstants.WX_PAY)
    Observable<BaseEntity<PayReqBean.cont>> wxpay(@Body PayReqBean payReqBean);

    @POST(HttpConstants.ALI_PAY)
    Observable<BaseEntity<PayReqBean.cont>> alipay(@Body PayReqBean payReqBean);

    @POST(HttpConstants.WX_QUERY)
    Observable<BaseEntity<TradeBean>> wxquery(@Body CommonBean commonBean);

    @POST(HttpConstants.ALI_QUERY)
    Observable<BaseEntity<TradeBean>> aliquery(@Body CommonBean commonBean);

    @POST(HttpConstants.MERCHANT)
    Observable<BaseEntity<MerchantsBean>> getMerchants(@Body MerchantBean merchantBean);

    @POST(HttpConstants.MERCHANT_GOODS)
    Observable<BaseEntity<List<GoodsBean>>> getGoods(@Body GoodsBean goodsBean);

    @POST(HttpConstants.GET_SLIDE)
    Observable<BaseEntity<List<String>>> getslide(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_UNPAY_LIST)
    Observable<BaseEntity<List<PayListBean>>> getunpaylists(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_PAY_HISTORY)
    Observable<BaseEntity<List<PayListBean>>> getPayHistory(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_PAY_CONTENT)
    Observable<BaseEntity<PayListBean>> getPayContent(@Body CommonBean commonBean);

    @POST(HttpConstants.QUERY_INFO_PAGE)
    Observable<BaseEntity<InformationBean>> queryInfoPage(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_NEW_REPLIES)
    Observable<BaseEntity<List<InformationBean.Info>>> get_new_replies(@Body CommonBean commonBean);

    @POST(HttpConstants.SAVE_INFO)//新增动态
    Observable<BaseEntity<String>> addReply(@Body ReplyBean replyBean);

    @POST(HttpConstants.SAVE_REPLY)//新增动态
    Observable<BaseEntity<CommunityBean>> saveReply(@Body ReplyBean replyBean);

    @POST(HttpConstants.GET_INFO_BY_ID)
    Observable<BaseEntity<CommunityBean>> getInfoDetail(@Body CommonBean commonBean);

    @POST(HttpConstants.DEL_INFO)
    Observable<BaseEntity<String>> delete_info(@Body CommonBean commonBean);

    @POST(HttpConstants.ZAN)
    Observable<BaseEntity<InformationBean.Info>> zan(@Body ZanBean zanBean);

    @POST(HttpConstants.EDIT_USER)
    Observable<BaseEntity<UserBean>> editUser(@Body EditUserBean editUserBean);

    @POST(HttpConstants.EXIST_EMCHAT)
    Observable<BaseEntity<String>> existEmchat(@Body LoginBean loginBean);

    @POST(HttpConstants.SET_JPUSH_ALIAS)
    Observable<BaseEntity<String>> setJPushAlias(@Body CommonBean commonBean);

    @POST(HttpConstants.ADD_VIA)
    Observable<BaseEntity<String>> addVia(@Body ViaBean viaBean);

    @POST(HttpConstants.GET_VIA_LIST)
    Observable<BaseEntity<List<ViaBean>>> getViaList(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_VIA_CONTENT)
    Observable<BaseEntity<ViaBean>> getViaContent(@Body CommonBean commonBean);

    @POST(HttpConstants.DELETE_VIA)
    Observable<BaseEntity<String>> deleteVia(@Body CommonBean commonBean);

    @POST(HttpConstants.GET_DOOR_FACILITYS_NEW)
    Observable<BaseEntity<List<FacilityBean>>> getDoors(@Body CommonBean commonBean);

    @GET(HttpConstants.community)
    Observable<BaseEntity<Community>> getcommunity(@Query("userId") String userId);
    @GET(HttpConstants.building)
    Observable<BaseEntity<Building>> getbuilding(@Query("communityId") String communityId);
    @GET(HttpConstants.room)
    Observable<BaseEntity<Room>> getroom(@Query("buildingId") String buildingId);
    @GET("/v1/user/signOut")
    Observable<BaseEntity<OutLogin>> signOut();
    @GET(Url.V1_USER_GETBYROOMID)
    Observable<BaseEntity<ProprietorBean>> Proprietor(@Path("roomId") String roomId, @Query("auditStatus") String auditStatus);
    @GET(Url.V1_USER_GETBYROOMID)
    Observable<BaseEntity<ProprietorBean>> Proprietor(@Path("roomId") String roomId);
    @GET(Url.V1_USER_CLOSEAPPLYBYID)
    Observable<BaseEntity<String>> Closed(@Path("id") String Id);
    @POST(Url.V1_USER_MEMBER_AUDIT)
    Observable<BaseEntity<String>> audit(@Body ProprietorBean.RecordsBean recordsBean);

    @GET(Url.V1_USER_MEMBER_RELIEVE)
    Observable<BaseEntity<String>> relieve(@Path("id") String id);
}
