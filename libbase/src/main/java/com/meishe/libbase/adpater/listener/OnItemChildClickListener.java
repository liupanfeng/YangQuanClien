package com.meishe.libbase.adpater.listener;

import android.view.View;

import com.meishe.libbase.adpater.BaseQuickAdapter;


/**
 * Created by AllenCoder on 2016/8/03.
 * A convenience class to extend when you only want to OnItemChildClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * 当您只想对一个子集使用OnItemChildClickListener时，可以扩展这个方便的类
 * 所有的SimpleClickListener。方法中的所有方法
 * {@link SimpleClickListener}
 */
public abstract class OnItemChildClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemChildClick(adapter, view, position);
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * On simple item child click.
     * 在简单的项目子单击
     * @param adapter  the adapter
     * @param view     the view
     * @param position the position
     */
    public abstract void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position);
}
