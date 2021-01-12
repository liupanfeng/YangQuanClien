package com.meishe.libbase.pop.interfaces;

/**
 * The interface On drag change listener.
 * 拖动改变监听器接口
 * Drag to change the listener interface
 */
public interface OnDragChangeListener {
    /**
     * On release.
     * 释放
     */
    void onRelease();

    /**
     * On drag change.
     *  拖动改变
     * @param dy       the dy  dy
     * @param scale    the scale 缩放
     * @param fraction the fraction 部分
     */
    void onDragChange(int dy, float scale, float fraction);
}
