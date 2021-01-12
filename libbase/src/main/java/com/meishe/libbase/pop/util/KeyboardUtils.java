package com.meishe.libbase.pop.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.meishe.libbase.pop.core.BasePopupView;

import java.util.HashMap;

/**
 * Description:
 * 键盘工具类
 * Keyboard tool class
 */
public final class KeyboardUtils {

    public static int sDecorViewInvisibleHeightPre;
    private static ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private static HashMap<View,OnSoftInputChangedListener> listenerMap = new HashMap<>();
    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static int sDecorViewDelta = 0;

    private static int getDecorViewInvisibleHeight(final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        if (decorView == null) return sDecorViewInvisibleHeightPre;
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    /**
     * Register soft input changed listener.
     * 注册软输入改变的监听器
     * @param activity  The activity.
     * @param popupView the popup view 弹出视图
     * @param listener  The soft input changed listener. 软输入改变了监听器
     */
    public static void registerSoftInputChangedListener(final Activity activity, final BasePopupView popupView, final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(activity);
        listenerMap.put(popupView, listener);
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    int height = getDecorViewInvisibleHeight(activity);
                    if (sDecorViewInvisibleHeightPre != height) {
                        /*
                        * 通知所有弹窗的监听器输入法高度变化了
                        * Notifies all popup listeners that the input method height has changed
                        * */
                        for (OnSoftInputChangedListener  changedListener: listenerMap.values()) {
                            changedListener.onSoftInputChanged(height);
                        }
                        sDecorViewInvisibleHeightPre = height;
                    }
            }
        };
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * Remove layout change listener.
     * 删除布局更改监听器
     * @param decorView the decor view装饰
     * @param popupView the popup view 弹出视图
     */
    public static void removeLayoutChangeListener(View decorView, BasePopupView popupView){
        onGlobalLayoutListener = null;
        if(decorView==null)return;
        View contentView = decorView.findViewById(android.R.id.content);
        if(contentView==null)return;
        contentView.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        listenerMap.remove(popupView);
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Show soft input.
     * 显示软输入
     * @param view the view
     */
    public static void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Hide soft input.
     * 隐藏软输入
     * @param view the view
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * The interface On soft input changed listener.
     * 软输入上的接口改变了监听器
     */
    public interface OnSoftInputChangedListener {
        /**
         * On soft input changed.
         * 软输入改变
         * @param height the height
         */
        void onSoftInputChanged(int height);
    }
}