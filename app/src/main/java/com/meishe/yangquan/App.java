package com.meishe.yangquan;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App app;
    private static Context mContext;
    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public static App getInstance(){
        return app;
    }
}
