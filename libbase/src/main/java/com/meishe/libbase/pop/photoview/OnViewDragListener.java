package com.meishe.libbase.pop.photoview;

/**
 * Interface definition for a callback to be invoked when the photo is experiencing a drag event
 * 当照片经历拖动事件时调用的回调函数的接口定义
 */
public interface OnViewDragListener {

    /**
     * Callback for when the photo is experiencing a drag event. This cannot be invoked when the
     * user is scaling.
     * 当照片遇到拖放事件时的回调。属性时不能调用
     * 用户正在扩大
     * @param dx The change of the coordinates in the x-direction x方向上坐标的改变量
     * @param dy The change of the coordinates in the y-direction 坐标在y方向上的变化
     */
    void onDrag(float dx, float dy);
}
