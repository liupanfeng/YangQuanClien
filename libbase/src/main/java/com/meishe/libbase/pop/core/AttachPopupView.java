package com.meishe.libbase.pop.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.meishe.libbase.R;
import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.ScrollScaleAnimator;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.util.XPopupUtils;
import com.meishe.libbase.pop.widget.PartShadowContainer;


/**
 * Description: 依附于某个View的弹窗，弹窗会出现在目标的上方或下方，如果你想要出现在目标的左边或者右边，请使用HorizontalAttachPopupView。
 * 支持通过popupPosition()方法手动指定想要出现在目标的上边还是下边，但是对Left和Right则不生效。
 * Attached to a View of the popup window, the pop-up window will appear above or below the target, if you want to appear in the left or right of the target, please use HorizontalAttachPopupView.
 * * Supports manually specifying whether you want to appear above or below the target via the popupPosition() method, but this does not work for Left and Right.
 */
public abstract class AttachPopupView extends BasePopupView {
    protected int defaultOffsetY = 0;
    protected int defaultOffsetX = 0;
    protected PartShadowContainer attachPopupContainer;

    public AttachPopupView(@NonNull Context context) {
        super(context);
        attachPopupContainer = findViewById(R.id.attachPopupContainer);

        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), attachPopupContainer, false);
        attachPopupContainer.addView(contentView);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout.x_pop_attach_popup_view;
    }

    public boolean isShowUp;
    boolean isShowLeft;
    protected int bgDrawableMargin = 6;

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        if (popupInfo.getAtView() == null && popupInfo.touchPoint == null)
            throw new IllegalArgumentException("atView() or touchPoint must not be null for AttachPopupView ！");

        defaultOffsetY = popupInfo.offsetY == 0 ? XPopupUtils.dp2px(getContext(), 4) : popupInfo.offsetY;
        defaultOffsetX = popupInfo.offsetX == 0 ? XPopupUtils.dp2px(getContext(), 0) : popupInfo.offsetX;

        attachPopupContainer.setTranslationX(popupInfo.offsetX);
        attachPopupContainer.setTranslationY(popupInfo.offsetY);
        if (!popupInfo.hasShadowBg) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                /*
                * 优先使用implView的背景
                * The background of implView is preferred
                * */
                if(getPopupBackground()==null){
                    attachPopupContainer.setBackgroundColor(Color.WHITE);
                }else {
                    attachPopupContainer.setBackgroundDrawable(getPopupBackground());
                }
                attachPopupContainer.setElevation(XPopupUtils.dp2px(getContext(), 10));
            } else {
                /*
                * 优先使用implView的背景
                * The background of implView is preferred
                * */
                if(getPopupImplView().getBackground()==null){
                    defaultOffsetX -= bgDrawableMargin;
                    defaultOffsetY -= bgDrawableMargin;
                    attachPopupContainer.setBackgroundResource(R.drawable.x_pop_shadow);
                }else {
                    attachPopupContainer.setBackgroundDrawable(getPopupBackground());
                }
            }
        }
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), new Runnable() {
            @Override
            public void run() {
                doAttach();
            }
        });

    }

    /**
     * 执行倚靠逻辑
     * Execution dependant logic
     */
    float translationX = 0,

    translationY = 0;
     /*
     * 弹窗显示的位置不能超越Window高度
     * The position displayed in the popover cannot exceed Window height
     * */
    float maxY = XPopupUtils.getWindowHeight(getContext());
   /*
   * 显示在右边时候的最大值
   * The maximum value shown on the right
   * */
    float maxX = 0;

    /**
     * Do attach.
     * 连接
     */
    protected void doAttach() {
        /*
        * 0. 判断是依附于某个点还是某个View
        * Whether it's attached to a point or a View
        * */
        if (popupInfo.touchPoint != null) {
            /*
            * 依附于指定点
            * Attached to a specified point
            * */
            maxX = Math.max(popupInfo.touchPoint.x - getPopupContentView().getMeasuredWidth(), 0);
            /*
            * 尽量优先放在下方，当不够的时候在显示在上方
            * Try to give priority to the bottom, when not enough in the top of the display
            * 假设下方放不下，超出window高度
            * Let's say it doesn't fit underneath, it's above window
            * */

            boolean isTallerThanWindowHeight = (popupInfo.touchPoint.y + getPopupContentView().getMeasuredHeight()) > maxY;
            if (isTallerThanWindowHeight) {
                isShowUp = popupInfo.touchPoint.y > XPopupUtils.getWindowHeight(getContext()) / 2;
            } else {
                isShowUp = false;
            }
            isShowLeft = popupInfo.touchPoint.x < XPopupUtils.getWindowWidth(getContext()) / 2;

            /*
            * 修正高度，弹窗的高有可能超出window区域
            * Fix the height. The popover height may exceed the window area
            * */
            if (isShowUpToTarget()) {
                if (getPopupContentView().getMeasuredHeight() > popupInfo.touchPoint.y) {
                    ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
                    params.height = (int) (popupInfo.touchPoint.y - XPopupUtils.getStatusBarHeight());
                    getPopupContentView().setLayoutParams(params);
                }
            } else {
                if (getPopupContentView().getMeasuredHeight() + popupInfo.touchPoint.y > XPopupUtils.getWindowHeight(getContext())) {
                    ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
                    params.height = (int) (XPopupUtils.getWindowHeight(getContext()) - popupInfo.touchPoint.y);
                    getPopupContentView().setLayoutParams(params);
                }
            }

            getPopupContentView().post(new Runnable() {
                @Override
                public void run() {
                    translationX = (isShowLeft ? popupInfo.touchPoint.x : maxX) + (isShowLeft ? defaultOffsetX: -defaultOffsetX);
                    if (popupInfo.isCenterHorizontal) {
                        /*
                        * 水平居中
                        * horizontal center
                        * */
                        if (isShowLeft)
                            translationX -= getPopupContentView().getMeasuredWidth() / 2f;
                        else
                            translationX += getPopupContentView().getMeasuredWidth() / 2f;
                    }
                    if (isShowUpToTarget()) {
                        /*
                        * 应显示在point上方
                        *  translationX: 在左边就和atView左边对齐，在右边就和其右边对齐
                        * Should be displayed above the Point
                        * translationX: Aligns to the left of the atView and to the right of the atView
                        * */

                        translationY = popupInfo.touchPoint.y - getPopupContentView().getMeasuredHeight() - defaultOffsetY;
                    } else {
                        translationY = popupInfo.touchPoint.y + defaultOffsetY;
                    }
                    getPopupContentView().setTranslationX(translationX);
                    getPopupContentView().setTranslationY(translationY);
                }
            });

        } else {
            /*
            * 依附于指定View
            * Attached to the specified View
            * 获取atView在屏幕上的位置
            * Gets the location of the atView on the screen
            * */

            int[] locations = new int[2];
            popupInfo.getAtView().getLocationOnScreen(locations);
            final Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
                    locations[1] + popupInfo.getAtView().getMeasuredHeight());

            maxX = Math.max(rect.right - getPopupContentView().getMeasuredWidth(), 0);
            int centerX = (rect.left + rect.right) / 2;

            // 尽量优先放在下方，当不够的时候在显示在上方
            //假设下方放不下，超出window高度
            boolean isTallerThanWindowHeight = (rect.bottom + getPopupContentView().getMeasuredHeight()) > maxY;
            if (isTallerThanWindowHeight) {
                int centerY = (rect.top + rect.bottom) / 2;
                isShowUp = centerY > XPopupUtils.getWindowHeight(getContext()) / 2;
            } else {
                isShowUp = false;
            }
            isShowLeft = centerX < XPopupUtils.getWindowWidth(getContext()) / 2;

            /*
            * 修正高度，弹窗的高有可能超出window区域
            * Fix the height. The popover height may exceed the window area
            * */
            if (isShowUpToTarget()) {
                if (getPopupContentView().getMeasuredHeight() > rect.top) {
                    ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
                    params.height = rect.top - XPopupUtils.getStatusBarHeight();
                    getPopupContentView().setLayoutParams(params);
                }
            } else {
                if (getPopupContentView().getMeasuredHeight() + rect.bottom > XPopupUtils.getWindowHeight(getContext())) {
                    ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
                    params.height = XPopupUtils.getWindowHeight(getContext()) - rect.bottom;
                    getPopupContentView().setLayoutParams(params);
                }
            }

            getPopupContentView().post(new Runnable() {
                @Override
                public void run() {
                    translationX = (isShowLeft ? rect.left : maxX) + (isShowLeft ? defaultOffsetX: -defaultOffsetX);
                    if (popupInfo.isCenterHorizontal) {
                        /*
                        * 水平居中
                        * center horizontally
                        * */
                        if (isShowLeft)
                            translationX += (rect.width() - getPopupContentView().getMeasuredWidth()) / 2f;
                        else
                            translationX -= (rect.width() - getPopupContentView().getMeasuredWidth()) / 2f;
                    }
                    if (isShowUpToTarget()) {
                        /*
                        * 说明上面的空间比较大，应显示在atView上方
                        * Note that the space above is relatively large, should be displayed above atView
                        * translationX: 在左边就和atView左边对齐，在右边就和其右边对齐
                        * TranslationX: translationX is aligned with the atView on the left and translationX on the right
                        * */

                        translationY = rect.top - getPopupContentView().getMeasuredHeight() - defaultOffsetY;
                    } else {
                        translationY = rect.bottom + defaultOffsetY;
                    }
                    getPopupContentView().setTranslationX(translationX);
                    getPopupContentView().setTranslationY(translationY);
                }
            });

        }
    }

    /**
     * Is show up to target boolean.
     * 是否显示目标
     * @return the boolean
     */
    protected boolean isShowUpToTarget() {
        return (isShowUp || popupInfo.popupPosition == PopupPosition.Top)
                && popupInfo.popupPosition != PopupPosition.Bottom;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        PopupAnimator animator;
        if (isShowUpToTarget()) {
            /*
            * 在上方展示
            * On the top
            * */
            if (isShowLeft) {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromLeftBottom);
            } else {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromRightBottom);
            }
        } else {
            /*
            * 在下方展示
            * Show below
            * */
            if (isShowLeft) {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromLeftTop);
            } else {
                animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromRightTop);
            }
        }
        return animator;
    }

    /**
     * 如果Attach弹窗的子类想自定义弹窗的背景，不能去直接给布局设置背景，那样效果不好；需要实现这个方法返回一个Drawable
     * If the Attach popover subclass wants to customize the background of the popover, you can't just set the background to the layout, that's not good; You need to implement this method to return a Drawable
     * @return drawable
     */
    protected Drawable getPopupBackground(){
        return null;
    }

}
