package com.meishe.libbase.pop.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.TranslateAnimator;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.libbase.pop.enums.PopupAnimation;
import com.meishe.libbase.pop.util.XPopupUtils;


/**
 * Description: 宽高撑满的全屏弹窗
 * Full screen popover with full width and height
 */
public class FullScreenPopupView extends CenterPopupView {
    public FullScreenPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getMaxWidth() {
        return 0;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        popupInfo.hasShadowBg = false;
    }

    @Override
    public void onNavigationBarChange(boolean show) {
        if(!show){
            applyFull();
            getPopupContentView().setPadding(0,0,0,0);
        }else {
            applySize(true);
        }
    }

    @Override
    protected void applySize(boolean isShowNavBar) {
        int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        View contentView = getPopupContentView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.TOP;
        contentView.setLayoutParams(params);

        int actualNabBarHeight = isShowNavBar|| XPopupUtils.isNavBarVisible(getContext()) ? XPopupUtils.getNavBarHeight() : 0;
        if (rotation == 0) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(),
                    actualNabBarHeight);
        } else if (rotation == 1 || rotation == 3) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), 0);
        }
    }

    Paint paint = new Paint();
    /**
     * The Shadow rect.
     * 影子矩形
     */
    Rect shadowRect;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (popupInfo.hasStatusBarShadow) {
            paint.setColor(XPopup.statusBarShadowColor);
            shadowRect = new Rect(0, 0, XPopupUtils.getWindowWidth(getContext()), XPopupUtils.getStatusBarHeight());
            canvas.drawRect(shadowRect, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        paint = null;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), PopupAnimation.TranslateFromBottom);
    }
}
