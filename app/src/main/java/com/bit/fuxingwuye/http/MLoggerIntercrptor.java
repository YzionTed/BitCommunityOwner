package com.BIT.fuxingwuye.http;

import android.text.TextUtils;
import android.util.Log;

import com.BIT.fuxingwuye.base.BaseApplication;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.BIT.fuxingwuye.utils.ACache;
import com.BIT.fuxingwuye.utils.AppInfo;
import com.BIT.fuxingwuye.utils.LogUtil;
import com.BIT.fuxingwuye.utils.Tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * KangTuUpperComputer-com.kangtu.uppercomputer.http
 * 作者： YanwuTang
 * 时间： 2016/9/26.
 */
public class MLoggerIntercrptor implements Interceptor {
    public static final String TAG = "MLoggerIntercrptor";
    private String tag;
    private boolean showResponse;
    private Map<String, String> headers;
    ACache aCache;

    public MLoggerIntercrptor(String tag, boolean showResponse) {

        this(tag, true, null);

    }

    public MLoggerIntercrptor(String tag) {
        this(tag, false, null);
    }

    public MLoggerIntercrptor(String tag, boolean showResponse, Map<String, String> headers) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        aCache=ACache.get(BaseApplication.getInstance());
        this.showResponse = showResponse;
        this.tag = tag;
        this.headers = headers;
        if (headers == null || headers.size() <= 0){
            initHeaders();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Request.Builder builder = chain.request()
                .newBuilder();
        if(!TextUtils.isEmpty(BaseApplication.getInstance().getToken())){
            builder.addHeader(HttpConstants.TOKEN,BaseApplication.getInstance().getToken());
        }
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return logForResponse(chain.proceed(builder.build()));
    }

    /**
     * 默认headers
     */
    private void initHeaders(){
        if (headers == null){
            headers = new HashMap<>();
           // headers.put("accept", "application/json");
            headers.put("Content-Type", "application/json");
            headers.put("CLIENT","1000");
            headers.put("OS-VERSION","Android");
            headers.put("DEVICE-TYPE","Android");
            headers.put("APP-VERSION", AppInfo.getLocalVersionName(BaseApplication.getInstance()));
           if(aCache!=null&&aCache.getAsString(HttpConstants.TOKEN)!=null){
                headers.put("BIT-TOKEN",""+aCache.getAsString(HttpConstants.TOKEN));
            }
            if(aCache!=null&&aCache.getAsString(HttpConstants.USERID)!=null){
                headers.put("BIT-UID",""+aCache.getAsString(HttpConstants.USERID));
            }

        }

    }
    /**
     * log
     * @param response
     * @return
     */
    private Response logForResponse(Response response) {
        try {
            //===>response log
            Log.e(tag, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(tag, "url : " + clone.request().url());
            Log.e(tag, "code : " + clone.code());
            Log.e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                Log.e(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.e(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }

            Log.e(tag, "========response'log=======end");
        } catch (Exception e) {
          //  e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(tag, "========request'log=======");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "requestBody's content : " + requestBody.toString());
                    }
                }
            }
            Log.e(tag, "========request'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
