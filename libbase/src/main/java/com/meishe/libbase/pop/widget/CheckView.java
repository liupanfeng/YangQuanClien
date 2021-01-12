package com.meishe.libbase.pop.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 对勾View
 * To check the View
 */
public class CheckView extends View {
    Paint paint;
    /**
     * The Color.
     * 颜色
     */
    int color = Color.TRANSPARENT;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(XPopupUtils.dp2px(context, 2));
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 设置对勾View
     * Set the checkbox View
     * @param color the color 颜色
     */
    public void setColor(int color){
        this.color = color;
        paint.setColor(color);
        postInvalidate();
    }

    /**
     * The Path.
     * 路径
     */
    Path path = new Path();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(color== Color.TRANSPARENT)return;
        /*
        * first part
        * 第一部分
        * */
        path.moveTo(getMeasuredWidth()/4, getMeasuredHeight()/2);
        path.lineTo(getMeasuredWidth()/2 , getMeasuredHeight()*3/4);
        /*
        * second part
        * 第二部
        * */
        path.lineTo(getMeasuredWidth(), getMeasuredHeight()/4);
        canvas.drawPath(path, paint);
    }
}
