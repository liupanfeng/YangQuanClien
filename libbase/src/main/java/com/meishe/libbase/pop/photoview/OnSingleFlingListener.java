package com.meishe.libbase.pop.photoview;

import android.view.MotionEvent;

/**
 * A callback to be invoked when the ImageView is flung with a single touch
 * 当ImageView通过单个触摸被抛出时要调用的回调
 */
public interface OnSingleFlingListener {

    /**
     * A callback to receive where the user flings on a ImageView. You will receive a callback if
     * the user flings anywhere on the view.
     * 接收用户在ImageView上投放的位置的回调。如果，您将收到一个回调
     * 用户将视图中的任意位置抛入
     * @param e1        MotionEvent the user first touch. MotionEvent用户第一次触摸
     * @param e2        MotionEvent the user last touch. MotionEvent用户最后一次触摸
     * @param velocityX distance of user's horizontal fling. 用户水平投掷的距离
     * @param velocityY distance of user's vertical fling. 用户垂直抛出的距离
     * @return the boolean
     */
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
