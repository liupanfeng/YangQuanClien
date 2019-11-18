package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meishe.yangquan.adapter.BaseRecyclerAdapter;

/**
 * Created by lpf on 2016/9/16.
 */
public class InnerDivideDecoration extends BaseDecoration {

    IDecorationHelper mHelper;
    public InnerDivideDecoration(int space, BaseRecyclerAdapter adapter, IDecorationHelper helper) {
        super(adapter, space);
        mHelper = helper;
    }

    @Override
    protected void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position) {
        if (mHelper != null)
            mHelper.handleItemOffsets(outRect, view, parent, state, position);
    }
}
