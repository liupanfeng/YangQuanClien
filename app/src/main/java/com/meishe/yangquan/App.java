package com.meishe.yangquan;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;


import com.bumptech.glide.request.target.ViewTarget;
import com.meishe.yangquan.area.helper.LocationSelectHelper;
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


        ViewTarget.setTagId(R.id.tag_glide);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //友盟初始化 Push推送业务的secret,没有为空
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 打开统计SDK调试模式
        UMConfigure.setLogEnabled(true);
//        PlatformConfig.setWeixin("wxc86baec54fc83b32", "bb1228fe238abe0bd962e5b4d50bf05e");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");

        LocationSelectHelper.getInstance().initJsonData();
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
