package com.meishe.libbase.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.core.AttachPopupView;
import com.meishe.libbase.pop.core.BasePopupView;
import com.meishe.libbase.pop.core.BottomPopupView;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.libbase.pop.core.PopupInfo;
import com.meishe.libbase.pop.core.PositionPopupView;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.enums.PopupType;
import com.meishe.libbase.pop.impl.LoadingPopupView;
import com.meishe.libbase.pop.interfaces.XPopupCallback;


/**
 * The type X popup.
 * X弹出类
 */
public class XPopup {
    private XPopup() {
    }

    /**
     * 全局弹窗的设置
     * Settings for global popovers
     **/
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = 360;
    public static int statusBarShadowColor = Color.parseColor("#55000000");
    private static int shadowBgColor = Color.parseColor("#9F000000");

    /**
     * Sets shadow bg color.
     * 设置阴影bg颜色
     * @param color the color 颜色
     */
    public static void setShadowBgColor(int color) {
        shadowBgColor = color;
    }

    /**
     * Gets shadow bg color.
     * 获取阴影背景颜色
     * @return the shadow bg color
     */
    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    /**
     * 设置主色调
     * Set the main tone
     * @param color the color 颜色
     */
    public static void setPrimaryColor(int color) {
        primaryColor = color;
    }

