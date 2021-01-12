package com.meishe.libbase.adpater.listener;

import android.view.View;

import com.meishe.libbase.adpater.BaseQuickAdapter;


/**
 * <p>
 * <p>
 * A convenience class to extend when you only want to OnItemClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * 当您只想对子集使用OnItemClickListener时，可以扩展这个方便的类
 * 所有的SimpleClickListener。方法中的所有方法
 * {@link SimpleClickListener}
 */
public abstract class OnItemClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemClick(adapter, view, position);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public abstract void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position);
}
