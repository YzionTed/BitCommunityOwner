package com.BIT.fuxingwuye.http;

import android.content.Intent;
import android.net.ParseException;


import com.BIT.fuxingwuye.activities.MainTabActivity;
import com.BIT.fuxingwuye.activities.login.LoginActivity;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.bean.EvenBusMessage;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * SmartCommunity-com.BIT.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/7.
 */

public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            ex = new ResponeThrowable(e, HttpConstants.HTTP_ERROR);
            ex.message = "网络错误";
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, HttpConstants.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, HttpConstants.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, HttpConstants.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException){
            ex = new ResponeThrowable(e, HttpConstants.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponeThrowable(e, HttpConstants.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else {
            ex = new ResponeThrowable(e, HttpConstants.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

    public static ServerException handleHttpException(BaseEntity e) {
       /* if(e.getCode().equals("9050001")){
            BaseApplication.finishAllActivity();
            Intent intent=new Intent(BaseApplication.getInstance(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getInstance().startActivity(intent);

        }*/
        ServerException ex;
        ex = new ServerException(e.getResultCode(),e.getMsg());
/*
        switch (e.getResultCode()){
            case HttpConstants.SYSTEM_ERROR:
                ex = new ServerException(e.getResultCode());
                ex.message = "系统异常";
                break;
            case HttpConstants.REDIS_NOT_WORK:
                ex = new ServerException(e.getResultCode());
                ex.message = "redis未启动";
                break;
            case HttpConstants.SMS_WRONG:
                ex = new ServerException(e.getResultCode());
                ex.message = "短信接口异常，运营商问题";
                break;
            case HttpConstants.OPERAT_FAIL:
               ex = new ServerException(e.getResultCode());
                ex.message = "操作失败";
                break;
            case HttpConstants.OPERAT_OK:
                ex.message = "操作成功";
                break;
           case HttpConstants.EMPTY_PHONE:
               ex = new ServerException(e.getResultCode());
               ex.message = "手机号为空";
                break;
            case HttpConstants.EMPTY_PWD:
               ex = new ServerException(e.getResultCode());
               ex.message = "密码为空";
              break;
            case HttpConstants.EMPTY_CODE:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证码为空";
               break;
           case HttpConstants.EMPTY_TYPE:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证方式为空";
                break;
           case HttpConstants.SEND_ALREADY:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证码已发送";
                break;
            case HttpConstants.WRONG_CODE:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证码错误";
                break;
           case HttpConstants.NOT_MATCH:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证码不符合该操作";
                break;
            case HttpConstants.CODE_OVER:
                ex = new ServerException(e.getResultCode());
                ex.message = "验证码不存在或已过期";
                break;
            case HttpConstants.PWD_NOT_SET:
                ex = new ServerException(e.getResultCode());
                ex.message = "该用户没设密码";
                break;
            case HttpConstants.USER_NOT_EXIST:
                ex = new ServerException(e.getResultCode());
                ex.message = "用户不存在";
                break;
            case HttpConstants.TOKEN_WRONG:
                ex = new ServerException(e.getResultCode());
                ex.message = "token缓存失败";
                break;
            case HttpConstants.PWD_WRONG:
                ex = new ServerException(e.getResultCode());
                ex.message = "帐号或密码错误";
                break;
            case HttpConstants.PHONE_EXIST:
                ex = new ServerException(e.getResultCode());
                ex.message = "该手机号已存在";
                break;
            case HttpConstants.NO_PWDTYPE:
                ex = new ServerException(e.getResultCode());
                ex.message = "修改密码类型为空";
               break;
            case HttpConstants.NO_OLDPWD:
               ex = new ServerException(e.getResultCode());
                ex.message = "原密码为空";
                break;
            case HttpConstants.WRONG_OLDPWD:
                ex = new ServerException(e.getResultCode());
                ex.message = "原密码不正确";
                break;
            case HttpConstants.EMPTY_USERNAME:
                ex = new ServerException(e.getResultCode());
                ex.message = "户主名为空";
                break;
            case HttpConstants.EMPTY_IDENTITY:
                ex = new ServerException(e.getResultCode());
                ex.message = "身份证为空";
                break;
            default:
                ex = null;
                break;

        }*/

        return ex;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public static class ServerException extends RuntimeException {
        public int code;
        public String message;

        public ServerException(int code,String msg){
            this.code = code;
            this.message = msg;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
