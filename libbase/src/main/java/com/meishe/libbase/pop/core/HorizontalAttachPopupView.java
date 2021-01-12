package com.meishe.libbase.pop.core;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.ScrollScaleAnimator;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 水平方向的依附于某个View或者某个点的弹窗，可以轻松实现微信朋友圈点赞的弹窗效果。
 * 支持通过popupPosition()方法手动指定想要出现在目标的左边还是右边，但是对Top和Bottom则不生效。
 * Description: horizontally attached to a View or a point, you can easily achieve the popover effect of WeChat friend circle likes.
 * Supports manually specifying whether you want to appear to the left or right of the target via the popupPosition() method, but this does not work for Top and Bottom.
 */
public class HorizontalAttachPopupView extends AttachPopupView {
    public HorizontalAttachPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        defaultOffsetY = popupInfo.offsetY == 0 ? XPopupUtils.dp2px(getContext(), 0) : popupInfo.offsetY;
        defaultOffsetX = popupInfo.offsetX == 0 ? XPopupUtils.dp2px(getContext(), 4) : popupInfo.offsetX;
        if (!popupInfo.hasShadowBg) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                if(getPopupBackground()==null){
                    defaultOffsetX -= bgDrawableMargin;
                    defaultOffsetY -= bgDrawableMargin;
                }
            }
        }
    }

    /**
     * 执行附着逻辑
     * Perform attachment logic
     */
    protected void doAttach() {
        float translationX = 0, translationY = 0;
        int w = getPopupContentView().getMeasuredWidth();
        int h = getPopupContentView().getMeasuredHeight();
        /*
        * 0. 判断是依附于某个点还是某个View
        * Whether it's attached to a point or a View
        * */
        if (popupInfo.touchPoint != null) {
            /*
            * 依附于指定点
            * Attached to a specified point
            * */
            isShowLeft = popupInfo.touchPoint.x > XPopupUtils.getWindowWidth(getContext()) / 2;

            /*
            * translationX: 在左边就和点左边对齐，在右边就和其右边对齐
            * TranslationX: translationX is aligned with a dot on the left and translationX on the right
            * */
            translationX = isShowLeftToTarget() ? (popupInfo.touchPoint.x - w - defaultOffsetX) : (popupInfo.touchPoint.x + defaultOffsetX);
            translationY = popupInfo.touchPoint.y - h * .5f + defaultOffsetY;
        } else {
            /*
            * 依附于指定View
            * 1. 获取atView在屏幕上的位置
            * Attached to the specified View
            * 1. Get the location of the atView on the screen
            * */

            int[] locations = new int[2];
            popupInfo.getAtView().getLocationOnScreen(locations);
            Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
                    locations[1] + popupInfo.getAtView().getMeasuredHeight());

            int centerX = (rect.left + rect.right) / 2;

            isShowLeft = centerX > XPopupUtils.getWindowWidth(getContext()) / 2;

            translationX = isShowLeftToTarget() ? (rect.left - w + defaultOffsetX) : (rect.right + defaultOffsetX);
            translationY = rect.top + (rect.height()-h)/2 + defaultOffsetY;
        }
        getPopupContentView().setTranslationX(translationX);
        getPopupContentView().setTranslationY(translationY);
    }

    private boolean isShowLeftToTarget() {
        return (isShowLeft || popupInfo.popupPosition == PopupPosition.Left)
                && popupInfo.popupPosition != PopupPosition.Right;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        ScrollScaleAnimator animator;
        if (isShowLeftToTarget()) {
            animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromRight);
        } else {
            animator = new ScrollScaleAnimator(getPopupContentView(), PopupAnimation.ScrollAlphaFromLeft);
        }
        animator.isOnlyScaleX = true;
        return animator;
    }
}
