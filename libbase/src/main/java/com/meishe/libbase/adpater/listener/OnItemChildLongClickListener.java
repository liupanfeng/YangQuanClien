package com.meishe.libbase.adpater.listener;

import android.view.View;

import com.meishe.libbase.adpater.BaseQuickAdapter;


/**
 * A convenience class to extend when you only want to OnItemChildLongClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * 当您只想对子集使用OnItemChildLongClickListener时，可以扩展这个方便的类
 * 所有的SimpleClickListener。方法中的所有方法
 * {@link SimpleClickListener}
 */
public abstract class OnItemChildLongClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemChildLongClick(adapter, view, position);
    }

    /**
     * On simple item child long click.
     * 在简单的项目子长点击
     * @param adapter  the adapter 适配器
     * @param view     the view 视图
     * @param position the position 位置
     */
    public abstract void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position);
}
