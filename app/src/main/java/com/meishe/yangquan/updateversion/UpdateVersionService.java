package com.meishe.yangquan.updateversion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;


import androidx.core.app.NotificationCompat;

import com.meishe.yangquan.R;
import com.meishe.yangquan.http.HttpAssetsDownload;
import com.meishe.yangquan.utils.Util;

import java.io.File;

public class UpdateVersionService extends Service {

    private static final String TAG = "UpdateVersionService";
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    private int mNotificationId = 0;

    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        String id = "channel_001";
        String name = "name";
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotification = new Notification.Builder(getApplicationContext())
                    .setChannelId(id)
                    .setSmallIcon(R.mipmap.logo)
                    .setTicker("开始下载")
                    .setWhen(System.currentTimeMillis())
                    .setContent(new RemoteViews(getPackageName(), R.layout.update_version_notification))
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setChannelId(id)
                    .setSmallIcon(R.mipmap.logo)
                    .setTicker("开始下载")
                    .setWhen(System.currentTimeMillis())
                    .setContent(new RemoteViews(getPackageName(), R.layout.update_version_notification));
            mNotification = notificationBuilder.build();
        }

        mNotificationManager.notify(0, mNotification);

        mHandler = new Handler();

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        String url = intent.getStringExtra("downloadUrl");
        String name = intent.getStringExtra("apkName");
        Log.d(TAG, "downloadUrl: " + url);
        Log.d(TAG, "apkName: " + name);

        downloadApk(url, name);

        mNotificationManager.notify(mNotificationId, mNotification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int lastProgress = -1;
    private void downloadApk(String url, String apkName){
        String path = Util.getDownloadApkFile(apkName);
        Log.d(TAG, "downloadApk: " + path);
        HttpAssetsDownload.shareInstance(getApplicationContext()).downloadAssets(url, path
                , new HttpAssetsDownload.DownloadListener() {
                    @Override
                    public void onProgress(final int progress) {
                        Log.d(TAG, "onProgress: " + progress);

                        if(lastProgress != progress){
                            lastProgress = progress;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mNotification.contentView.setTextViewText(R.id.msg, "正在下载：羊圈");
                                    mNotification.contentView.setTextViewText(R.id.bartext,  progress + "%");
                                    mNotification.contentView.setProgressBar(R.id.progressBar, 100, progress, false);
                                    mNotificationManager.notify(mNotificationId, mNotification);
                                }
                            });
                        }
                    }

                    @Override
                    public void onSuccess(boolean success, final String downloadPath) {
                        Log.d(TAG, "onSuccess: ");
                        mNotification.contentView.setTextViewText(R.id.msg, "下载完成!");
                        mNotification.contentView.setProgressBar(R.id.progressBar, 100, 100, false);
                        mNotificationManager.notify(mNotificationId, mNotification);

                        UpdateVersionUtil.collapseStatusBar(UpdateVersionService.this);
                        mNotificationManager.cancel(mNotificationId);

                        File apkFile = new File(downloadPath);
                        Log.d(TAG, "onSuccess: " + downloadPath);
                        Intent installIntent = ApkUtils.getInstallIntent(apkFile, getApplicationContext());
                        startActivity(installIntent);

                        stopSelf();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.d(TAG, "onFailed: ");
                        mNotification.contentView.setTextViewText(R.id.msg, "网络异常！请检查网络设置！");
                        mNotificationManager.notify(0, mNotification);
                    }

                    @Override
                    public void onNetAvailable(boolean isNetAvailabe) {
                        Log.d(TAG, "onNetAvailable: ");
                        mNotification.contentView.setTextViewText(R.id.msg, "网络不可用，请检查！");
                        mNotificationManager.notify(mNotificationId, mNotification);
                    }
                });
    }
}
