package com.meishe.yangquan.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.meishe.yangquan.R;


public class TitleBar extends LinearLayout implements View.OnClickListener {
    TextView textCenter, textRight;
    private RelativeLayout backLayout;
    private RelativeLayout forwardLayout;
    OnTitleBarClickListener onTitleBarClickListener;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View viewParent = mInflater.inflate(R.layout.title_bar, null);
        addView(viewParent);
        backLayout = (RelativeLayout) viewParent.findViewById(R.id.backLayout);
        backLayout.setOnClickListener(this);
        textCenter = (TextView) viewParent.findViewById(R.id.text_center);
        textCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_text_size));
        textCenter.getPaint().setFakeBoldText(true);
        textCenter.setOnClickListener(this);
        textRight = (TextView) viewParent.findViewById(R.id.text_right);
        textRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_text_size));
        textRight.getPaint().setFakeBoldText(true);
        forwardLayout = (RelativeLayout) viewParent.findViewById(R.id.forwardLayout);
        forwardLayout.setOnClickListener(this);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setTextCenter(String msg) {
        textCenter.setText(msg);
    }

    public void setTextCenter(@StringRes int resid) {
        textCenter.setText(getContext().getResources().getText(resid));
    }

    public void setTextRight(String msg) {
        textRight.setText(msg);
    }

    public void setTextRight(@StringRes int resid) {
        textRight.setText(getContext().getResources().getText(resid));
    }

    public void setTextRightVisible(int visible) {
        textRight.setVisibility(visible);
        if (visible == VISIBLE) {
            forwardLayout.setClickable(true);
        } else {
            forwardLayout.setClickable(false);
        }
    }

    public void setBackImageVisible(int visible) {
        backLayout.setVisibility(visible);
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return onTitleBarClickListener;
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backLayout:
                if (onTitleBarClickListener != null) {
                    onTitleBarClickListener.OnBackImageClick();
                }
                break;
            case R.id.text_center:
                if (onTitleBarClickListener != null) {
                    onTitleBarClickListener.OnCenterTextClick();
                }
                break;
            case R.id.forwardLayout:
                if (onTitleBarClickListener != null) {
                    onTitleBarClickListener.OnRightTextClick();
                }
                break;
            default:
                break;
        }
    }

    public interface OnTitleBarClickListener {
        void OnBackImageClick();

        void OnCenterTextClick();

        void OnRightTextClick();
    }
}
