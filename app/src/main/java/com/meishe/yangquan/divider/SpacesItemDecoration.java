package com.meishe.yangquan.divider;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private boolean isVertical;
    public SpacesItemDecoration(int space, boolean isVertical) {
        this.space = space;
        this.isVertical = isVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        if(isVertical){
            outRect.top = space;
            outRect.bottom = space;
        }else{
            outRect.left = space;
            outRect.right = space;
        }
    }
}
