package com.meishe.libbase.pop.photoview;

import android.view.View;

/**
 * The interface On view tap listener.
 * 视图点击监听器的接口
 */
public interface OnViewTapListener {

    /**
     * A callback to receive where the user taps on a ImageView. You will receive a callback if
     * the user taps anywhere on the view, tapping on 'whitespace' will not be ignored.
     * 用于接收用户点击ImageView的回调。如果，您将收到一个回调
     * 用户点击视图的任何地方，点击“空格”将不会被忽略
     * @param view - View the user tapped. 查看用户点击的情况
     * @param x    - where the user tapped from the left of the View. 用户从视图左侧点击的位置
     * @param y    - where the user tapped from the top of the View. 用户从视图顶部点击的位置是什么
     */
    void onViewTap(View view, float x, float y);
}
