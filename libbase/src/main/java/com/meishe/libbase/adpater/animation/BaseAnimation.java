package com.meishe.libbase.adpater.animation;

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * 动画基类
 * Animation base class
 */
public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
