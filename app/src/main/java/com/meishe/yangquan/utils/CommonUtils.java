package com.meishe.yangquan.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.Collection;

public class CommonUtils {

    public static <T extends Collection> boolean isEmpty(T t) {
        return ((t == null) || (t.isEmpty()));
    }

    /**
     * 判断索引在集合中是否非法
     */
    public static <T extends Collection> boolean isIndexAvailable(int index, T t) {
        if (t == null) {
            return false;
        }
        return ((index >= 0) && (index < t.size()));
    }

    /**
     * 获取圆角背景 不用每次都在drawable新建文件  那个不够灵活也很浪费时间
     */
    public static Drawable getRadiusDrawable(int strokeWidth, int strokeColor, int roundRadius, int fillColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (fillColor != -1) {
            gradientDrawable.setColor(fillColor);
        }
        if (roundRadius > 0) {
            gradientDrawable.setCornerRadius(roundRadius);
        }
        if (strokeWidth > 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor);
        }
        return gradientDrawable;
    }

    /**
     * 只有圆角和填充
     */
    public static Drawable getRadiusDrawable(int roundRadius, int fillColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (fillColor != -1) {
            gradientDrawable.setColor(fillColor);
        }
        if (roundRadius > 0) {
            gradientDrawable.setCornerRadius(roundRadius);
        }
        return gradientDrawable;
    }
}
