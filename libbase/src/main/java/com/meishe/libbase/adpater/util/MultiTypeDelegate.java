package com.meishe.libbase.adpater.util;

import android.util.SparseIntArray;

import androidx.annotation.LayoutRes;

import java.util.List;

import static com.meishe.libbase.adpater.BaseMultiItemQuickAdapter.TYPE_NOT_FOUND;


/**
 * help you to achieve multi type easily
 * 帮助您轻松实现多类型
 * <p>
 * more information: https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/968
 *
 * @param <T> the type parameter
 */
public abstract class MultiTypeDelegate<T> {

    private static final int DEFAULT_VIEW_TYPE = -0xff;
    private SparseIntArray layouts;
    private boolean autoMode, selfMode;

    public MultiTypeDelegate(SparseIntArray layouts) {
        this.layouts = layouts;
    }

    public MultiTypeDelegate() {
    }

    public final int getDefItemViewType(List<T> data, int position) {
        T item = data.get(position);
        return item != null ? getItemType(item) : DEFAULT_VIEW_TYPE;
    }

    /**
     * get the item type from specific entity.
     * 从特定实体获取项目类型
     * @param t entity 实体
     * @return item type
     */
    protected abstract int getItemType(T t);

    public final int getLayoutId(int viewType) {
        return this.layouts.get(viewType, TYPE_NOT_FOUND);
    }

    private void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(type, layoutResId);
    }

    /**
     * auto increase type vale, start from 0.
     * 自动增加类型，从0开始
     * @param layoutResIds layout id arrays 布局id数组
     * @return MultiTypeDelegate multi type delegate 多类型委托
     */
    public MultiTypeDelegate registerItemTypeAutoIncrease(@LayoutRes int... layoutResIds) {
        autoMode = true;
        checkMode(selfMode);
        for (int i = 0; i < layoutResIds.length; i++) {
            addItemType(i, layoutResIds[i]);
        }
        return this;
    }

    /**
     * set your own type one by one.
     * 一个一个地设置你自己的类型
     * @param type        type value 类型值
     * @param layoutResId layout id 布局id
     * @return MultiTypeDelegate multi type delegate 多类型委托
     */
    public MultiTypeDelegate registerItemType(int type, @LayoutRes int layoutResId) {
        selfMode = true;
        checkMode(autoMode);
        addItemType(type, layoutResId);
        return this;
    }

    private void checkMode(boolean mode) {
        if (mode) {
            throw new IllegalArgumentException("Don't mess two register mode");
        }
    }
}
