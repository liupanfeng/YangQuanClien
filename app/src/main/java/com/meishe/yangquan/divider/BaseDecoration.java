package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meishe.yangquan.adapter.BaseRecyclerAdapter;


public  class BaseDecoration extends RecyclerView.ItemDecoration{
    protected int mSpace;
    protected BaseRecyclerAdapter mAdapter;

    public BaseDecoration(BaseRecyclerAdapter mAdapter, int mSpace) {
        this.mAdapter = mAdapter;
        this.mSpace = mSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        handleItemOffsets(outRect, view, parent, state, position);
    }

    protected  void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position){

    }
}
