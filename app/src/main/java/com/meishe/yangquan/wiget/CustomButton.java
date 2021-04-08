package com.meishe.yangquan.wiget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.meishe.yangquan.R;

/**
 * @author liupanfeng
 * @desc 自定义按钮 带有选中效果
 * @date 2020/11/28 13:38
 */
public class CustomButton extends AppCompatTextView {

    private Context mContext;

    public CustomButton(Context context) {
        super(context);
        init(context);
    }


    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext=context;
        this.setBackgroundResource(R.drawable.btn_unselected_bg);
        this.setTextColor(Color.parseColor("#656565"));
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected){
            this.setBackgroundResource(R.drawable.btn_selected_bg);
            this.setTextColor(mContext.getResources().getColor(R.color.mainColor));
        }else{
            this.setBackgroundResource(R.drawable.btn_unselected_bg);
            this.setTextColor(Color.parseColor("#656565"));
        }
    }
}
