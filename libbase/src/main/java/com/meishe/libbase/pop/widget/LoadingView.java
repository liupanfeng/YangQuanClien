package com.meishe.libbase.pop.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 加载View
 * Load the View
 */
public class LoadingView extends View {
    private Paint paint;
    private float radius;
    private float radiusOffset;
    /*
    * 不是固定不变的，当width为30dp时，它为2dp，当宽度变大，这个也会相应的变大
    * It's not fixed. When width is 30dp, it's 2dp, and as the width gets larger, this will get larger
    * */
    private float stokeWidth = 2f;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private int startColor = Color.parseColor("#CCCCCC");
    private int endColor = Color.parseColor("#333333");
    /**
     * The Line count.
     * 行数
     * 共12条线
     * A total of 12 lines
     */
    int lineCount = 12;
    /**
     * The Avg angle.
     * Avg的角度
     */
    float avgAngle = 360f / lineCount;
    /**
     * The Time.
     * 重复次数
     */
    int time = 0;
    /**
     * The Center x.
     * 中心x
     */
    float centerX,
    /**
     * The Center y.
     * 中心x，y
     */
    centerY;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stokeWidth = XPopupUtils.dp2px(context, stokeWidth);
        paint.setStrokeWidth(stokeWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = getMeasuredWidth() / 2;
        radiusOffset = radius / 2.5f;

        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;

        stokeWidth *= getMeasuredWidth() * 1f / XPopupUtils.dp2px(getContext(), 30);
        paint.setStrokeWidth(stokeWidth);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        // 1 2 3 4 5
        // 2 3 4 5 1
        // 3 4 5 1 2
        // ...
        for (int i = lineCount - 1; i >= 0; i--) {
            int temp = Math.abs(i + time) % lineCount;
            float fraction = (temp + 1) * 1f / lineCount;
            int color = (int) argbEvaluator.evaluate(fraction, startColor, endColor);
            paint.setColor(color);

            float startX = centerX + radiusOffset;
            float endX = startX + radius / 3f;
            canvas.drawLine(startX, centerY, endX, centerY, paint);
            /*
            * 线的两端画个点，看着圆滑
            * Draw a point at each end of the line and look round
            * */
            canvas.drawCircle(startX, centerY, stokeWidth / 2, paint);
            canvas.drawCircle(endX, centerY, stokeWidth / 2, paint);
            canvas.rotate(avgAngle, centerX, centerY);
        }
        postDelayed(increaseTask, 80);
    }

    private Runnable increaseTask = new Runnable() {
        @Override
        public void run() {
            time++;
            invalidate();
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(increaseTask);
    }
}
