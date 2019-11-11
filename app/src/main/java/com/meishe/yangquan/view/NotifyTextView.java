package com.meishe.yangquan.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class NotifyTextView extends TextView {

    public NotifyTextView(Context context) {
        this(context, null, 0);
    }

    public NotifyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotifyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scrollHandler = new ScrollHandler();

        textPaint = this.getPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.white));
        initTextHeight();
        initPaddingValue();
        initScrollAnim();
    }

    public void setDataList(List<ScrollNotify> dataList) {
        this.dataList = dataList;
        if (dataList != null && dataList.size() > 0) {
            this.dataList.add(dataList.get(0));
        }
        postInvalidate();
        sendScrollMessage(SCROLL_INTERNAL);
    }

    public void pauseScrollView() {
        isScrolling = false;
        if (scrollHandler != null)
            scrollHandler.removeMessages(SCROLL_WHAT);
    }

    public void restartScrollView() {
        if (!isScrolling) {
            sendScrollMessage(SCROLL_INTERNAL);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            height = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (dataList == null || dataList.size() == 0) {
            super.onDraw(canvas);
        } else {
            int i = 0;
            for (ScrollNotify notify : dataList) {
                canvas.drawText(notify.text, paddingLeft, (i + 0.5f) * height + textHeight / 2 - bottomY, textPaint);
                i++;
            }
        }
    }

    private void sendScrollMessage(long delayTimeInMills) {
        isScrolling = true;
        scrollHandler.removeMessages(SCROLL_WHAT);
        scrollHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    private void initScrollAnim() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(SCROLL_TIME);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float franction = (float) animation.getAnimatedValue();
                    int scrollY = currentScrollY + (int) (franction * height);
                    scrollTo(0, scrollY);
                }
            });
        }
    }

    private void initTextHeight() {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textHeight = (int) (fm.descent - fm.ascent);
        bottomY = (int) fm.descent;
    }

    private void initPaddingValue() {
        int marginLeft = 0;
        if (getLayoutParams() != null && getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLeft = params.leftMargin;
        }
        paddingLeft = getPaddingLeft() + marginLeft;
    }

    private class ScrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_WHAT:
                    scrollOnce();
                    sendScrollMessage(SCROLL_INTERNAL);
                default:
                    break;
            }
        }

        private void scrollOnce() {
            int scollerY = NotifyTextView.this.getScrollY();
            if (dataList != null && scollerY >= height * (dataList.size() - 1)) {
                scrollTo(0, 0);
            }
            currentScrollY = getScrollY();
            if (valueAnimator != null) {
                valueAnimator.start();
            }
        }
    }


    public static class ScrollNotify {
        public String text;
        public OnClickListener onClickListener;
    }

    private ScrollHandler scrollHandler;
    private int height;
    private boolean isScrolling = false;

    private ValueAnimator valueAnimator;
    private int currentScrollY;

    private List<ScrollNotify> dataList;
    private TextPaint textPaint;
    private int bottomY;
    private int textHeight;
    private int paddingLeft;


    private static final int SCROLL_WHAT = 11;
    private static final int SCROLL_INTERNAL = 2000;
    private static final int SCROLL_TIME = 600;
}