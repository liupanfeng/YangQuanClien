package com.meishe.yangquan.http;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpAssetsDownload {
    private static final String TAG = "AssetsDownload ";
    OkHttpClient m_httpClient = null;
    private static HttpAssetsDownload m_instance = null;
    Context m_context;
    private Call call;
    private boolean isFromUser = false;

    public static HttpAssetsDownload shareInstance(Context context) {
        if (m_instance == null)
            m_instance = new HttpAssetsDownload(context);
        return m_instance;
    }

    private HttpAssetsDownload(Context context) {
        m_httpClient = new OkHttpClient();
        m_context = context;
    }

    //下载文件
    public void downloadAssets(String srcFileUrl, String desFilePath, DownloadListener downloadListener) {
        if (!CheckNetwork.checkNetWork(m_context)) {//网络不可用
            downloadListener.onNetAvailable(false);
            return;
        }
        final DownloadListener localDownloadListener = downloadListener;
        final String desFileUrl = desFilePath;
        Request httpRequenst = null;
        try {
            Request.Builder requestBuilder = new Request.Builder().url(srcFileUrl);
            if (requestBuilder != null){
                httpRequenst = requestBuilder.build();
            }
        } catch (Exception e) {
            if (localDownloadListener != null) {
                localDownloadListener.onFailed(e);
            }
            return;
        }

        call = m_httpClient.newCall(httpRequenst);
        if (call == null){
            return;
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                File localFile = new File(desFileUrl);
                if (localFile.exists()) {
                    boolean isDelete = localFile.delete();
                    if (!isDelete) {
                        Log.d(TAG,"fail to delete file");
                    }
                }
                if (localDownloadListener != null) {
                    localDownloadListener.onFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    File file = new File(desFileUrl);
                    RandomAccessFile accessFile = null;
                    InputStream httpInputStream = null;
                    try {
                        accessFile = new RandomAccessFile(file, "rw");
                        long contentLength = response.body().contentLength();
                        httpInputStream = response.body().byteStream();
                        byte[] byteStreamArray = new byte[4096];
                        int totalLen = 0;
                        int len = 0;
                        while ((len = httpInputStream.read(byteStreamArray)) != -1) {
                            totalLen += len;
                            accessFile.write(byteStreamArray, 0, len);
                            //计算百分比
                            int progress = (int) (totalLen * 100.0 / contentLength);
                            if (localDownloadListener != null) {
                                localDownloadListener.onProgress(progress);
                            }
                        }
                        httpInputStream.close();
                        response.body().close();
                        accessFile.close();
                        if (localDownloadListener != null) {
                            localDownloadListener.onSuccess(true, desFileUrl);
                        }
                    } catch (FileNotFoundException e) {
                        if (file.exists()) {
                            boolean isDelete = file.delete();
                            if (isDelete){
                                Log.d(TAG,"fail to delete file");
                            }
                        }
                        Log.e(TAG,"fail to find file" ,e);
                        if (localDownloadListener != null) {
                            localDownloadListener.onFailed(e);
                        }
                    } catch (IOException e) {
                        if (file.exists()) {
                            boolean isDelete = file.delete();
                            if (!isDelete) {
                                Log.d(TAG,"fail to delete file");
                            }
                        }
                        Log.e(TAG,"fail to IOException" ,e);
                        if (localDownloadListener != null) {
                            if (!isFromUser)
                                localDownloadListener.onFailed(e);
                        }
                    } finally {
                        try {
                            response.body().close();
                            if (httpInputStream != null)
                                httpInputStream.close();
                            if (accessFile != null)
                                accessFile.close();
                        } catch (IOException e) {
                            Log.e(TAG,"fail to close():" ,e);
                            if (localDownloadListener != null) {
                                localDownloadListener.onFailed(e);
                            }
                        }
                    }

                } else {
                    Log.e(TAG, "服务器端错误");
                }
            }
        });
    }

    public void cancle() {
        if(call!=null) {
            if (call.isExecuted()) {
                call.cancel();
                isFromUser = true;
            }
        }
    }

    public interface DownloadListener {
        //下载进度
        void onProgress(int progress);

        //下载成功
        void onSuccess(boolean success, String downloadPath);

        //下载异常
        void onFailed(Exception e);

        //网络是否可用
        void onNetAvailable(boolean isNetAvailabe);
    }
}
