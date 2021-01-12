package com.meishe.libbase.adpater.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 关于项目拖动监听接口
 * About the project drag listening interface
 */
public interface OnItemDragListener {

    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
