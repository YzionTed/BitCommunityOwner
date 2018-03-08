package com.bit.communityOwner.net;

import com.bit.communityOwner.model.PassCode;
import com.bit.communityOwner.model.PassFlag;
import com.bit.communityOwner.model.ResetPassword;
import com.bit.communityOwner.model.UserInfo;
import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.model.VerfriyCode;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.bean.AppVersionInfo;
import com.bit.fuxingwuye.bean.CallPoliceBean;
import com.bit.fuxingwuye.bean.DoorMILiBean;
import com.bit.fuxingwuye.bean.DoorMiLiRequestBean;
import com.bit.fuxingwuye.bean.ElevatorCardRequestion;
import com.bit.fuxingwuye.bean.ElevatorListBean;
import com.bit.fuxingwuye.bean.ElevatorListRequestion;
import com.bit.fuxingwuye.bean.FeedbackBean;
import com.bit.fuxingwuye.bean.GetFeedbackBean;
import com.bit.fuxingwuye.bean.GetUserRoomListBean;
import com.bit.fuxingwuye.bean.HouseBean;
import com.bit.fuxingwuye.bean.PropertyBean;
import com.bit.fuxingwuye.bean.ReplenishBean;
import com.bit.fuxingwuye.bean.TokenBean;
import com.bit.fuxingwuye.bean.UserRoomBean;
import com.bit.fuxingwuye.bean.request.PassCodeListBean;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.newsdetail.bean.NewsDetailBean;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.AppInfo;

import java.util.List;

/**
 * 项目接口统一封装
 * Created by zhangjiajie on 18/2/28.
 */

public class Api {

    /**
     * 反馈
     *
     * @param content
     * @param callBack
     */
    public static void feedback(String content, ResponseCallBack<GetFeedbackBean> callBack) {
        FeedbackBean feedbackbean = new FeedbackBean();
        feedbackbean.setAppId(AppInfo.getAppId(BaseApplication.getInstance().getContext()));
        feedbackbean.setContent(content);
        ApiRequester.post(HttpConstants.GET_TALIKING, feedbackbean, callBack);
    }

    public static void Replanish(ReplenishBean replenishBean, ResponseCallBack<HouseBean> callBack) {
        ApiRequester.post(HttpConstants.REPLENISH, replenishBean, callBack);
    }
    public static  void commitMember(ReplenishBean replenishBean, ResponseCallBack<HouseBean> callBack){
        ApiRequester.post(HttpConstants.MEMBER, replenishBean, callBack);
    }

    /**
     * 获取oss鉴权
     *
     * @param callBack
     */
    public static void ossToken(ResponseCallBack<OssToken> callBack) {
        ApiRequester.get(Url.V1_OSS_STS_TOKEN, null, callBack);
    }


    /**
     * 修改整个用户信息
     *
     * @param tokenBean
     * @param callBack
     */
    public static void editUserInfo(TokenBean tokenBean, ResponseCallBack<UserInfo> callBack) {
        ApiRequester.post(Url.V1_USER_EDIT, tokenBean, callBack);
    }

    /**
     * 修改头像
     *
     * @param imageUrl
     * @param callBack
     */
    public static void editHeadUrl(String imageUrl, ResponseCallBack<UserInfo> callBack) {

        TokenBean userBean = (TokenBean) ACache.get(BaseApplication.getInstance())
                .getAsObject(HttpConstants.TOKENBEAN);
        TokenBean edit = new TokenBean();
        edit.setId(userBean.getId());
        edit.setHeadImg(imageUrl);
        ApiRequester.post(Url.V1_USER_EDIT, edit, callBack);
    }

    /**
     * 修改用户名
     *
     * @param username
     * @param callBack
     */
    public static void editUsername(String username, ResponseCallBack<UserInfo> callBack) {

        TokenBean userBean = (TokenBean) ACache.get(BaseApplication.getInstance())
                .getAsObject(HttpConstants.TOKENBEAN);
        TokenBean edit = new TokenBean();
        edit.setId(userBean.getId());
        edit.setName(username);
        ApiRequester.post(Url.V1_USER_EDIT, edit, callBack);
    }

    /**
     * 修改用户性别
     *
     * @param sex      1男，0女
     * @param callBack
     */
    public static void editUserSex(int sex, ResponseCallBack<UserInfo> callBack) {
        TokenBean userBean = (TokenBean) ACache.get(BaseApplication.getInstance())
                .getAsObject(HttpConstants.TOKENBEAN);
        TokenBean edit = new TokenBean();
        edit.setId(userBean.getId());
        edit.setSex(String.valueOf(sex));
        ApiRequester.post(Url.V1_USER_EDIT, userBean, callBack);
    }

