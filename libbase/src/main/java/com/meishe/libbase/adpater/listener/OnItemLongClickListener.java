package com.meishe.libbase.adpater.listener;

import android.view.View;

import com.meishe.libbase.adpater.BaseQuickAdapter;


/**
 * create by: allen on 16/8/3.
 * 长按条目监听
 * Listen by item
 */
public abstract class OnItemLongClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemLongClick(adapter, view, position);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    /**
     * On simple item long click.
     * 在简单的项目长点击
     * @param adapter  the adapter
     * @param view     the view
     * @param position the position
     */
    public abstract void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position);
}
