package com.bit.fuxingwuye.http;

import android.databinding.BaseObservable;

import com.bit.fuxingwuye.base.BaseEntity;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * SmartCommunity-com.bit.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/4.
 */

public interface IRetrofitRequest {

    @GET("{url}")
    Observable<ResponseBody> doGet(@Path("url") String url, @QueryMap Map<String, String> maps);

    @POST("{url}")
    <T> Observable<ResponseBody> doPost(
            @Path("url") String url, @QueryMap Map<String, T> maps);

    @POST("{url}")
    Observable<ResponseBody> doPostJson(
            @Path("url") String url, @Body BaseObservable jsonStr);

    @POST
    @Headers({"Content-Type:application/json; charset=utf-8", "accept:application/json"})
    Observable<BaseEntity> doPostJsonWithHeaders(
            @Url String url, @Body BaseObservable jsonStr);

    @POST
    @Headers({"Content-Type:application/json; charset=utf-8", "accept:application/json"})
    Observable<BaseEntity> doPostJsonWithHeaders(
            @Url String url, @Body RequestBody jsonStr);

    @POST
    Observable<ResponseBody> doPostJsonWithHeaders(
            @Url String url, @Body BaseObservable jsonStr, @Header("Content-Type") String contentType, @Header("accept") String accept);


    @POST
    Observable<ResponseBody> doPostJsonWithHeaders(
            @Url String url, @Body BaseObservable jsonStr, @Path("headers") Map<String, String> headers);


    @POST("{url}")
    Observable<ResponseBody> upLoader(@Path("url") String url,
                                      @Path("headers") Map<String, String> headers,
                                      @Part("filename") String description,
                                      @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downLoader(@Url String url);
}
