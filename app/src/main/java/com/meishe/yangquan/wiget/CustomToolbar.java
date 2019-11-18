package com.meishe.yangquan.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;


public class CustomToolbar extends Toolbar {

    private TextView toolbar_title;
    private ImageView toolbar_leftButton;
    private ImageView toolbar_rightButton;
    private View mChildView;
    private Drawable left_button_icon;
    private Drawable right_button_icon;
    private String title;


    public interface OnLeftButtonClickListener {
        void onClick();
    }

    public interface OnRightButtonClickListener {
        void onClick();

    }

    private OnLeftButtonClickListener onLeftButtonClickListener;
    private OnRightButtonClickListener onRightButtonClickListener;

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listener) {
        onLeftButtonClickListener = listener;
    }

    public void setOnRightButtonClickListener(OnRightButtonClickListener listener) {
        onRightButtonClickListener = listener;
    }

    public CustomToolbar(Context context) {
        this(context, null, 0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyleAttr, 0);
        left_button_icon = a.getDrawable(R.styleable.CustomToolbar_leftButtonIcon);
        right_button_icon = a.getDrawable(R.styleable.CustomToolbar_rightButtonIcon);
        title = a.getString(R.styleable.CustomToolbar_myTitle);
        a.recycle();
        initView();//初始化视图
        initListener();//初始化监听器
    }

    private void initListener() {
        toolbar_leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLeftButtonClickListener!=null){
                    onLeftButtonClickListener.onClick();
                }
            }
        });

        toolbar_rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightButtonClickListener!=null){
                    onRightButtonClickListener.onClick();
                }
            }
        });
    }



    private void initView() {
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.custom_toobar, null);
            toolbar_leftButton = mChildView.findViewById(R.id.toolbar_leftButton);
            toolbar_rightButton = mChildView.findViewById(R.id.toolbar_rightButton);
            toolbar_title = mChildView.findViewById(R.id.toolbar_title);
            addView(mChildView);
            if (left_button_icon != null) {
                toolbar_leftButton.setImageDrawable(left_button_icon);
            }
            if (right_button_icon != null) {
                toolbar_rightButton.setImageDrawable(right_button_icon);
            }
            if(toolbar_title != null) {
                toolbar_title.setText(title);
            }
        }
    }

    public void setMyTitle(String title){
        toolbar_title.setText(title);
    }
    public void setMyTitleColor(int color){
        toolbar_title.setTextColor(color);
    }

    public void setMyTitle(int resId){
        toolbar_title.setText(resId);
    }

    public void setMyTitleVisible(int visible){
        toolbar_title.setVisibility(visible);
    }

    public void setRightButtonVisible(int visible){
        toolbar_rightButton.setVisibility(visible);
    }

    public void setLeftButtonVisible(int visible){
        toolbar_leftButton.setVisibility(visible);
    }

    public void setRightButton(int backgroundResource){
        toolbar_rightButton.setBackgroundResource(backgroundResource);
    }

    public void setLeftButton(int backgroundResource){
        toolbar_leftButton.setBackgroundResource(backgroundResource);
    }


    /**
     * 设置左右按钮的图标
     *
     * @param d
     */
    public void setLeftButtonIconDrawable(Drawable d) {
        toolbar_leftButton.setImageDrawable(d);
    }

    public void setRightButtonIconDrawable(Drawable d) {
        toolbar_rightButton.setImageDrawable(d);
    }

}
