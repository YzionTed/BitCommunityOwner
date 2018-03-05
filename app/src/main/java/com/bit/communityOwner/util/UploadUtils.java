package com.BIT.communityOwner.util;

/**
 * Created by hjw on 17/3/22.
 */


import android.util.Log;

import com.BIT.communityOwner.model.OssToken;
import com.BIT.fuxingwuye.base.BaseApplication;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * 阿里云上传
 */
public class UploadUtils {


    private static final String TAG = UploadUtils.class.getSimpleName();

    /**
     * 文件上传阿里云不带进度回调
     *
     * @param data
     * @param filePath
     * @return
     */
    public static String uploadFileToAliYun(OssToken data, String filePath) {
        try {
            String endpoint = data.getEndPoint();
            data.setName("ap2" + /*SPUtil.get(MyApplication.getInstance(), AppConfig.id, "") +*/ "_" + System.currentTimeMillis() + ".jpg");
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken());
            OSS oss = new OSSClient(BaseApplication.getInstance(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            PutObjectResult putResult = oss.putObject(put);
//            Log.d("okhttp", "http://bit-app.oss-cn-beijing.aliyuncs.com/"+data.getName());
            String url = oss.presignConstrainedObjectURL(data.getBucket(), data.getName(), 30 * 60);
            Log.d(TAG, url);
            data.setPath(url);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件上传阿里云带进度回调
     *
     * @param data
     * @param filePath
     * @param progressCallback
     * @return
     */
    public static String uploadFileToAliYun(OssToken data, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
        try {
            String endpoint = data.getEndPoint();
            data.setName("ap2" + /*SPUtil.get(MyApplication.getInstance(), AppConfig.id, "") +*/ "_" + System.currentTimeMillis() + ".jpg");
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(data
                    .getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken());
            OSS oss = new OSSClient(BaseApplication.getInstance(), endpoint, credentialProvider);
            PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName
                    (), filePath);
            put.setProgressCallback(progressCallback);
            String url = oss.presignConstrainedObjectURL(data.getBucket(), data.getName(), 30 * 60);
            LogUtil.d(TAG, url);
            data.setPath(url);
            PutObjectResult putResult = oss.putObject(put);
            return data.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
