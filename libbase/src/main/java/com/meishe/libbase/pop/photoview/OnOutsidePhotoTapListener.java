package com.meishe.libbase.pop.photoview;

import android.widget.ImageView;

/**
 * Callback when the user tapped outside of the photo
 * 当用户在照片外部轻击时回调
 */
public interface OnOutsidePhotoTapListener {

    /**
     * The outside of the photo has been tapped
     * 这张照片的外面已经被点击了
     * @param imageView the image view 图像视图
     */
    void onOutsidePhotoTap(ImageView imageView);
}
