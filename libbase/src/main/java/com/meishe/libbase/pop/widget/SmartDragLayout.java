package com.meishe.libbase.pop.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.animator.ShadowBgAnimator;
import com.meishe.libbase.pop.enums.LayoutStatus;
import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 智能的拖拽布局，优先滚动整体，整体滚到头，则滚动内部能滚动的View
 * Intelligent drag-and-drop layout gives priority to the whole scroll, and the whole scroll to the end, so that the scroll can be inside the View
 */
public class SmartDragLayout extends FrameLayout implements NestedScrollingParent {
    private static final String TAG = "SmartDragLayout";
    private View child;
    OverScroller scroller;
    VelocityTracker tracker;
    ShadowBgAnimator bgAnimator = new ShadowBgAnimator();
    /*
    * 是否启用手势
    * Whether to enable gestures
    * */
    boolean enableDrag = true;
    /**
     * The Dismiss on touch outside.
     * 在触摸外面解散
     */
    boolean dismissOnTouchOutside = true;
    /**
     * The Has shadow bg.
     * 有阴影bg
     */
    boolean hasShadowBg = true;
    /**
     * The Is user close.
     * 用户是否关闭
     */
    boolean isUserClose = false;
    /**
     * The Status.
     * 状态
     */
    LayoutStatus status = LayoutStatus.Close;

    public SmartDragLayout(Context context) {
        this(context, null);
    }

