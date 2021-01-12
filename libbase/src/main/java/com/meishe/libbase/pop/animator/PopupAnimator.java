package com.meishe.libbase.pop.animator;

import android.view.View;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.enums.PopupAnimation;

/**
 * Description: 弹窗动画执行器
 * Popover animation executor
 */
public abstract class PopupAnimator {
    public View targetView;
    /*
    * 内置的动画
    *  Built-in animation
    * */
    public PopupAnimation popupAnimation;
    public PopupAnimator(){}
    public PopupAnimator(View target){
        this(target, null);
    }

    public PopupAnimator(View target, PopupAnimation popupAnimation){
        this.targetView = target;
        this.popupAnimation = popupAnimation;
    }

    public abstract void initAnimator();
    public abstract void animateShow();
    public abstract void animateDismiss();
    public int getDuration(){
        return XPopup.getAnimationDuration();
    }
}
