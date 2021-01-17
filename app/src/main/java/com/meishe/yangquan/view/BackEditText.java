package com.meishe.yangquan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description :
 */
public class BackEditText extends AppCompatEditText {
    public BackEditText (Context context) {
        super(context);
    }

    public BackEditText (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackEditText (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnBackListener {
        void back(TextView textView);
    }



    private OnBackListener listener;

    public void setBackListener(OnBackListener listener) {
        this.listener = listener;
    }

    @Override

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listener != null) {
                listener.back(this);
            }
        }
        return false;
    }
}