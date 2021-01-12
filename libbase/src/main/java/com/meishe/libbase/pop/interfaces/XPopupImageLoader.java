package com.meishe.libbase.pop.interfaces;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;

/**
 * The interface X popup image loader.
 * 弹出式图像加载接口
 * Pop-up image loading interface
 */
public interface XPopupImageLoader{
    /**
     * Load image.
     *加载图像
     * @param position  the position 位置
     * @param uri       the uri  统一资源标识符
     * @param imageView the image view 图像
     */
    void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView);

    /**
     * 获取图片对应的文件
     * Gets the file corresponding to the image
     * @param context the context 上下文
     * @param uri     the uri  统一资源标识符
     * @return image file 图象文件
     */
    File getImageFile(@NonNull Context context, @NonNull Object uri);
}
