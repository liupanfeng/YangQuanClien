package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.adapter.BaseRecyclerAdapter;


public  class BaseItemDecoration extends RecyclerView.ItemDecoration {

    protected int mSpace;
    protected BaseRecyclerAdapter mAdapter;

    public BaseItemDecoration(BaseRecyclerAdapter mAdapter, int mSpace) {
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
