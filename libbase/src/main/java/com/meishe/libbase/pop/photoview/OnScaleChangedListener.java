package com.meishe.libbase.pop.photoview;


/**
 * Interface definition for callback to be invoked when attached ImageView scale changes
 * 当附加的ImageView缩放变化时调用的回调的接口定义
 */
public interface OnScaleChangedListener {

    /**
     * Callback for when the scale changes
     * 当比例变化时的回调
     * @param scaleFactor the scale factor (less than 1 for zoom out, greater than 1 for zoom in) 比例因子(缩小小于1，放大大于1)
     * @param focusX      focal point X position 焦点X位置
     * @param focusY      focal point Y position 焦点Y位置
     */
    void onScaleChange(float scaleFactor, float focusX, float focusY);
}
