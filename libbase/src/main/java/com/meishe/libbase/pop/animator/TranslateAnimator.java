package com.meishe.libbase.pop.animator;

import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.enums.PopupAnimation;


/**
 * Description: 平移动画，不带渐变
 * Pan animation without gradient
 */
public class TranslateAnimator extends PopupAnimator {
    /*
    * 动画起始坐标
    * Animation start coordinates
    * */
    private float startTranslationX, startTranslationY;
    private int oldWidth, oldHeight;
    private float initTranslationX, initTranslationY;
    private boolean hasInitDefTranslation = false;

    public TranslateAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }

    @Override
    public void initAnimator() {
        if(!hasInitDefTranslation){
            initTranslationX = targetView.getTranslationX();
            initTranslationY = targetView.getTranslationY();
            hasInitDefTranslation = true;
        }
        /*
        * 设置起始坐标
        * Set the starting coordinates
        * */
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();

        oldWidth = targetView.getMeasuredWidth();
        oldHeight = targetView.getMeasuredHeight();
    }

    private void applyTranslation() {
        switch (popupAnimation) {
            case TranslateFromLeft:
                targetView.setTranslationX(-targetView.getRight());
                break;
            case TranslateFromTop:
                targetView.setTranslationY(-targetView.getBottom());
                break;
            case TranslateFromRight:
                targetView.setTranslationX(((View) targetView.getParent()).getMeasuredWidth() - targetView.getLeft());
                break;
            case TranslateFromBottom:
                targetView.setTranslationY(((View) targetView.getParent()).getMeasuredHeight() - targetView.getTop());
                break;
        }
    }

    @Override
    public void animateShow() {
        targetView.animate().translationX(initTranslationX).translationY(initTranslationY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(XPopup.getAnimationDuration()).start();
    }

    @Override
    public void animateDismiss() {
        /*
        * 执行消失动画的时候，宽高可能改变了，所以需要修正动画的起始值
        * The width and height may have changed when performing the vanishing animation, so you need to fix the start value of the animation
        * */
        switch (popupAnimation) {
            case TranslateFromLeft:
                startTranslationX -= targetView.getMeasuredWidth() - oldWidth;
                break;
            case TranslateFromTop:
                startTranslationY -= targetView.getMeasuredHeight() - oldHeight;
                break;
            case TranslateFromRight:
                startTranslationX += targetView.getMeasuredWidth() - oldWidth;
                break;
            case TranslateFromBottom:
                startTranslationY += targetView.getMeasuredHeight() - oldHeight;
                break;
        }

        targetView.animate().translationX(startTranslationX).translationY(startTranslationY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(XPopup.getAnimationDuration()).start();
    }
}
