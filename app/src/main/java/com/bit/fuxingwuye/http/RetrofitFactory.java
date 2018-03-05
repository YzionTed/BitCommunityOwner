package com.BIT.fuxingwuye.http;

import android.databinding.BaseObservable;
import android.util.Log;

import com.BIT.fuxingwuye.base.BaseEntity;
import com.BIT.fuxingwuye.constant.HttpConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * SmartCommunity-com.BIT.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/1.
 */
@Module
public class RetrofitFactory {

    private Retrofit.Builder builder;
    private OkHttpClient okHttpClient;

    public RetrofitFactory() {
        initBuilder();
    }

    @Singleton
    @Provides
    public RetrofitFactory getInstance(){
        return new RetrofitFactory();
    }

    /**
     * Retrofit 默认
     */
    private void initBuilder() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // log的格式
                .addInterceptor(new MLoggerIntercrptor("SmartCommunityHttp", true))
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS)) // 这里你可以根据自己的机型设置同时连接的个数和时间 默认是5个
                .build();

        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpProvider.getHttpIpAdds())
                .client(okHttpClient);
    }


    /**
     * getRetrofit
     *
     * @return
     */
    @Provides
    @Singleton
    public Retrofit getRetrofit() {
        return builder.build();
    }

    private Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    /**
     * 更改 BASE URL
     *
     * @param newApiBaseUrl
     */
    public void changeApiBaseUrl(String newApiBaseUrl) {

        builder = new Retrofit.Builder()
                //.addConverterFactory(FastJsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(newApiBaseUrl)
                .client(okHttpClient);
    }


    /**
     * get 返回 map
     *
     * @param url
     * @param parameters
     * @param subscriber
     */
    public void get(String url, Map parameters, Subscriber<ResponseBody> subscriber) {
        getRetrofit().create(IRetrofitRequest.class)
                .doGet(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    /**
     * post
     *
     * @param url
     * @param parameters
     * @param subscriber
     */
    public void post(String url, Map parameters, Subscriber<ResponseBody> subscriber) {
        getRetrofit().create(IRetrofitRequest.class)
                .doPost(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    /**
     * post json, response json too
     *
     * @param url
     * @param bean
     * @param response
     */
    public void postJson(String url, BaseObservable bean, BaseObserver response) {
        Log.d("postJsonWithHeaders", "url:" + url);
        getRetrofit().create(IRetrofitRequest.class)
                .doPostJson(url, bean)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(response);
    }

    /**
     * 带header 的post, header 为 content boby 为json, accept json
     *
     * @param url
     * @param bean
     * @param response
     */
    public void postJsonWithHeaders(String url, BaseObservable bean, BaseObserver response) {
        getRetrofit().create(IRetrofitRequest.class)
                .doPostJsonWithHeaders(url, bean)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(response);
    }

    /**
     * 带header 的post, header 为 content boby 为json, accept json
     * @param url
     * @param json
     * @param response
     */
    public void postJsonWithHeaders(String url, String json, BaseObserver response) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        getRetrofit().create(IRetrofitRequest.class)
                .doPostJsonWithHeaders(url, body)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(response);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param headers
     * @param filename
     * @param maps     // 携带参数
     */
    public void upLoader(String url, Map<String, String> headers, String filename, Map<String, RequestBody> maps, BaseObserver observer) {
        getRetrofit().create(IRetrofitRequest.class)
                .upLoader(url, headers, filename, maps)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(observer);
    }

    /**
     * 下载
     *
     * @param url
     * @param callBack
     */
    public void downLoader(String url, String path, FileCallBack callBack) {
        getRetrofit().create(IRetrofitRequest.class)
                .downLoader(url)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new DownSubscriber<ResponseBody>(path, callBack));
    }

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public <T> Observable.Transformer<BaseEntity<T>, BaseEntity<T>> transformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private class HandleFuc<T> implements Func1<BaseEntity<T>, BaseEntity<T>> {
        @Override
        public BaseEntity<T> call(BaseEntity<T> response) {
            if (!response.isSuccess()) {
//                throw new RuntimeException(response.getCode() + "" + response.getResult() != null ? response.getResult() : "");
                if (response.getResultCode() != HttpConstants.OPERAT_OK){  // 操作成功
                    throw ExceptionHandle.handleHttpException(response);
                }
            }
            return response;
        }
    }

}
