package com.meishe.yangquan.utils;

import android.content.Context;
import android.util.TypedValue;

public class AnimationUtil {

    public static int dip2px(Context context, float dpValue) {
        // dip->px
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                context.getResources().getDisplayMetrics());
    }

}
