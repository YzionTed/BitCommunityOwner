package com.bit.fuxingwuye.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.communityOwner.util.StringUtils;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.constant.HttpConstants;

import cn.qqtheme.framework.AppConfig;

/**
 * Created by DELL60 on 2018/3/5.
 */

public class OssManager {
    private OssToken uploadInfo;
    private OSS oss;

    public static OssManager getInstance() {
        return OssInstance.instance;
    }

    private static class OssInstance {
        private static final OssManager instance = new OssManager();
    }

    private OssManager() {
    }

    /**
     * 初始化
     **/
    public OssManager init(Context context, OssToken uploadInfo) {
        if (oss == null) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(uploadInfo.getAccessKeyId(), uploadInfo.getAccessKeySecret(), uploadInfo.getSecurityToken());
            oss = new OSSClient(context, uploadInfo.getEndPoint(), credentialProvider);
        }
        this.uploadInfo = uploadInfo;
        return OssInstance.instance;
    }

    public String getUrl(String url) {
        if (oss != null) {
            try {
                return oss.presignConstrainedObjectURL(StringUtils.getBucket(url), url, 30 * 60);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 文件异步上传阿里云
     *
     * @param data
     * @param filePath
     * @param callback
     * @return
     */
    public String uploadFileToAliYun(final OssToken data, String filePath, OSSCompletedCallback callback) {
        try {
            if (oss != null) {
                data.setName("ap1" + SPUtils.getInstance().getString(HttpConstants.USERID) + "_" + data.getBucket() + "_" + TimeUtils.getCurrentTime() + ".jpg");
                PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                        (), filePath);
                OSSAsyncTask task = oss.asyncPutObject(put, callback);
//                String url = oss.presignConstrainedObjectURL(data.getBucket(), data.getName(),30 * 60);
                Log.d("okhttp", getUrl(filePath));
                data.setPath(getUrl(filePath));
            }
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void refreshToken(){
        if (uploadInfo == null || TimeUtils.isExpiration(uploadInfo.getExpiration())) {
            initOssToken();
        }
    }

    private void initOssToken() {
        Api.ossToken(new ResponseCallBack<OssToken>() {
            @Override
            public void onSuccess(OssToken data) {
                super.onSuccess(data);
                if (data!=null){
                    oss = null;
                    init(BaseApplication.getInstance(), data);
                }
            }

            @Override
            public void onFailure(ServiceException e) {
                super.onFailure(e);
            }
        });
    }
}
