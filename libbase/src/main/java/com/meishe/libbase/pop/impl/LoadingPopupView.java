package com.meishe.libbase.pop.impl;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.libbase.R;
import com.meishe.libbase.pop.core.CenterPopupView;

/**
 * Description: 加载对话框
 * Load dialog box
 */
public class LoadingPopupView extends CenterPopupView {
    private TextView tv_title;

    public LoadingPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return bindLayoutId != 0 ? bindLayoutId : R.layout.x_pop_center_impl_loading;
    }

    /**
     * 绑定已有布局
     * Bind existing layouts
     * @param layoutId 如果要显示标题，则要求必须有id为tv_title的TextView，否则无任何要求
     *                 If you want to display the title, you must have a TextView with id TV_title, otherwise nothing is required
     * @return loading popup view 加载弹出视图
     */
    public LoadingPopupView bindLayout(int layoutId){
        bindLayoutId = layoutId;
        return this;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_title = findViewById(R.id.tv_title);
        setup();
    }

    /**
     * Setup.
     * 设置
     */
    protected void setup(){
        if(title!=null && tv_title!=null){
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(title);
        }
    }

    private String title;

    /**
     * Set title loading popup view.
     * 设置标题加载弹出视图
     * @param title the title
     * @return the loading popup view
     */
    public LoadingPopupView setTitle(String title){
        this.title = title;
        setup();
        return this;
    }
}
