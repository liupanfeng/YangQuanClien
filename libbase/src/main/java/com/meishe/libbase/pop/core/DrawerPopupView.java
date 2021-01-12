package com.meishe.libbase.pop.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.meishe.libbase.R;
import com.meishe.libbase.pop.enums.PopupPosition;
import com.meishe.libbase.pop.widget.PopupDrawerLayout;


/**
 * Description: 带Drawer的弹窗
 * Pop-up window with Drawer
 */
public abstract class DrawerPopupView extends BasePopupView {
    PopupDrawerLayout drawerLayout;
    protected FrameLayout drawerContentContainer;

    public DrawerPopupView(@NonNull Context context) {
        super(context);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerContentContainer = findViewById(R.id.drawerContentContainer);
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), drawerContentContainer, false);
        drawerContentContainer.addView(contentView);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout.x_pop_drawer_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        drawerLayout.enableShadow = popupInfo.hasShadowBg;
        drawerLayout.isCanClose = popupInfo.isDismissOnTouchOutside;
        drawerLayout.setOnCloseListener(new PopupDrawerLayout.OnCloseListener() {
            @Override
            public void onClose() {
                DrawerPopupView.super.dismiss();
            }

            @Override
            public void onOpen() {
                DrawerPopupView.super.doAfterShow();
            }

            @Override
            public void onDismissing(float fraction) {
                drawerLayout.isDrawStatusBarShadow = popupInfo.hasStatusBarShadow;
            }
        });
        getPopupImplView().setTranslationX(popupInfo.offsetX);
        getPopupImplView().setTranslationY(popupInfo.offsetY);
        drawerLayout.setDrawerPosition(popupInfo.popupPosition == null ? PopupPosition.Left : popupInfo.popupPosition);
        drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.close();
            }
        });
    }

    @Override
    protected void doAfterShow() {
        /*
        * do nothing self
        * 本身什么都不用做
        * */
    }

    @Override
    public void doShowAnimation() {
        drawerLayout.open();
    }

    @Override
    public void doDismissAnimation() {
        drawerLayout.close();
    }

    /**
     * 动画是跟随手势发生的，所以不需要额外的动画器，因此动画时间也清零
     * Animation occurs following gestures, so no additional animators are required, so the animation time is also cleared
     * @return
     */
    @Override
    public int getAnimationDuration() {
        return 0;
    }

    @Override
    public void dismiss() {

        /*
        * 关闭Drawer，由于Drawer注册了关闭监听，会自动调用dismiss
        * Close the Drawer. Since the Drawer is registered for close listeners, the dismiss is automatically called
        * */
        drawerLayout.close();
    }

    @Override
    protected View getTargetSizeView() {
        return getPopupImplView();
    }
}
