package com.meishe.yangquan;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    private Typeface typeface;
    private static App app;
    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //友盟初始化 Push推送业务的secret,没有为空
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 打开统计SDK调试模式
        UMConfigure.setLogEnabled(true);
    }


    public static App getInstance(){
        return app;
    }


    public Typeface getFont(boolean bold){
        if(typeface == null) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/xmlt.ttf");
        }
        return typeface;
    }

}
