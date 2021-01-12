package com.meishe.libbase.pop.enums;

/**
 * Description:
 * 弹出动画类
 * Pop-up animation class
 */
public enum PopupAnimation {
    /**
     * Scale alpha from center popup animation.
     * 缩放 + 透明渐变 从中心进行缩放+透明渐变
     */
    ScaleAlphaFromCenter,
    /**
     * Scale alpha from left top popup animation.
     * 从左上角进行缩放+透明渐变
     */
    ScaleAlphaFromLeftTop,
    /**
     * Scale alpha from right top popup animation.
     * 从右上角进行缩放+透明渐变
     */
    ScaleAlphaFromRightTop,
    /**
     * Scale alpha from left bottom popup animation.
     * 从左下角进行缩放+透明渐变
     */
    ScaleAlphaFromLeftBottom,
    /**
     * Scale alpha from right bottom popup animation.
     * 从右下角进行缩放+透明渐变
     */
    ScaleAlphaFromRightBottom,

    /**
     * Translate alpha from left popup animation.
     * 平移 + 透明渐变 从左平移进入
     */
    TranslateAlphaFromLeft,
    /**
     * Translate alpha from right popup animation.
     * 从右平移进入
     */
    TranslateAlphaFromRight,
    /**
     * Translate alpha from top popup animation.
     * 从上方平移进入
     */
    TranslateAlphaFromTop,
    /**
     * Translate alpha from bottom popup animation.
     * 从下方平移进入
     */
    TranslateAlphaFromBottom,

    /**
     * Translate from left popup animation.
     * 平移，不带透明渐变 从左平移进入
     */
    TranslateFromLeft,
    /**
     * Translate from right popup animation.
     * 从右平移进入
     */
    TranslateFromRight,
    /**
     * Translate from top popup animation.
     * 从上方平移进入
     */
    TranslateFromTop,
    /**
     * Translate from bottom popup animation.
     * 从下方平移进入
     */
    TranslateFromBottom,

    /**
     * Scroll alpha from left popup animation.
     * 滑动 + 透明渐变
     */
    ScrollAlphaFromLeft,
    /**
     * Scroll alpha from left top popup animation.
     * 滚动渐变从左上角弹出动画
     */
    ScrollAlphaFromLeftTop,
    /**
     * Scroll alpha from top popup animation.
     * 滚动渐变从顶部弹出动画
     */
    ScrollAlphaFromTop,
    /**
     * Scroll alpha from right top popup animation.
     * 滚动渐变从右上方弹出动画
     */
    ScrollAlphaFromRightTop,
    /**
     * Scroll alpha from right popup animation.
     * 滚动渐变从右弹出动画
     */
    ScrollAlphaFromRight,
    /**
     * Scroll alpha from right bottom popup animation.
     * 滚动渐变从右下角弹出动画
     */
    ScrollAlphaFromRightBottom,
    /**
     * Scroll alpha from bottom popup animation.
     * 滚动渐变从底部弹出动画
     */
    ScrollAlphaFromBottom,
    /**
     * Scroll alpha from left bottom popup animation.
     * 滚动渐变从左底部弹出动画
     */
    ScrollAlphaFromLeftBottom,

    /**
     * No animation popup animation.
     * 禁用动画
     */
    NoAnimation
}
