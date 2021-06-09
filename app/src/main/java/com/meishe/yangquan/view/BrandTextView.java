package com.meishe.yangquan.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.meishe.yangquan.App;

public class BrandTextView extends androidx.appcompat.widget.AppCompatTextView {

    public BrandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BrandTextView(Context context) {
        super(context);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(App.getInstance().getFont(true));
    }

}
