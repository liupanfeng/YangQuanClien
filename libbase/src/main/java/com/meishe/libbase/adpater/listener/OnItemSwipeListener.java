package com.meishe.libbase.adpater.listener;

import android.graphics.Canvas;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目滑动监听器接口
 * Item slide listener interface
 */
public interface OnItemSwipeListener {

    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos);


    void clearView(RecyclerView.ViewHolder viewHolder, int pos);


    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos);


    void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive);
}
