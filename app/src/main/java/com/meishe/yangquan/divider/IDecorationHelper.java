package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lpf on 2016/9/16.
 */
public interface IDecorationHelper {
    void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position);
}
