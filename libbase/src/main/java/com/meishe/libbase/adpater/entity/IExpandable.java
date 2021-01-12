package com.meishe.libbase.adpater.entity;

import java.util.List;

/**
 * implement the interface if the item is expandable
 * 如果项是可展开的，则实现该接口
 * @param <T> the type parameter
 */
public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     * 获取该物品的级别。级别从0开始。
     * *如果你不关心级别，只要返回一个负数
     * @return the level
     */
    int getLevel();
}
