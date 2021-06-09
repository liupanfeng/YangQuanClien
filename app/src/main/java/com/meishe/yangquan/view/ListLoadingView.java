package com.meishe.yangquan.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AnimationUtil;


public class ListLoadingView extends LinearLayout {

    private static final int RIGHT_MARGIN = 5;
    private static final int BOTTOM_PADDING = 50;
    private static final int FOOTER_HEIGHT = 50;
    private static final int ICON_WIDTH = 24;
    private static final int ICON_HEIGHT = 26;

    private BrandTextView title;
    private ImageView icon;

    public ListLoadingView(Context context, int height, int titleId) {
        super(context);
        initView(context, height, titleId);
    }

    private ListLoadingView(Context context) {
        super(context);
    }

    private ListLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ListLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private ListLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context mContext, int height, int titleId) {
        RecyclerView.LayoutParams params =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        AnimationUtil.dip2px(mContext, height));
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(params);
        setBackgroundColor(mContext.getResources().getColor(R.color.divide));

        LinearLayout footView = new LinearLayout(mContext);
        footView.setOrientation(LinearLayout.HORIZONTAL);
        footView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footparams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        AnimationUtil.dip2px(mContext, FOOTER_HEIGHT));
        footView.setLayoutParams(footparams);

        title = new BrandTextView(mContext);
        title.setTextColor(mContext.getResources().getColor(R.color.text_nodata));
        title.setText(titleId);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                mContext.getResources().getDimension(R.dimen.text_size_18px));
        title.setGravity(Gravity.CENTER);

        icon = new ImageView(mContext);
        icon.setImageResource(R.mipmap.ic_footer);
        LinearLayout.LayoutParams iconLayout =
                new LinearLayout.LayoutParams(AnimationUtil.dip2px(mContext, ICON_WIDTH),
                        AnimationUtil.dip2px(mContext, ICON_HEIGHT));
        iconLayout.rightMargin = AnimationUtil.dip2px(mContext, RIGHT_MARGIN);
        icon.setLayoutParams(iconLayout);

        footView.addView(icon);
        footView.addView(title);

        setPadding(0, 0, 0, AnimationUtil.dip2px(mContext, height - BOTTOM_PADDING));
        addView(footView);
    }


    public void setTitle(String text) {
        title.setText(text);
    }

    public void setTitle(int resId) {
        title.setText(getResources().getString(resId));
    }

    public void setIcon(int resId) {
        icon.setImageResource(resId);
    }
}
