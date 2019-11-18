package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lpf on 2016/9/16.
 */
public interface IDecorationHelper {
    void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position);
}
