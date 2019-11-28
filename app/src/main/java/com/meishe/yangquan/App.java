package com.meishe.yangquan;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

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
