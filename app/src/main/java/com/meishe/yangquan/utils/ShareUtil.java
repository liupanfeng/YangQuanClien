package com.meishe.yangquan.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;




public class ShareUtil {

    public interface OnForwardVideoListener{
        void onSuccess(int code);
        void onError();
    }
    private static final String TAG = "ShareUtil";




    /**
     * 跳转到微信
     */
    public static boolean openWechat(Context context) {
        if (context == null) {
            Log.e(TAG, "openWechat: context is null!");
            return false;
        }

        boolean ret = true;
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToastCenterNoBg(context, "您还没有安装微信!");
            ret = false;
        }
        return ret;
    }

    /**
     * 打开微博客户端
     */
    public static boolean openWeibo(Context context) {
        if (context == null) {
            Log.e(TAG, "openWechat: context is null!");
            return false;
        }

        boolean ret = true;
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("sinaweibo://splash"));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToastCenterNoBg(context, "您还没有安装微博!");
            ret = false;
        }
        return ret;
    }


    public static String getOppositeItemValue(String value){
        if("1".equals(value)){
            return "0";
        }else{
            return "1";
        }
    }

    public static String getOppositeValue(String value){
        if (value == null){
            return "-1";
        }
        if("1".equals(value)){
            return "-1";
        }else{
            return "1";
        }
    }

}
