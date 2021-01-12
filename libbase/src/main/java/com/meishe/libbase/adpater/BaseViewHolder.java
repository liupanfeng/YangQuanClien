/**
 * Copyright 2013 Joan Zapata
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.meishe.libbase.adpater;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * viewholder基类
 *  viewholder base class
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    public Set<Integer> getNestViews() {
        return nestViews;
    }

    private final HashSet<Integer> nestViews;

    private final LinkedHashSet<Integer> childClickViewIds;

    private final LinkedHashSet<Integer> itemChildLongClickViewIds;
    private BaseQuickAdapter adapter;
    /**
     * use itemView instead
     */
    @Deprecated
    public View convertView;

    /**
     * Package private field to retain the associated user object and detect a change
     */
    private Object associatedObject;


    /**
     * Instantiates a new Base view holder.
     *
     * @param view the view
     */
    public BaseViewHolder(final View view) {
        super(view);
        this.views = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
        this.nestViews = new HashSet<>();
        convertView = view;


    }

    /**
     * Gets item child long click view ids.
     *
     * @return the item child long click view ids
     */
    public HashSet<Integer> getItemChildLongClickViewIds() {
        return itemChildLongClickViewIds;
    }

    /**
     * Gets child click view ids.
     *
     * @return the child click view ids
     */
    public HashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    /**
     * use itemView instead
     *
     * @return the ViewHolder root view
     */
    @Deprecated
    public View getConvertView() {

        return convertView;
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     * 将从资源id设置ImageView的图像
     * @param viewId     The view id. 视图id
     * @param imageResId The image resource id. 图像资源id
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     * 将设置一个视图的背景颜色
     * @param viewId The view id. 视图id
     * @param color  A color, not a resource id. 一种颜色，而不是资源id
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     * 设置一个视图的背景
     * @param viewId        The view id. 视图id
     * @param backgroundRes A resource to use as a background. 用作背景的资源
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setBackgroundDrawable(@IdRes int viewId, Drawable backgroundRes) {
        View view = getView(viewId);
        view.setBackground(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     * 设置文本颜色的一个TextView
     * @param viewId    The view id. 视图id
     * @param textColor The text color (not a resource id). 文本颜色(不是资源id)
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }


    /**
     * Will set the image of an ImageView from a drawable.
     * 将设置图像的ImageView从一个drawable
     * @param viewId   The view id. 视图id
     * @param drawable The image drawable. 可绘制的图像
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     * 添加一个操作来设置图像视图的图像 可多次调用
     * @param viewId the view id 视图id
     * @param bitmap the bitmap 位图
     * @return the image bitmap 位图
     */
    public BaseViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     * 添加一个设置视图alpha的操作。可多次调用。
     * 在0-1之间
     * @param viewId the view id 视图id
     * @param value  the value 值
     * @return the alpha
     */
    public BaseViewHolder setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            /*
            * Pre-honeycomb hack to set Alpha value
            * 蜂窝hack设置Alpha值
            * */
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     * 视图可见性设置为可见(true)或消失(false)。
     * @param viewId  The view id. 视图id
     * @param visible True for VISIBLE, false for GONE. 真实代表可见，虚假代表消失
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setGone(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     * 视图可见性设置为可见(真)或不可见(假)
     * @param viewId  The view id. 视图id
     * @param visible True for VISIBLE, false for INVISIBLE. 可见为真，无形为假
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * Add links into a TextView.
     * 添加链接到TextView
     * @param viewId The id of the TextView to linkify. 要连接的TextView的id
     * @return The BaseViewHolder for chaining. 用于链接的BaseViewHolder
     */
    public BaseViewHolder linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     * 将字体应用到给定的viewId，并启用亚像素呈现
     * @param viewId   the view id 视图id
     * @param typeface the typeface 字体
     * @return the typeface 字体
     */
    public BaseViewHolder setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
       设置ProgressBar的进度
     * @param viewId   The view id. 视图id
     * @param progress The progress.进展
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    @Deprecated
    public BaseViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * add childView id
     *
     * @param viewIds add the child views id can support childview click
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildClickListener(listener))} <p> or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    public BaseViewHolder addOnClickListener(@IdRes final int ...viewIds) {
        for (int viewId : viewIds) {
            childClickViewIds.add(viewId);
            final View view = getView(viewId);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adapter.getOnItemChildClickListener() != null) {
                            int position = getAdapterPosition();
                            if (position == RecyclerView.NO_POSITION) {
                                return;
                            }
                            position -= adapter.getHeaderLayoutCount();
                            adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, position);
                        }
                    }
                });
            }
        }
        return this;
    }


    /**
     * set nestview id
     *
     * @param viewIds add the child views id   can support childview click
     * @return nest view
     */
    public BaseViewHolder setNestView(@IdRes int ... viewIds) {
        for (int viewId : viewIds) {
            nestViews.add(viewId);
        }
        addOnClickListener(viewIds);
        addOnLongClickListener(viewIds);
        return this;
    }

    /**
     * add long click view id
     *
     * @param viewIds the view ids
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildLongClickListener(listener))} <p> or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    public BaseViewHolder addOnLongClickListener(@IdRes final int ... viewIds) {
        for (int viewId : viewIds) {
            itemChildLongClickViewIds.add(viewId);
            final View view = getView(viewId);
            if (view != null) {
                if (!view.isLongClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (adapter.getOnItemChildLongClickListener() == null) {
                            return false;
                        }
                        int position = getAdapterPosition();
                        if (position == RecyclerView.NO_POSITION) {
                            return false;
                        }
                        position -= adapter.getHeaderLayoutCount();
                        return adapter.getOnItemChildLongClickListener().onItemChildLongClick(adapter, v, position);
                    }
                });
            }
        }
        return this;
    }


    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on touch listener;
     * @return The BaseViewHolder for chaining.
     */
    @Deprecated
    public BaseViewHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * Sets the on long click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return The BaseViewHolder for chaining. Please use {@link #addOnLongClickListener} (adapter.setOnItemChildLongClickListener(listener))}
     */
    @Deprecated
    public BaseViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * Sets the listview or gridview's item click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item on click listener;
     * @return The BaseViewHolder for chaining. Please use {@link #addOnClickListener} (int)} (adapter.setOnItemChildClickListener(listener))}
     */
    @Deprecated
    public BaseViewHolder setOnItemClickListener(@IdRes int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    /**
     * Sets the listview or gridview's item long click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item long click listener;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnItemLongClickListener(@IdRes int viewId, AdapterView.OnItemLongClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item selected click listener;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnItemSelectedClickListener(@IdRes int viewId, AdapterView.OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The checked change listener of compound button.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnCheckedChangeListener(@IdRes int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }
    /**
     * Set the enabled state of this view.
     *
     * @param viewId The view id.
     * @param enable The checked status;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setEnabled(@IdRes int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param viewId  The view id.
     * @param adapter The adapter;
     * @return The BaseViewHolder for chaining.
     */
    @SuppressWarnings("unchecked")
    public BaseViewHolder setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param adapter The adapter;
     * @return The BaseViewHolder for chaining.
     */
    protected BaseViewHolder setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    /**
     * Gets view.
     *
     * @param <T>    the type parameter
     * @param viewId the view id
     * @return the view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Retrieves the last converted object on this view.
     *
     * @return the associated object
     */
    public Object getAssociatedObject() {
        return associatedObject;
    }

    /**
     * Should be called during convert
     *
     * @param associatedObject the associated object
     */
    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }
}
