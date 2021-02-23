package com.meishe.yangquan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/2/23 16:27
 * @Description :
 */
public class CustomButtonWhitText extends LinearLayout {

    private CheckBox btn_bu_circle;

    private TextView tv_bu_content;

    public CustomButtonWhitText(Context context) {
        super(context);
        init(context);
    }

    public CustomButtonWhitText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomButtonWhitText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_bu_custom_buttom_width_textview, this, true);

        btn_bu_circle = view.findViewById(R.id.btn_bu_circle);

        tv_bu_content = view.findViewById(R.id.tv_bu_content);


        btn_bu_circle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setCircleSelected(boolean selected) {
        btn_bu_circle.setChecked(selected);
    }

    public void setContent(String content) {
        tv_bu_content.setText(content);
    }
}
