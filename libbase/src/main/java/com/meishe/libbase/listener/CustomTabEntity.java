package com.meishe.libbase.listener;

import androidx.annotation.DrawableRes;

/**
 * The interface Custom tab entity.
 * 自定义标签实体接口
 */
public interface CustomTabEntity {
    /**
     * Gets tab title.
     * 获得标签标题
     * @return the tab title
     */
    String getTabTitle();

    /**
     * Gets tab selected icon.
     * 获取标签选中的图标
     * @return the tab selected icon
     */
    @DrawableRes
    int getTabSelectedIcon();

    /**
     * Gets tab unselected icon.
     * 获取标签未被选中的图标
     * @return the tab unselected icon
     */
    @DrawableRes
    int getTabUnselectedIcon();
}