package com.meishe.yangquan.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/18 13:52
 */
public abstract class BaseCustomLinearLayout extends LinearLayout implements View.OnClickListener {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public BaseCustomLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseCustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseCustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        initView();
        initListener();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    public void show(){
        this.setVisibility(VISIBLE);
    }

    public void hide(){
        this.setVisibility(GONE);
    }

}
