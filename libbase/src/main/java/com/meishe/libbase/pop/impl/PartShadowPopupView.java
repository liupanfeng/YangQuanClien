package com.meishe.libbase.pop.impl;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.TranslateAnimator;
import com.meishe.libbase.pop.core.AttachPopupView;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.interfaces.OnClickOutsideListener;
import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 局部阴影的弹窗，类似于淘宝商品列表的下拉筛选弹窗
 * A popover with partial shadows, similar to the drop-down screen popover for taobao's list of items
 */
public abstract class PartShadowPopupView extends AttachPopupView {
    public PartShadowPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        defaultOffsetY = popupInfo.offsetY == 0 ? XPopupUtils.dp2px(getContext(), 0) : popupInfo.offsetY;
        defaultOffsetX = popupInfo.offsetX == 0 ? XPopupUtils.dp2px(getContext(), 0) : popupInfo.offsetX;

        getPopupImplView().setTranslationX(popupInfo.offsetX);
        getPopupImplView().setTranslationY(popupInfo.offsetY);
    }

    @Override
    public void onNavigationBarChange(boolean show) {
        super.onNavigationBarChange(show);
        if(!show){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)getPopupContentView().getLayoutParams();
            params.height = XPopupUtils.getWindowHeight(getContext());
            getPopupContentView().setLayoutParams(params);
        }
    }

    @Override
    protected void doAttach() {
        if (popupInfo.getAtView() == null)
            throw new IllegalArgumentException("atView must not be null for PartShadowPopupView！");

        /*
        * 指定阴影动画的目标View
        * Specifies the target View for the shadow animation
        * */
        shadowBgAnimator.targetView = getPopupContentView();
        /*
        * 1. apply width and height
        * 应用宽度和高度
        * */
        int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getPopupContentView().getLayoutParams();
        if(rotation==0){
            /*
            * 满宽
            * Full width
            * */
            params.width = getMeasuredWidth();
        }else if(rotation==1 || rotation==3){
            params.width = getMeasuredWidth() - (XPopupUtils.isNavBarVisible(getContext()) ? XPopupUtils.getNavBarHeight() : 0);
        }

        /*
        * 水平居中
        * center horizontally
        * */
        if(popupInfo.isCenterHorizontal && getPopupImplView()!=null){
            getPopupImplView().setTranslationX(XPopupUtils.getWindowWidth(getContext())/2f - getPopupContentView().getMeasuredWidth()/2f);
        }

        /*
        * 1. 获取atView在屏幕上的位置
        * Gets the location of the atView on the screen
        * */
        int[] locations = new int[2];
        popupInfo.getAtView().getLocationOnScreen(locations);
        Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
                locations[1] + popupInfo.getAtView().getMeasuredHeight());
        int centerY = rect.top + rect.height()/2;
        if((centerY > getMeasuredHeight()/2 || popupInfo.popupPosition== PopupPosition.Top) && popupInfo.popupPosition!= PopupPosition.Bottom){
            /*
            * 说明atView在Window下半部分，PartShadow应该显示在它上方，计算atView之上的高度
            * Indicates that the atView is in the lower half of the Window, and the PartShadow should be displayed above it, calculating the height above the atView
            * */
            params.height = rect.top;
            isShowUp = true;
            params.topMargin = -defaultOffsetY;
            /*
            *  同时自定义的impl View应该Gravity居于底部
            * And the custom IMPL View should have Gravity at the bottom
            * */
            View implView = ((ViewGroup)getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (FrameLayout.LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.BOTTOM;
            if(getMaxHeight()!=0)
                implParams.height = Math.min(implView.getMeasuredHeight(), getMaxHeight());
            implView.setLayoutParams(implParams);

        } else {
            /*
            * atView在上半部分，PartShadow应该显示在它下方，计算atView之下的高度
            * The atView is in the top half, and the PartShadow should be displayed below it, calculating the height below the atView
            * */
            params.height = getMeasuredHeight() - rect.bottom;
            /*
            * 防止伸到导航栏下面
            * Prevent reaching below the navigation bar
            * */
            if(XPopupUtils.isNavBarVisible(getContext())){
                params.height -= XPopupUtils.getNavBarHeight();
            }
            isShowUp = false;
            params.topMargin = rect.bottom + defaultOffsetY;

            /*
            * 同时自定义的impl View应该Gravity居于顶部
            * And the custom Impl View should have Gravity at the top
            * */
            View implView = ((ViewGroup)getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (FrameLayout.LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.TOP;
            if(getMaxHeight()!=0)
                implParams.height = Math.min(implView.getMeasuredHeight(), getMaxHeight());
            implView.setLayoutParams(implParams);
        }
        getPopupContentView().setLayoutParams(params);

        attachPopupContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(popupInfo.isDismissOnTouchOutside)dismiss();
                return false;
            }
        });

        attachPopupContainer.setOnClickOutsideListener(new OnClickOutsideListener() {
            @Override
            public void onClickOutside() {
                if(popupInfo.isDismissOnTouchOutside)dismiss();
            }
        });
    }
    /*
    * 让触摸透过
    * Let the touch pass through
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(popupInfo.isDismissOnTouchOutside){
            dismiss();
        }
        return !popupInfo.isDismissOnTouchOutside;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupImplView(), isShowUp ?
                PopupAnimation.TranslateFromBottom: PopupAnimation.TranslateFromTop);
    }

}
