package com.meishe.libbase.listener;

/**
 * The interface On tab select listener.
 * 标签选择监听器
 */
public interface OnTabSelectListener {
    /**
     * On tab select.
     * 标签选择
     * @param position the position 位置
     */
    void onTabSelect(int position);

    /**
     * On tab reselect.
     *  标签重新选择
     * @param position the position 位置
     */
    void onTabReselect(int position);
}