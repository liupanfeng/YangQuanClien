package com.meishe.libbase.adpater.provider;

import android.content.Context;

import com.meishe.libbase.adpater.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/chaychan
 * 提供程序的基类
 * @param <T> the type parameter
 * @param <V> the type parameter
 * @description: The base class of ItemProvider
 */
public abstract class BaseItemProvider<T,V extends BaseViewHolder> {

    public Context mContext;
    public List<T> mData;


    /*
    * 子类须重写该方法返回viewType
    * Subclasses must override the method to return viewType
    * Rewrite this method to return viewType
    * 重写此方法以返回viewType
    * */

    public abstract int viewType();


    /*
    * 子类须重写该方法返回layout
    * Subclasses must override the method to return Layout
    * Rewrite this method to return layout
    * 重写此方法以返回布局
    * */

    public abstract int layout();

    public abstract void convert(V helper, T data, int position);



    /*
    * 子类若想实现条目点击事件则重写该方法
    * Subclasses override this method if they want to implement an entry click event
    *
    * */

    public void onClick(V helper, T data, int position){};

   /*
   * 子类若想实现条目长按事件则重写该方法
   *  Subclasses override this method if you want to implement an item long press event
   * */

    public boolean onLongClick(V helper, T data, int position){return false;};
}
