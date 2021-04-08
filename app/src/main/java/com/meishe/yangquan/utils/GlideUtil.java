package com.meishe.yangquan.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/8 14:03
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class GlideUtil {

    private static GlideUtil instance = new GlideUtil();
    private final RequestOptions options;
    private final RequestOptions otherOptions;

    public static GlideUtil getInstance() {
        return instance;
    }

    private GlideUtil() {
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_default_photo);

        otherOptions = new RequestOptions();
        otherOptions.centerCrop();
    }

    /**
     * 使用默认的options 这个只用来加载头像
     * @param url
     * @param imageView
     */
    public void loadPhotoUrl(String url, ImageView imageView){
        Glide.with(App.getContext())
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 这个默认的option 没有默认图片
     * @param url
     * @param imageView
     */
    public void loadUrl(String url, ImageView imageView){
        Glide.with(App.getContext())
                .asBitmap()
                .load(url)
                .apply(otherOptions)
                .into(imageView);
    }

    public void loadUrl(String url, ImageView imageView,RequestOptions requestOptions){
        Glide.with(App.getContext())
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}
