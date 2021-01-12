package com.meishe.libbase.pop.photoview;

import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * The type Util.
 * 工具类
 */
class Util {

    /**
     * Check zoom levels.
     * 检查缩放级别
     * @param minZoom the min zoom 最小缩放
     * @param midZoom the mid zoom 中期放大
     * @param maxZoom the max zoom 最大放大
     */
    static void checkZoomLevels(float minZoom, float midZoom,
                                float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException(
                    "Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
        } else if (midZoom >= maxZoom) {
            throw new IllegalArgumentException(
                    "Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
        }
    }

    static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    /**
     * Is supported scale type boolean.
     * 支持缩放
     * @param scaleType the scale type 缩放类型
     * @return the boolean
     */
    static boolean isSupportedScaleType(final ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        switch (scaleType) {
            case MATRIX:
//                throw new IllegalStateException("Matrix scale type is not supported");
                return false;
        }
        return true;
    }

    /**
     * Gets pointer index.
     * 指针索引
     * @param action the action 动作
     * @return the pointer index 指针索引
     */
    static int getPointerIndex(int action) {
        return (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
    }
}