    /**
     * Gets primary color.
     * 得到原色
     * @return the primary color 原色
     */
    public static int getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Sets animation duration.
     * 动画时间
     * @param duration the duration 时长
     */
    public static void setAnimationDuration(int duration) {
        if (duration >= 0) {
            animationDuration = duration;
        }
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * The type Builder.
     * 建造者模式类
     */
    public static class Builder {
        private final PopupInfo popupInfo = new PopupInfo();
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Popup type builder.
         * 弹出式建造者
         * @param popupType the popup type 弹出式
         * @return the builder 建造者模式
         */
        public Builder popupType(PopupType popupType) {
            this.popupInfo.popupType = popupType;
            return this;
        }

        /**
         * 设置按下返回键是否关闭弹窗，默认为true
         * Sets whether to close the popover by pressing the return key. The default is true
         * @param isDismissOnBackPressed the is dismiss on back pressed 解雇在背面压
         * @return builder 建造者
         */
        public Builder dismissOnBackPressed(Boolean isDismissOnBackPressed) {
            this.popupInfo.isDismissOnBackPressed = isDismissOnBackPressed;
            return this;
        }

        /**
         * 设置点击弹窗外面是否关闭弹窗，默认为true
         * Sets whether to close the popover by clicking on the popover window. The default is true
         * @param isDismissOnTouchOutside the is dismiss on touch outside 一触到外面，它就消失了
         * @return builder
         */
        public Builder dismissOnTouchOutside(Boolean isDismissOnTouchOutside) {
            this.popupInfo.isDismissOnTouchOutside = isDismissOnTouchOutside;
            return this;
        }

        /**
         * 设置当操作完毕后是否自动关闭弹窗，默认为true。比如：点击Confirm弹窗的确认按钮默认是关闭弹窗，如果为false，则不关闭
         * Sets whether the popover will be closed automatically after the operation is completed. The default is true. Click Confirm. The Confirm button in the Confirm pop-up closes by default. If false, it does not close
         * @param autoDismiss the auto dismiss 自动解散
         * @return builder
         */
        public Builder autoDismiss(Boolean autoDismiss) {
            this.popupInfo.autoDismiss = autoDismiss;
            return this;
        }

        /**
         * 弹窗是否有半透明背景遮罩，默认是true
         * Whether the popover has a translucent background mask. Default is true
         * @param hasShadowBg the has shadow bg 有阴影背景
         * @return builder
         */
        public Builder hasShadowBg(Boolean hasShadowBg) {
            this.popupInfo.hasShadowBg = hasShadowBg;
            return this;
        }

        /**
         * 设置弹窗依附的View
         * Set the popover attached View
         * @param atView the at view 视图
         * @return builder
         */
        public Builder atView(View atView) {
            this.popupInfo.atView = atView;
            return this;
        }

        /**
         * 设置弹窗监视的View
         * Set the View for popover monitoring
         * @param watchView the watch view 表视图
         * @return builder
         */
        public Builder watchView(View watchView) {
            this.popupInfo.watchView = watchView;
            this.popupInfo.watchView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (popupInfo.touchPoint == null || event.getAction() == MotionEvent.ACTION_DOWN)
                        popupInfo.touchPoint = new PointF(event.getRawX(), event.getRawY());
                    return false;
                }
            });
            return this;
        }

        /**
         * 为弹窗设置内置的动画器，默认情况下，已经为每种弹窗设置了效果最佳的动画器；如果你不喜欢，仍然可以修改。
         * Set a built-in animator for the popover. By default, the best animator is set for each popover. If you don't like it, you can still change it
         * @param popupAnimation the popup animation 弹出动画
         * @return builder
         */
        public Builder popupAnimation(PopupAnimation popupAnimation) {
            this.popupInfo.popupAnimation = popupAnimation;
            return this;
        }

        /**
         * 自定义弹窗动画器
         * Custom pop-up animator
         * @param customAnimator the custom animator 自定义动画
         * @return builder
         */
        public Builder customAnimator(PopupAnimator customAnimator) {
            this.popupInfo.customAnimator = customAnimator;
            return this;
        }

        /**
         * 设置最大宽度，如果重写了弹窗的getMaxWidth，则以重写的为准
         * Set the maximum width. If the getMaxWidth of the popover is overridden, the overridden will prevail
         * @param maxWidth the max width 最大的宽度
         * @return builder
         */
        public Builder maxWidth(int maxWidth) {
            this.popupInfo.maxWidth = maxWidth;
            return this;
        }

        /**
         * 设置最大高度，如果重写了弹窗的getMaxHeight，则以重写的为准
         * Sets the maximum height, if the getMaxHeight of the pop-up window is overridden, the overridden will prevail
         * @param maxHeight the max height 最大高度
         * @return builder
         */
        public Builder maxHeight(int maxHeight) {
            this.popupInfo.maxHeight = maxHeight;
            return this;
        }

        /**
         * 是否自动打开输入法，当弹窗包含输入框时很有用，默认为false
         * If the input method is automatically turned on, which is useful when a pop-up window contains an input field, the default is false
         * @param autoOpenSoftInput the auto open soft input 自动打开软输入
         * @return builder
         */
        public Builder autoOpenSoftInput(Boolean autoOpenSoftInput) {
            this.popupInfo.autoOpenSoftInput = autoOpenSoftInput;
            return this;
        }

        /**
         * 当弹出输入法时，弹窗是否要移动到输入法之上，默认为true。如果不移动，弹窗很有可能被输入法盖住
         * When the input method pops up, whether the popover should move above the input method, the default is true. If you don't move, the popover is likely to be covered by the input method
         * @param isMoveUpToKeyboard the is move up to keyboard 移动到键盘上
         * @return builder
         */
        public Builder moveUpToKeyboard(Boolean isMoveUpToKeyboard) {
            this.popupInfo.isMoveUpToKeyboard = isMoveUpToKeyboard;
            return this;
        }

        /**
         * 设置弹窗出现在目标的什么位置，有四种取值：Left，Right，Top，Bottom。这种手动设置位置的行为
         * 只对Attach弹窗和Drawer弹窗生效。
         *
         * @param popupPosition the popup position
         * @return builder
         */
        public Builder popupPosition(PopupPosition popupPosition) {
            this.popupInfo.popupPosition = popupPosition;
            return this;
        }

        /**
         * 设置是否给StatusBar添加阴影，目前对Drawer弹窗生效。如果你的Drawer的背景是白色，建议设置为true，因为状态栏文字的颜色也往往
         * 是白色，会导致状态栏文字看不清；如果Drawer的背景色不是白色，则忽略即可
         * Sets whether to add a shadow to StatusBar, currently takes effect on the Drawer pop-up window. If your Drawer has a white background, it is recommended to set to true, as the status bar text is often colored as well
         *  Is white, which makes the text in the status bar hard to read; If the background color of the Drawer is not white, the Drawer is ignored
         * @param hasStatusBarShadow the has status bar shadow 状态栏阴影
         * @return builder
         */
        public Builder hasStatusBarShadow(boolean hasStatusBarShadow) {
            this.popupInfo.hasStatusBarShadow = hasStatusBarShadow;
            return this;
        }

        /**
         * 弹窗在x方向的偏移量，对所有弹窗生效，单位是px
         * The offset of the popover in the x direction, which is px for all popovers
         * @param offsetX the offset x 抵消x
         * @return builder
         */
        public Builder offsetX(int offsetX) {
            this.popupInfo.offsetX = offsetX;
            return this;
        }

        /**
         * 弹窗在y方向的偏移量，对所有弹窗生效，单位是px
         * The offset in the y direction of the popover, which is px for all popovers
         * @param offsetY the offset y 抵消y
         * @return builder
         */
        public Builder offsetY(int offsetY) {
            this.popupInfo.offsetY = offsetY;
            return this;
        }

        /**
         * 是否启用拖拽，比如：Bottom弹窗默认是带手势拖拽效果的，如果禁用则不能拖拽
         * Whether to enable drag-and-drop, for example: Bottom popovers default to drag-and-drop with gestures; if not, drag-and-drop cannot be enabled
         * @param enableDrag the enable drag 阻力
         * @return builder
         */
        public Builder enableDrag(boolean enableDrag) {
            this.popupInfo.enableDrag = enableDrag;
            return this;
        }

        /**
         * 是否水平居中，默认情况下Attach弹窗依靠着目标的左边或者右边，如果isCenterHorizontal为true，则与目标水平居中对齐
         * With or without a horizontal center, the Attach pop-up leans on the left or right side of the target by default. If isCenterHorizontal is true, align with the target horizontal center
         * @param isCenterHorizontal the is center horizontal 居中水平
         * @return builder
         */
        public Builder isCenterHorizontal(boolean isCenterHorizontal) {
            this.popupInfo.isCenterHorizontal = isCenterHorizontal;
            return this;
        }

        /**
         * 是否抢占焦点，默认情况下弹窗会抢占焦点，目的是为了能处理返回按键事件。如果为false，则不在抢焦点，但也无法响应返回按键了
         * Whether or not to preempt focus, by default the popover will preempt focus in order to be able to handle the key-back event. If false, it is not in focus, but it will not respond to the return button
         * @param isRequestFocus 默认为true The default is true
         * @return builder
         */
        public Builder isRequestFocus(boolean isRequestFocus) {
            this.popupInfo.isRequestFocus = isRequestFocus;
            return this;
        }

        /**
         * 是否让弹窗内的输入框自动获取焦点，默认是true。
         * Whether to let the input box in the popover automatically get focus, the default is true
         * @param autoFocusEditText the auto focus edit text 自动对焦编辑文本
         * @return builder
         */
        public Builder autoFocusEditText(boolean autoFocusEditText) {
            this.popupInfo.autoFocusEditText = autoFocusEditText;
            return this;
        }

        /**
         * 是否点击弹窗背景时将点击事件透传到Activity下，默认是不透传，目前会引发很多不可控的问题，暂时关闭。
         * Whether click the background of the pop-up window to upload the click event to the Activity or not, the default is no transmission, which will cause a lot of uncontrollable problems at present, so it will be closed temporarily.
         * @param isClickThrough
         * @return
         */