    /**
     * 一键报警
     *
     * @param roomId
     * @param callBack
     */
    public static void callPolice(String roomId, ResponseCallBack<String> callBack) {
        CallPoliceBean bean = new CallPoliceBean();
        bean.setRoomid(roomId);
        ApiRequester.post(HttpConstants.CALLPOLICE, bean, callBack);
    }


    /**
     * 获取业主房屋列表
     *
     * @param communityId
     * @param callBack
     */

    public static void getUserRoomsList(String communityId, ResponseCallBack<List<GetUserRoomListBean>> callBack) {
        UserRoomBean bean = new UserRoomBean();
        bean.setAuditStatus(1);
        bean.setCommunityId(communityId);
        ApiRequester.post(HttpConstants.GET_ROOM_QUERY, bean, callBack);
    }

    public static void getAppVersion(ResponseCallBack<AppVersionInfo> callBack, Object... o) {
        ApiRequester.get(ApiRequester.createUrl(HttpConstants.GET_APP_VERSION, o), null, callBack);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param type     验证码类型
     *                 1（注册）
     *                 2（登录）
     *                 3（修改密码）
     *                 4（修改手机号）
     * @param callBack
     */
    public static void getVerfriyCode(String phone, int type, ResponseCallBack<String> callBack) {
        VerfriyCode verfriyCode = new VerfriyCode();
        verfriyCode.setPhone(phone);
        verfriyCode.setBizCode(type);
        ApiRequester.post(Url.V1_USER_GET_VERIFICATION_CODE, verfriyCode, callBack);
    }


//    /**
//     * 修改密码
//     *
//     * @param phone
//     * @param callBack
//     */
//    public static void changePassword(String oldPass, String newPass, ResponseCallBack<String> callBack) {
//        ResetPassword resetPassword = new ResetPassword();
//        resetPassword.set
//        verfriyCode.setCodeType("2");
//        ApiRequester.post(Url.V1_USER_CHANGE_PASSWORD, verfriyCode, callBack);
//    }

    /**
     * 重置密码
     *
     * @param phone
     * @param callBack
     */
    public static void resetPassword(String phone, String code, String newPass, ResponseCallBack<String> callBack) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setPhone(phone);
        resetPassword.setCode(code);
        resetPassword.setNewPass(newPass);
        ApiRequester.post(Url.V1_USER_RESET_PASSWORD, resetPassword, callBack);
    }

    /**
     * 上传蓝牙门禁列表
     *
     * @param doorMiLiRequestBean
     * @param callBack
     */
    public static void getDoorDate(DoorMiLiRequestBean doorMiLiRequestBean, ResponseCallBack<List<DoorMILiBean>> callBack) {
        ApiRequester.post(HttpConstants.door_list, doorMiLiRequestBean, callBack);
    }

    /**
     * 蓝牙搜索上传Mac 梯禁卡
     *
     * @param doorMiLiRequestBean
     * @param callBack
     */
    public static void lanyaElevatorList(ElevatorCardRequestion doorMiLiRequestBean, ResponseCallBack<List<ElevatorListBean>> callBack) {
        ApiRequester.post(HttpConstants.lanya_elevator_list, doorMiLiRequestBean, callBack);
    }

    /**
     * 蓝牙搜索上传Mac 梯禁卡
     *
     * @param doorMiLiRequestBean
     * @param callBack
     */
    public static void lanyaElevatorLists(ElevatorListRequestion doorMiLiRequestBean, ResponseCallBack<List<ElevatorListBean>> callBack) {
        ApiRequester.post(HttpConstants.lanya_elevator_lists, doorMiLiRequestBean, callBack);
    }

    public static void rpass(PassFlag passFlag, ResponseCallBack<PassCode> callBack) {
        ApiRequester.post(Url.V1_USER_ADD_PASS_FLAG, passFlag, callBack);
    }


    public static void  getPassCodeList(PropertyBean bean,int page,int size, ResponseCallBack<PassCodeListBean> callBack) {
        ApiRequester.post(Url.V1_PASSCODE_LIST + "?page=" + page + "&size=" + size, bean, callBack);
    }
    /**
     * 公告详情
     * @param callBack
     * @param o
     */
    public static void getNoticeDetail(ResponseCallBack<NewsDetailBean> callBack, Object... o){
        ApiRequester.get(ApiRequester.createUrl(Url.V1_NOTICE_DETAIL,o),null,callBack);
    }
}
