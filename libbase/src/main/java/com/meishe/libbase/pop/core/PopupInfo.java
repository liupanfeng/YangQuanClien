package com.meishe.libbase.pop.core;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.enums.PopupType;
import com.meishe.libbase.pop.interfaces.XPopupCallback;

/**
 * Description: Popup的属性封装
 * Attribute encapsulation for Popup
 */
public class PopupInfo {
    /*
     * 窗体的类型
     * Type of form
     * */
    public PopupType popupType = null;
    /*
     * 按返回键是否消失
     * Press the back key to disappear
     * */
    public Boolean isDismissOnBackPressed = true;
    /*
     * 点击外部消失
     * Click on the outside to disappear
     * */
    public Boolean isDismissOnTouchOutside = true;
/*
* 操作完毕后是否自动关闭
* Whether to close automatically after the operation
* */
    public Boolean autoDismiss = true;
/*
* 是否有半透明的背景
* Does it have a translucent background
* */
    public Boolean hasShadowBg = true;
/*
* 依附于那个View显示
* Attached to that View display
* */
    public View atView = null;
/*
* 依附于那个View显示
* Attached to that View display
* */
    public View watchView = null;

    /*
    * 动画执行器，如果不指定，则会根据窗体类型popupType字段生成默认合适的动画执行器
    *  Animation executor, if not specified, generates the default appropriate animation executor based on the form type popupType field
    * */
    public PopupAnimation popupAnimation = null;

    public PopupAnimator customAnimator = null;
/*
* 触摸的点
* Touch point
* */
    public PointF touchPoint = null;
    /**
     * The Max width.
     * 最大宽度
     */
    public int maxWidth;
    /**
     * The Max height.
     * 最大高度
     */
    public int maxHeight;
    /**
     * The Auto open soft input.
     * 是否自动打开输入法
     */
    public Boolean autoOpenSoftInput = false;
    /**
     * The X popup callback.
     * X弹出回调
     */
    public XPopupCallback xPopupCallback;
    /*
    * 每个弹窗所属的DecorView
    * The DecorView to which each pop-up belongs
    * */
    public ViewGroup decorView;
   /*
   * 是否移动到软键盘上面，默认弹窗会移到软键盘上面
   * Whether to move to the soft keyboard, the default popover will move to the soft keyboard
   * */
    public Boolean isMoveUpToKeyboard = true;
   /*
   * 弹窗出现在目标的什么位置
   * Where does the popover appear on the target
   * */
    public PopupPosition popupPosition = null;
    /**
     * The Has status bar shadow.
     * 有状态栏阴影
     */
    public Boolean hasStatusBarShadow = false;

    public int offsetX,
    /*
    * x，y方向的偏移量
    * The offset in the x, y direction
    * */
    offsetY;
    /**
     * The Enable drag.
     * 是否启用拖拽
     */
    public Boolean enableDrag = true;
    /**
     * The Is center horizontal
     * 是否水平居中
     */
    public boolean isCenterHorizontal = false;
    /**
     * The Is request focus.
     * 弹窗是否强制抢占焦点
     */
    public boolean isRequestFocus = true;
    /**
     * The Auto focus edit text.
     * 是否让输入框自动获取焦点
     */
    public boolean autoFocusEditText = true;
//    public boolean isClickThrough = true;//是否点击透传，默认弹背景点击是拦截的

    public View getAtView() {
        return atView;
    }

}
