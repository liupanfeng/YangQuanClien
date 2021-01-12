package com.meishe.libbase.pop.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.meishe.libbase.R;
import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.enums.PopupStatus;
import com.meishe.libbase.pop.util.KeyboardUtils;
import com.meishe.libbase.pop.util.XPopupUtils;
import com.meishe.libbase.pop.widget.SmartDragLayout;


/**
 * Description: 在底部显示的Popup
 * Popup shown at the bottom
 */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;
    public BottomPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout.x_pop_bottom_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        bottomPopupContainer = findViewById(R.id.bottomPopupContainer);
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), bottomPopupContainer, false);
        bottomPopupContainer.addView(contentView);
        bottomPopupContainer.enableDrag(popupInfo.enableDrag);
        bottomPopupContainer.dismissOnTouchOutside(popupInfo.isDismissOnTouchOutside);
        bottomPopupContainer.hasShadowBg(popupInfo.hasShadowBg);

        getPopupImplView().setTranslationX(popupInfo.offsetX);
        getPopupImplView().setTranslationY(popupInfo.offsetY);

        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());

        bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() {
            @Override
            public void onClose() {
                doAfterDismiss();
            }
            @Override
            public void onOpen() {
                BottomPopupView.super.doAfterShow();
            }
        });

        bottomPopupContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    protected void doAfterShow() {
        if(popupInfo.enableDrag){
            /*
            * do nothing self.
            * 本身什么都不做
            * */
        }else {
            super.doAfterShow();
        }
    }

    @Override
    public void doShowAnimation() {
        if (popupInfo.enableDrag) {
            bottomPopupContainer.open();
        } else {
            super.doShowAnimation();
        }
    }

    @Override
    public void doDismissAnimation() {
        if (popupInfo.enableDrag) {
            bottomPopupContainer.close();
        } else {
            super.doDismissAnimation();
        }
    }

    /**
     * 动画是跟随手势发生的，所以不需要额外的动画器，因此动画时间也清零
     * Animation occurs following gestures, so no additional animators are required, so the animation time is also cleared
     * @return
     */
    @Override
    public int getAnimationDuration() {
        return popupInfo.enableDrag ? 0 : super.getAnimationDuration();
    }

    @Override
    protected PopupAnimator getPopupAnimator() {

        /*
        * 移除默认的动画器
        * Removes the default animator
        * */
        return popupInfo.enableDrag ? null : super.getPopupAnimator();
    }

    @Override
    public void dismiss() {
        if (popupInfo.enableDrag) {
            if (popupStatus == PopupStatus.Dismissing) return;
            popupStatus = PopupStatus.Dismissing;
            if (popupInfo.autoOpenSoftInput) KeyboardUtils.hideSoftInput(this);
            clearFocus();
            /*
            * 关闭Drawer，由于Drawer注册了关闭监听，会自动调用dismiss
            * Close the Drawer. Since the Drawer is registered for close listeners, the dismiss is automatically called
            * */
            bottomPopupContainer.close();
        } else {
            super.dismiss();
        }
    }

    /**
     * 具体实现的类的布局
     * The layout of the concrete implementation classes
     * @return
     */
    protected int getImplLayoutId() {
        return 0;
    }

    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? XPopupUtils.getWindowWidth(getContext())
                : popupInfo.maxWidth;
    }

    @Override
    protected View getTargetSizeView() {
        return getPopupImplView();
    }

}