    public SmartDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (enableDrag) {
            scroller = new OverScroller(context);
        }
    }

    /**
     * The Max y.
     * 最大y
     */
    int maxY;
    /**
     * The Min y.
     * 最小y
     */
    int minY;

    @Override
    public void onViewAdded(View c) {
        super.onViewAdded(c);
        child = c;
    }

    /**
     * The Last height.
     * 最后的高度
     */
    int lastHeight;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        maxY = child.getMeasuredHeight();
        minY = 0;
        int l = getMeasuredWidth() / 2 - child.getMeasuredWidth() / 2;
        if (enableDrag) {
            /*
            * horizontal center
            * 水平居中
            * */
            child.layout(l, getMeasuredHeight(), l + child.getMeasuredWidth(), getMeasuredHeight() + maxY);
            if (status == LayoutStatus.Open) {
                /*
                * 通过scroll上移
                * We're moving up by scroll
                * */
                scrollTo(getScrollX(), getScrollY() - (lastHeight - maxY));
            }
        } else {
            /*
            * like bottom gravity
            * 像底重力
            * */
            child.layout(l, getMeasuredHeight() - child.getMeasuredHeight(), l + child.getMeasuredWidth(), getMeasuredHeight());
        }
        lastHeight = maxY;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        isUserClose = true;
        return super.dispatchTouchEvent(ev);
    }

    /**
     * The Touch x.
     * 触摸x
     */
    float touchX, /**
     * The Touch y.
     * 触摸y
     */
    touchY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (scroller.computeScrollOffset()) {
            touchX = 0;
            touchY = 0;
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (enableDrag)
                    tracker = VelocityTracker.obtain();
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (enableDrag) {
                    tracker.addMovement(event);
                    tracker.computeCurrentVelocity(1000);
                    int dy = (int) (event.getY() - touchY);
                    scrollTo(getScrollX(), getScrollY() - dy);
                    touchY = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                /*
                * click in child rect
                * 单击子矩形
                * */
                Rect rect = new Rect();
                child.getGlobalVisibleRect(rect);
                if (!XPopupUtils.isInRect(event.getRawX(), event.getRawY(), rect) && dismissOnTouchOutside) {
                    float distance = (float) Math.sqrt(Math.pow(event.getX() - touchX, 2) + Math.pow(event.getY() - touchY, 2));
                    if (distance < ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                        performClick();
                    }
                }
                if (enableDrag) {
                    float yVelocity = tracker.getYVelocity();
                    if (yVelocity > 1500) {
                        close();
                    } else {
                        finishScroll();
                    }

                    tracker.clear();
                    tracker.recycle();
                }

                break;
        }
        return true;
    }

    private void finishScroll() {
        if (enableDrag) {
            int threshold = isScrollUp ? (maxY - minY) / 3 : (maxY - minY) * 2 / 3;
            int dy = (getScrollY() > threshold ? maxY : minY) - getScrollY();
            scroller.startScroll(getScrollX(), getScrollY(), 0, dy, XPopup.getAnimationDuration());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * The Is scroll up.
     * 向上滚动
     */
    boolean isScrollUp;

    @Override
    public void scrollTo(int x, int y) {
        if (y > maxY) y = maxY;
        if (y < minY) y = minY;
        float fraction = (y - minY) * 1f / (maxY - minY);
        isScrollUp = y > getScrollY();
        if (hasShadowBg)
            setBackgroundColor(bgAnimator.calculateBgColor(fraction));
        if (listener != null) {
            if (isUserClose && fraction == 0f && status != LayoutStatus.Close) {
                status = LayoutStatus.Close;
                listener.onClose();
            } else if (fraction == 1f && status != LayoutStatus.Open) {
                status = LayoutStatus.Open;
                listener.onOpen();
            }
        }
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isScrollUp = false;
        isUserClose = false;
        setTranslationY(0);
    }

    /**
     * Open.
     * 打开
     */
    public void open() {
        status = LayoutStatus.Opening;
        post(new Runnable() {
            @Override
            public void run() {
                smoothScroll(maxY - getScrollY());
            }
        });
    }

    /**
     * Close.
     * 关闭
     */
    public void close() {
        isUserClose = true;
        status = LayoutStatus.Closing;
        post(new Runnable() {
            @Override
            public void run() {
                smoothScroll(minY - getScrollY());
            }
        });
    }

    /**
     * Smooth scroll.
     * 平滑上卷
     * @param dy the dy
     */
    public void smoothScroll(final int dy) {
        post(new Runnable() {
            @Override
            public void run() {
                scroller.startScroll(getScrollX(), getScrollY(), 0, dy, XPopup.getAnimationDuration());
                ViewCompat.postInvalidateOnAnimation(SmartDragLayout.this);
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL && enableDrag;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        /*
        * 必须要取消，否则会导致滑动初次延迟
        * You must cancel, otherwise it will cause the initial delay of sliding
        * */
        scroller.abortAnimation();
    }

    @Override
    public void onStopNestedScroll(View target) {
        finishScroll();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        scrollTo(getScrollX(), getScrollY() + dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            /*
            * scroll up
            * 向上滚动
            * */
            int newY = getScrollY() + dy;
            if (newY < maxY) {
                /*
                * dy不一定能消费完
                * Dy doesn't necessarily consume all of it
                * */
                consumed[1] = dy;
            }
            scrollTo(getScrollX(), newY);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        boolean isDragging = getScrollY() > minY && getScrollY() < maxY;
        if (isDragging && velocityY < -1500) {
            close();
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * Enable drag.
     * 支持拖拽
     * @param enableDrag the enable drag
     */
    public void enableDrag(boolean enableDrag) {
        this.enableDrag = enableDrag;
    }

    /**
     * Dismiss on touch outside.
     * 在触摸外部时解散
     * @param dismissOnTouchOutside the dismiss on touch outside 在触摸外面解散
     */
    public void dismissOnTouchOutside(boolean dismissOnTouchOutside) {
        this.dismissOnTouchOutside = dismissOnTouchOutside;
    }

    /**
     * Has shadow bg.
     * bg有阴影
     * @param hasShadowBg the has shadow bg 有阴影bg
     */
    public void hasShadowBg(boolean hasShadowBg) {
        this.hasShadowBg = hasShadowBg;
    }

    private OnCloseListener listener;

    /**
     * Sets on close listener.
     * 设置关闭监听
     * @param listener the listener 监听
     */
    public void setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
    }

    /**
     * The interface On close listener.
     * 关闭监听器的接口
     */
    public interface OnCloseListener {
        /**
         * On close.
         * 关闭
         */
        void onClose();

        /**
         * On open.
         * 打开
         */
        void onOpen();
    }
}
