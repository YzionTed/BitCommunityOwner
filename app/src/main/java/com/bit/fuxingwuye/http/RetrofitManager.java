package com.bit.fuxingwuye.http;

import android.databinding.BaseObservable;

import com.bit.communityOwner.util.AppUtil;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.constant.AppConstants;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.AppInfo;
import com.bit.fuxingwuye.utils.CommonUtils;
import com.bit.fuxingwuye.utils.LogUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 14:50
 */

public class RetrofitManager {

    private static final int DEFAULT_TIMEOUT = 5;
    ACache aCache;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private static RetrofitManager sInstace;

    /**
     * 私有构造方法
     */
    private RetrofitManager() {
        aCache = ACache.get(BaseApplication.getInstance());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("SKU网络请求", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        builder.addInterceptor(logging);
        // }
        //SKU接口要在Header添加下面的三个参数
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()

                        .addHeader("OS", "2")
                        .addHeader("CLIENT", "1000")
                        .addHeader("OS-VERSION", AppUtil.getSystemVersion())
                        .addHeader("DEVICE-TYPE", AppUtil.getMobileModel())
                        .addHeader("DEVICE-ID", AppInfo.getImei())
                        .addHeader("APP-VERSION", AppInfo.getLocalVersionName(BaseApplication.getInstance()))
                        .addHeader("BIT-TOKEN", "" + aCache.getAsString(HttpConstants.TOKEN))
                        .addHeader("BIT-UID", "" + aCache.getAsString(HttpConstants.USERID))
                        .build();

                return chain.proceed(request);
            }
        });
        File cacheFile = new File(AppConstants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!CommonUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (CommonUtils.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(new MLoggerIntercrptor("SmartCommunityHttp", true));
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        mOkHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()

                //.addConverterFactory(FastJsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpProvider.getHttpIpAdds())
                .client(mOkHttpClient)
                .build();
    }

    /**
     * 创建单例
     */
    public static RetrofitManager getInstace() {
        if (sInstace == null) {
            synchronized (RetrofitManager.class) {
                sInstace = new RetrofitManager();
            }
        }
        return sInstace;
    }

    private Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }

    /**
     * 更改请求头
     *
     * @param newApiHeaders
     */
    public void addApiHeader(Map<String, String> newApiHeaders) {
        mOkHttpClient.newBuilder().addInterceptor(new MLoggerIntercrptor("SmartCommunityHttp", true, newApiHeaders)).build();
        getRetrofit().newBuilder().client(mOkHttpClient).build();
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

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
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
                if (response.getResultCode() != HttpConstants.OPERAT_OK) {  // 操作成功
                    throw ExceptionHandle.handleHttpException(response);
                }
            }
            return response;
        }
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
