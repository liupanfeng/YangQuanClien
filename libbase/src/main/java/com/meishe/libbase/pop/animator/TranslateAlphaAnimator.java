package com.meishe.libbase.pop.animator;

import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 平移动画
 * TranslateAnimation
 */
public class TranslateAlphaAnimator extends PopupAnimator {
    /*
    * 动画起始坐标
    * Animation start coordinates
    * */
    private float startTranslationX, startTranslationY;
    private float defTranslationX, defTranslationY;

    public TranslateAlphaAnimator(View target, PopupAnimation popupAnimation) {
        super(target, popupAnimation);
    }

    @Override
    public void initAnimator() {
        defTranslationX = targetView.getTranslationX();
        defTranslationY = targetView.getTranslationY();

        targetView.setAlpha(0);
        /*
        * 设置移动坐标
        *Set moving coordinates
        * */
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();
    }

    private void applyTranslation() {
        int halfWidthOffset = XPopupUtils.getWindowWidth(targetView.getContext())/2 - targetView.getMeasuredWidth()/2;
        int halfHeightOffset = XPopupUtils.getWindowHeight(targetView.getContext())/2 - targetView.getMeasuredHeight()/2;
        switch (popupAnimation){
            case TranslateAlphaFromLeft:
                targetView.setTranslationX(-(targetView.getMeasuredWidth()/* + halfWidthOffset*/));
                break;
            case TranslateAlphaFromTop:
                targetView.setTranslationY(-(targetView.getMeasuredHeight() /*+ halfHeightOffset*/));
                break;
            case TranslateAlphaFromRight:
                targetView.setTranslationX(targetView.getMeasuredWidth() /*+ halfWidthOffset*/);
                break;
            case TranslateAlphaFromBottom:
                targetView.setTranslationY(targetView.getMeasuredHeight() /*+ halfHeightOffset*/);
                break;
        }
    }

    @Override
    public void animateShow() {
        targetView.animate().translationX(defTranslationX).translationY(defTranslationY).alpha(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(XPopup.getAnimationDuration()).start();
    }

    @Override
    public void animateDismiss() {
        targetView.animate().translationX(startTranslationX).translationY(startTranslationY).alpha(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(XPopup.getAnimationDuration()).start();
    }
}
