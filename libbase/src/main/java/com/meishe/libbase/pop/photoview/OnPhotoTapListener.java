package com.meishe.libbase.pop.photoview;

import android.widget.ImageView;

/**
 * A callback to be invoked when the Photo is tapped with a single tap
 * .
 * 一个回调函数，当单击照片时调用
 *
 */
public interface OnPhotoTapListener {

    /**
     * A callback to receive where the user taps on a photo. You will only receive a callback if
     * the user taps on the actual photo, tapping on 'whitespace' will be ignored.
     * 用于接收用户点击照片的回调。只有在以下情况才会收到回调
     * 用户点击实际的照片，点击“空格”将被忽略
     * @param view ImageView the user tapped.用户点击了
     * @param x    where the user tapped from the of the Drawable, as percentage of the             Drawable width.
     *             其中用户所选取的可绘制值，作为可绘制宽度的百分比
     * @param y    where the user tapped from the top of the Drawable, as percentage of the             Drawable height.
     *             用户从可绘制的顶部点击的地方，作为可绘制高度的百分比
     */
    void onPhotoTap(ImageView view, float x, float y);
}
