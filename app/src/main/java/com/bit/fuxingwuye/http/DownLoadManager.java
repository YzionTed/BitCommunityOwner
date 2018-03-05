package com.bit.fuxingwuye.http;

import android.os.Handler;
import android.os.Looper;

import com.bit.fuxingwuye.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * SmartCommunity-com.bit.fuxingwuye.http
 * 作者： YanwuTang
 * 时间： 2017/7/7.
 */

public class DownLoadManager {
    private static final String TAG = "DownLoadManager";
    private static DownLoadManager sInstance;

    private DownLoadManager(){}
    public static synchronized DownLoadManager getInstance() {
        if (sInstance == null) {
            synchronized (DownLoadManager.class) {
                sInstance = new DownLoadManager();
            }
        }
        return sInstance;
    }

    public boolean  writeResponseBodyToDisk(ResponseBody body, final String path, final FileCallBack callBack) {
        LogUtil.d(TAG, "contentType:>>>>"+ body.contentType().toString());
        LogUtil.d(TAG, "path:>>>>"+ path);
        Handler handler;
        try {
            File futureStudioIconFile = new File(path);

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                LogUtil.d(TAG, "file length: "+ fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    LogUtil.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    if (callBack != null) {
                        handler = new Handler(Looper.getMainLooper());
                        final long finalFileSizeDownloaded = fileSizeDownloaded;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(finalFileSizeDownloaded);
                            }
                        });

                    }
                }

                outputStream.flush();
                LogUtil.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                if (callBack != null) {
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucess(path, fileSize);

                        }
                    });
                    LogUtil.d(TAG, "file downloaded: " + fileSizeDownloaded + " of " + fileSize);
                }

                return true;
            } catch (IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            if (callBack != null) {
                callBack.onError(e);
            }
            return false;
        }
    }
}
