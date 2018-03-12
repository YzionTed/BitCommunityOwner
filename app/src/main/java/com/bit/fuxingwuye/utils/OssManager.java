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
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bit.communityOwner.model.OssToken;
import com.bit.communityOwner.net.Api;
import com.bit.communityOwner.net.ResponseCallBack;
import com.bit.communityOwner.net.ServiceException;
import com.bit.communityOwner.util.StringUtils;
import com.bit.fuxingwuye.base.BaseApplication;
import com.bit.fuxingwuye.constant.HttpConstants;

import java.util.List;

import cn.qqtheme.framework.AppConfig;

/**
 * Created by DELL60 on 2018/3/5.
 */

public class OssManager {
    private OssToken uploadInfo;
    private OSS oss;
    ACache aCache;
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
            return data.getName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件异步批量上传阿里云
     * @param data
     * @param filePath 文件本地路径
     * @param finalPath 返回的文件名列表
     * @param uploadFinishListener 上传完成回调
     */
    public void uploadFileToAliYun(final OssToken data, final List<String> filePath, final List<String> finalPath, final UploadFinishListener uploadFinishListener) {
        try {
            if (filePath!=null&&filePath.size()<=0){
                uploadFinishListener.uploadFinish(finalPath);
                return;
            }
            if (oss != null) {
                data.setName("ap1" + SPUtils.getInstance().getString(HttpConstants.USERID) + "_" + data.getBucket() + "_" + TimeUtils.getCurrentTime() + ".jpg");
                PutObjectRequest put = new PutObjectRequest(data.getBucket() /*+ "-trans"*/, data.getName(), filePath.get(0));
                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        filePath.remove(0);
                        finalPath.add(data.getName());
                        uploadFileToAliYun(data,filePath,finalPath,uploadFinishListener);
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, com.alibaba.sdk.android.oss.ServiceException e1) {

                    }
                });
                Log.d("okhttp", getUrl(filePath.get(0)));
                data.setPath(getUrl(filePath.get(0)));
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void refreshToken(){
        aCache = ACache.get(BaseApplication.getInstance());
        if ((uploadInfo == null || TimeUtils.isExpiration(uploadInfo.getExpiration()))&&(aCache.getAsString(HttpConstants.TOKEN) != null && !"".equals(aCache.getAsString(HttpConstants.TOKEN)))) {
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

    public interface UploadFinishListener{
        void uploadFinish(List<String> finalName);
    }
}
