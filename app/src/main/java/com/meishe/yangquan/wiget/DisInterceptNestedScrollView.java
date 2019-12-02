package com.meishe.yangquan.wiget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by stefan on 2017/5/26.
 * Func:用于子类 防止父类拦截子类的事件
 */
public class DisInterceptNestedScrollView extends NestedScrollView {

    float x2 = 0;
    float y2 = 0;
    float x1 = 0;
    float y1 = 0;

    public DisInterceptNestedScrollView(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }


    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "1111You down layout");
                x2 = event.getX();
                y2 = event.getY();
                if(y1 - y2 > 50) {
                    requestDisallowInterceptTouchEvent(true);
                } else if(y2 - y1 > 50) {
                    requestDisallowInterceptTouchEvent(true);
                } else if(x1 - x2 > 50) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if(x2 - x1 > 50) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                //requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e("TAG", "333You down layout");
                requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


}
