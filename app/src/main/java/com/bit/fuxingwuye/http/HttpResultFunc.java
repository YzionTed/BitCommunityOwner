package com.bit.fuxingwuye.http;


import android.content.Intent;
import android.widget.Toast;

import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.base.BaseEntity;
import com.bit.fuxingwuye.constant.HttpConstants;
import com.bit.fuxingwuye.utils.ACache;
import com.bit.fuxingwuye.utils.LogUtil;

import rx.functions.Func1;

/**
 * Created by Dell on 2017/7/26.
 * Created time:2017/7/26 15:20
 */

public class HttpResultFunc<T> implements Func1<BaseEntity<T>, BaseEntity<T>> {

    /**
     * @param httpResult
     * @return
     */
    @Override
    public BaseEntity<T> call(BaseEntity<T> httpResult) {
        if (!httpResult.isSuccess()) {
            if (httpResult.getResultCode() != HttpConstants.OPERAT_OK){  // 操作成功
                if(httpResult.getCode().equals("9050001")){
                    ACache aCache=ACache.get(BaseApplication.getInstance());
                    if(aCache!=null){
                        aCache.clear();
                    }
                    BaseApplication.finishAllActivity();
                    Intent intent=new Intent(BaseApplication.getInstance(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    BaseApplication.getInstance().startActivity(intent);
                }else{
                    throw ExceptionHandle.handleHttpException(httpResult);
                }

                //Toast.makeText(BaseApplication.getInstance(),httpResult.getMsg(),Toast.LENGTH_LONG).show();
            }
        }
        return httpResult;
    }
}
