package com.meishe.yangquan.wiget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;
import com.minminaya.abs.GeneralRoundViewImpl;
import com.minminaya.abs.IRoundView;

public class CustomRoundView extends RelativeLayout implements IRoundView {

    private GeneralRoundViewImpl generalRoundViewImpl;

    public CustomRoundView(Context context) {
        super(context);
    }

    public CustomRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(this, context, attrs);
    }

    private void init(CustomRoundView customRoundView, Context context, AttributeSet attrs) {
        generalRoundViewImpl = new GeneralRoundViewImpl(customRoundView,
                context,
                attrs,
                R.styleable.CustomRoundView,
                R.styleable.CustomRoundView_corner_radius);
    }

    public CustomRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(this, context, attrs);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        generalRoundViewImpl.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setCornerRadius(float cornerRadius) {
        generalRoundViewImpl.setCornerRadius(cornerRadius);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        generalRoundViewImpl.beforeDispatchDraw(canvas);
        super.dispatchDraw(canvas);
        generalRoundViewImpl.afterDispatchDraw(canvas);
    }

}