//        public Builder isClickThrough(boolean isClickThrough) {
//            this.popupInfo.isClickThrough = isClickThrough;
//            return this;
//        }

        /**
         * 设置弹窗显示和隐藏的回调监听
         * Sets popup display and hidden callback listening
         * @param xPopupCallback the x popup callback x弹窗回调
         * @return popup callback
         */
        public Builder setPopupCallback(XPopupCallback xPopupCallback) {
            this.popupInfo.xPopupCallback = xPopupCallback;
            return this;
        }

        /****************************************** 便捷方法 convenience method****************************************/


        /**
         * 显示在中间加载的弹窗
         * Displays the popover loaded in the middle
         * @param title the title 标题
         * @return loading popup view 加载弹出视图
         */
        public LoadingPopupView asLoading(String title) {
            popupType(PopupType.Center);
            LoadingPopupView popupView = new LoadingPopupView(this.context)
                    .setTitle(title);
            popupView.popupInfo = this.popupInfo;
            return popupView;
        }

        /**
         * As loading loading popup view.
         * 作为加载加载弹出视图
         * @return the loading popup view 加载弹出视图
         */
        public LoadingPopupView asLoading() {
            return asLoading(null);
        }


        /**
         * As custom base popup view.
         * 作为自定义基础弹出视图
         * @param popupView the popup view 弹出视图
         * @return the base popup view 基础弹出视图
         */
        public BasePopupView asCustom(BasePopupView popupView) {
            if (popupView instanceof CenterPopupView) {
                popupType(PopupType.Center);
            } else if (popupView instanceof BottomPopupView) {
                popupType(PopupType.Bottom);
            } else if (popupView instanceof AttachPopupView) {
                popupType(PopupType.AttachView);
            } else if (popupView instanceof PositionPopupView) {
                popupType(PopupType.Position);
            }
            popupView.popupInfo = this.popupInfo;
            return popupView;
        }

    }
}
