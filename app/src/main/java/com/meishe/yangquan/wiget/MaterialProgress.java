package com.meishe.yangquan.wiget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;


public class MaterialProgress extends RelativeLayout {


    private int[] colors = {0xff0099cc,0xffff4444,0xff669900,0xffaa66cc,0xffff8800};
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private ImageView mImageView;
    private MaterialProgressDrawable mProgress;
    private Context mContext;

    private ValueAnimator mValueAnimator;

    private boolean mIsStart = false;

    boolean mIsVisible = false;

    boolean mIsInterrupt = false;


    public MaterialProgress(Context context) {
        super(context);
        init(context);
    }

    public MaterialProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaterialProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MaterialProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.material_progress, null);

        addView(view);

        mImageView = view.findViewById(R.id.image_view);

        mProgress = new MaterialProgressDrawable(mContext, mImageView);

        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        //圈圈颜色,可以是多种颜色
        mProgress.setColorSchemeColors(colors);
        //设置圈圈的各种大小
        mProgress.updateSizes(MaterialProgressDrawable.LARGE);

        mImageView.setImageDrawable(mProgress);
        mImageView.setVisibility(INVISIBLE);

    }


    public void show()
    {
        hide();
        mIsInterrupt = false;
        mImageView.setVisibility(VISIBLE);
        if(mValueAnimator == null)
        {
            mValueAnimator = ValueAnimator.ofFloat(0f,1f);
            mValueAnimator.setDuration(600);
            mValueAnimator.setInterpolator(new DecelerateInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float n = (float) animation.getAnimatedValue();
                    //圈圈的旋转角度
                    mProgress.setProgressRotation(n * 0.5f);
                    //圈圈周长，0f-1F
                    mProgress.setStartEndTrim(0f, n * 0.8f);
                    //箭头大小，0f-1F
                    mProgress.setArrowScale(n);
                    //透明度，0-255
                    mProgress.setAlpha((int) (255 * n));
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mIsVisible = true;
                    start();

                }
            });
        }

        if(!mValueAnimator.isRunning())
        {
            if(!mIsVisible)
            {
                //是否显示箭头
                mProgress.showArrow(true);
                mValueAnimator.start();
            }
        }
    }

    private void start()
    {
        if(mIsInterrupt){
            return;
        }

        if(mIsVisible)
        {
            if (!mIsStart)
            {
                mProgress.start();
                mIsStart = true;
            }
        }
    }

    public void hide()
    {
        if(!mIsStart){
            mIsInterrupt = true;
        }else{
            mIsInterrupt = false;
        }
        mIsVisible = false;
        mProgress.stop();
        mIsStart = false;
        mImageView.setVisibility(INVISIBLE);

    }
}
