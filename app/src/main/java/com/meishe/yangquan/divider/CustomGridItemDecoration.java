package com.meishe.yangquan.divider;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/12/20 16:19
 */
public class CustomGridItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    private int color;

    private Paint mPaint;

    public CustomGridItemDecoration() {
        this(1);
    }


    /**
     * 自定义宽度的透明分割线
     *
     * @param space 指定宽度
     */
    public CustomGridItemDecoration(int space) {
        this(space, Color.TRANSPARENT);
    }


    /**
     * 自定义宽度，并指定颜色的分割线
     *
     * @param space 指定宽度
     * @param color 指定颜色
     */

    public CustomGridItemDecoration(int space, int color) {
        this.space = space;
        this.color = color;
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        /*开启抗锯齿*/
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        /*只绘制图形内容*/
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(space);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        int childSize = parent.getChildCount();
        int span = manager.getSpanCount();
        //为了Item大小均匀，将设定分割线平均分给左右两边Item各一半
        int offset = space / 2;
        //得到View的位置
        int childPosition = parent.getChildAdapterPosition(view);
        //第一排，顶部
        if (childPosition  < span) {
            //最左边的
            if (childPosition  % span == 0) {
                outRect.set(space, space, offset, 0);
                //最右边的
            } else if (childPosition  % span == span - 1) {
                outRect.set(offset, space, space, 0);
                //中间的
            } else {
                outRect.set(offset, space, offset, 0);
            }
        } else {
            //上下的分割线，就从第二排开始
            //最左边的
            if (childPosition  % span == 0) {
                outRect.set(space, space, offset, 0);
                //最右边的
            } else if (childPosition  % span == span - 1) {
                outRect.set(offset, space, space, 0);
                //中间的
            } else {
                outRect.set(offset, space, offset, 0);
            }
        }

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
