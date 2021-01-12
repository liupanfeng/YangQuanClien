package com.meishe.yangquan.wiget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 自定义文案 带有选中效果
 * @date 2020/11/28 13:38
 */
public class CustomTextView extends AppCompatTextView {

    private Context mContext;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext=context;
        this.setTextColor(Color.parseColor("#656565"));
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected){
            this.setTextColor(mContext.getResources().getColor(R.color.mainColor));
        }else{
            this.setTextColor(Color.parseColor("#656565"));
        }
    }
}
