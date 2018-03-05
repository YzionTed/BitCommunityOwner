package com.bit.fuxingwuye.base;



import com.bit.fuxingwuye.constant.HttpConstants;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * SmartCommunity-com.bit.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/1.
 */
public class BaseEntity<T> extends ResponseBody {

    @SerializedName("errorCode")
    private String code;
    @SerializedName("success")
    private boolean result;
    @SerializedName("errorMsg")
    private String msg;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return  result;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getResultCode(){
//        if (result == null || TextUtils.isEmpty(result)) return 0;
//        return Integer.parseInt(result);
        if (result){
            return HttpConstants.OPERAT_OK;
        }else {
            return 0;
        }
    }

    public String getResult() {
       if (result){
            return "success";
        }else {
            return "fail";
        }
      // return msg;



//        if(result.equals("10001")){
//            return "系统异常";
//        }
//        if(result.equals("10002")){
//            return "redis未启动";
//        }
//        if(result.equals("10003")){
//            return "短信接口异常，运营商问题";
//        }
//        if(result.equals("10004")){
//            return "操作失败";
//        }
//        if(result.equals("10005")){
//            return "操作成功";
//        }
//        if(result.equals("11001")){
//            return "手机号为空";
//        }
//        if(result.equals("11002")){
//            return "密码为空";
//        }
//        if(result.equals("11003")){
//            return "验证码为空";
//        }
//        if(result.equals("11004")){
//            return "验证方式为空";
//        }
//        if(result.equals("11005")){
//            return "验证码已发送";
//        }
//        if(result.equals("11006")){
//            return "验证码错误";
//        }
//        if(result.equals("11007")){
//            return "验证码不符合该操作";
//        }
//        if(result.equals("11008")){
//            return "验证码不存在或已过期";
//        }
//        if(result.equals("11009")){
//            return "该用户没设密码";
//        }
//        if(result.equals("11010")){
//            return "用户不存在";
//        }
//        if(result.equals("11011")){
//            return "token缓存失败";
//        }
//        if(result.equals("11012")){
//            return "帐号或密码错误";
//        }
//        if(result.equals("11013")){
//            return "该手机号已存在";
//        }
//        if(result.equals("11014")){
//            return "修改密码类型为空";
//        }
//        if(result.equals("11015")){
//            return "原密码为空";
//        }
//        if(result.equals("11016")){
//            return "原密码不正确";
//        }
//        if(result.equals("11017")){
//            return "户主名为空";
//        }
//        if(result.equals("11018")){
//            return "身份证为空";
//        }
//        if(result.equals("11019")){
//            return "户主ID为空";
//        }
//        if(result.equals("11020")){
//            return "验证码方式错误";
//        }
//        if(result.equals("12001")){
//            return "该楼房已有户主";
//        }
//        if(result.equals("12002")){
//            return "车位ID为空";
//        }
//        if(result.equals("12003")){
//            return "该车位已有户主";
//        }if(result.equals("12004")){
//            return "非户主";
//        }else{
//            return result;
//        }
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
