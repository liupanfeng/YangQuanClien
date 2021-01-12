package com.meishe.libbase.pop.core;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.meishe.libbase.R;
import com.meishe.libbase.pop.animator.PopupAnimator;
import com.meishe.libbase.pop.animator.ScaleAlphaAnimator;
import com.meishe.libbase.pop.util.XPopupUtils;

import static com.meishe.libbase.pop.enums.PopupAnimation.ScaleAlphaFromCenter;

/**
 * Description: 在中间显示的Popup
 * Popup shown in the middle
 *
 */
public class CenterPopupView extends BasePopupView {
    protected FrameLayout centerPopupContainer;
    protected int bindLayoutId;
    protected int bindItemLayoutId;

    public CenterPopupView(@NonNull Context context) {
        super(context);
        centerPopupContainer = findViewById(R.id.centerPopupContainer);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout.x_pop_center_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), centerPopupContainer, false);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        params.gravity = Gravity.CENTER;
        centerPopupContainer.addView(contentView, params);
        getPopupContentView().setTranslationX(popupInfo.offsetX);
        getPopupContentView().setTranslationY(popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTranslationY(0);
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
        return popupInfo.maxWidth==0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.86f)
                : popupInfo.maxWidth;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), ScaleAlphaFromCenter);
    }
}
