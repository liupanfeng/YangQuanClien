package com.meishe.yangquan.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.meishe.yangquan.App;


public class ToastUtil {

    // 文本的toast
    private static Toast mTextToast;
    /**
     * 显示Toast
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (mTextToast == null) {
            mTextToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mTextToast.setText(msg);
        }
        mTextToast.show();
    }


    /**
     * 显示Toast
     *
     * @param msg
     */
    public static void showToast(String msg) {
        Context context= App.getContext();
        if (context == null){
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (mTextToast == null) {
            mTextToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mTextToast.setText(msg);
        }
        mTextToast.show();
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public static void showToast(Context context,int msg) {
        String str = context.getResources().getString(msg);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        showToast(context,str);
    }

    /**
     * 居中透明背景显示Toast
     *
     * @param context
     * @param s
     */
    public static void showToastCenterNoBg(Context context, String s) {
        if(context == null){
            return;
        }
        Toast toast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        toast.setText(s);
        toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.getView().setBackgroundResource(R.color.colorTranslucent);
        int tvToastId = Resources.getSystem().getIdentifier("message", "id", "android");
        TextView tvToast = ((TextView) toast.getView().findViewById(tvToastId));
        tvToast.setTextColor(Color.parseColor("#CCFFFFFF"));
        tvToast.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tvToast.setGravity(Gravity.CENTER);
        toast.show();
    }
}
