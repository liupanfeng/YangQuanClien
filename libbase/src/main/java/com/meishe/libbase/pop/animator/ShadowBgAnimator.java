package com.meishe.libbase.pop.animator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.meishe.libbase.pop.XPopup;


/**
 * Description: 背景Shadow动画器，负责执行半透明的渐入渐出动画
 * The background Shadow animator is responsible for performing the translucent fade-in and fade-out animation
 */
public class ShadowBgAnimator extends PopupAnimator {

    public ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public int startColor = Color.TRANSPARENT;
    public boolean isZeroDuration = false;

    public ShadowBgAnimator(View target) {
        super(target);
    }

    public ShadowBgAnimator() {}
    @Override
    public void initAnimator() {
        targetView.setBackgroundColor(startColor);
    }

    @Override
    public void animateShow() {
        ValueAnimator animator = ValueAnimator.ofObject(argbEvaluator, startColor, XPopup.getShadowBgColor());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetView.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(isZeroDuration?0:XPopup.getAnimationDuration()).start();
    }

    @Override
    public void animateDismiss() {
        ValueAnimator animator = ValueAnimator.ofObject(argbEvaluator, XPopup.getShadowBgColor(), startColor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetView.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(isZeroDuration?0: XPopup.getAnimationDuration()).start();
    }

    /**
     * Calculate bg color int.
     * 计算bg颜色
     * @param fraction the fraction 分数
     * @return the int
     */
    public int calculateBgColor(float fraction){
        return (int) argbEvaluator.evaluate(fraction, startColor, XPopup.getShadowBgColor());
    }

}
