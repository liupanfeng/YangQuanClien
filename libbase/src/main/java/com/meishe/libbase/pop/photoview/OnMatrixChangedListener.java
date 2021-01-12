package com.meishe.libbase.pop.photoview;

import android.graphics.RectF;

/**
 * Interface definition for a callback to be invoked when the internal Matrix has changed for
 * this View.
 * 当内部矩阵更改时调用的回调函数的接口定义 这一观点
 *
 */
public interface OnMatrixChangedListener {

    /**
     * Callback for when the Matrix displaying the Drawable has changed. This could be because
     * the View's bounds have changed, or the user has zoomed.
     * 当显示可绘制图形的矩阵发生变化时的回调。这可能是因为
     * 视图的边界已经改变，或者用户已经缩放
     * @param rect - Rectangle displaying the Drawable's new bounds. 显示可绘制的新边界的矩形
     */
    void onMatrixChanged(RectF rect);
}
