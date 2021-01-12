package com.meishe.libbase;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.meishe.libbase.listener.OnTabSelectListener;
import com.meishe.libbase.utils.FragmentChangeManager;
import com.meishe.libbase.utils.UnreadMsgUtils;
import com.meishe.libbase.widget.MsgView;

import java.util.List;


/**
 * The type Segment tab layout.
 * 类型部分标签布局
 */
public class SegmentTabLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {
    private Context mContext;
    private String[] mTitles;
    private LinearLayout mTabsContainer;
    private int mCurrentTab;
    private int mLastTab;
    private int mTabCount;
    /**
     * 用于绘制显示器
     * Used to draw displays
     */
    private Rect mIndicatorRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();
    private GradientDrawable mRectDrawable = new GradientDrawable();

    private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mTabPadding;
    private boolean mTabSpaceEqual;
    private float mTabWidth;

    /**
     * indicator
     * 指标
     */
    private int mIndicatorColor;
    private float mIndicatorHeight;
    private float mIndicatorCornerRadius;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginTop;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginBottom;
    private long mIndicatorAnimDuration;
    private boolean mIndicatorAnimEnable;
    private boolean mIndicatorBounceEnable;

    /**
     * divider
     * 分频器
     */
    private int mDividerColor;
    private float mDividerWidth;
    private float mDividerPadding;

    /**
     * title
     * 标题
     */
    private static final int TEXT_BOLD_NONE = 0;
    private static final int TEXT_BOLD_WHEN_SELECT = 1;
    private static final int TEXT_BOLD_BOTH = 2;
    private float mTextSize;
    private int mTextSelectColor;
    private int mTextUnselectedColor;
    private int mTextBold;
    private boolean mTextAllCaps;

    private int mBarColor;
    private int mBarStrokeColor;
    private float mBarStrokeWidth;

    private int mHeight;

    /**
     * anim
     * 动画
     */
    private ValueAnimator mValueAnimator;
    private OvershootInterpolator mInterpolator = new OvershootInterpolator(0.8f);

    private FragmentChangeManager mFragmentChangeManager;
    private float[] mRadiusArr = new float[8];

    public SegmentTabLayout(Context context) {
        this(context, null, 0);
    }

    public SegmentTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
        * 重写onDraw方法,需要调用这个方法来清除flag
        * Override the onDraw method, which you need to call to clear the flag
        * */
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);

        this.mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);

        obtainAttributes(context, attrs);

        /*
        * get layout_height
        * 得到布局高度
        * */
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        /*
        * create ViewPager
        * 创建:viewpage
        * */
        if (height.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (height.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            a.recycle();
        }

        mValueAnimator = ValueAnimator.ofObject(new PointEvaluator(), mLastP, mCurrentP);
        mValueAnimator.addUpdateListener(this);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentTabLayout);

        mIndicatorColor = ta.getColor(R.styleable.SegmentTabLayout_tl_indicator_color, Color.parseColor("#222831"));
        mIndicatorHeight = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_height, -1);
        mIndicatorCornerRadius = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_corner_radius, -1);
        mIndicatorMarginLeft = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_left, dp2px(0));
        mIndicatorMarginTop = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_top, 0);
        mIndicatorMarginRight = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_right, dp2px(0));
        mIndicatorMarginBottom = ta.getDimension(R.styleable.SegmentTabLayout_tl_indicator_margin_bottom, 0);
        mIndicatorAnimEnable = ta.getBoolean(R.styleable.SegmentTabLayout_tl_indicator_anim_enable, false);
        mIndicatorBounceEnable = ta.getBoolean(R.styleable.SegmentTabLayout_tl_indicator_bounce_enable, true);
        mIndicatorAnimDuration = ta.getInt(R.styleable.SegmentTabLayout_tl_indicator_anim_duration, -1);

        mDividerColor = ta.getColor(R.styleable.SegmentTabLayout_tl_divider_color, mIndicatorColor);
        mDividerWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_divider_width, dp2px(1));
        mDividerPadding = ta.getDimension(R.styleable.SegmentTabLayout_tl_divider_padding, 0);

        mTextSize = ta.getDimension(R.styleable.SegmentTabLayout_tl_textSize, sp2px(13f));
        mTextSelectColor = ta.getColor(R.styleable.SegmentTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        mTextUnselectedColor = ta.getColor(R.styleable.SegmentTabLayout_tl_textUnselectedColor, mIndicatorColor);
        mTextBold = ta.getInt(R.styleable.SegmentTabLayout_tl_textBold, TEXT_BOLD_NONE);
        mTextAllCaps = ta.getBoolean(R.styleable.SegmentTabLayout_tl_textAllCaps, false);

        mTabSpaceEqual = ta.getBoolean(R.styleable.SegmentTabLayout_tl_tab_space_equal, true);
        mTabWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_tab_width, dp2px(-1));
        mTabPadding = ta.getDimension(R.styleable.SegmentTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(10));

        mBarColor = ta.getColor(R.styleable.SegmentTabLayout_tl_bar_color, Color.TRANSPARENT);
        mBarStrokeColor = ta.getColor(R.styleable.SegmentTabLayout_tl_bar_stroke_color, mIndicatorColor);
        mBarStrokeWidth = ta.getDimension(R.styleable.SegmentTabLayout_tl_bar_stroke_width, dp2px(1));

        ta.recycle();
    }

    /**
     * Sets tab data.
     * 设置标签数据
     * @param titles the titles
     */
    public void setTabData(String[] titles) {
        if (titles == null || titles.length == 0) {
            throw new IllegalStateException("Titles can not be NULL or EMPTY !");
        }

        this.mTitles = titles;

        notifyDataSetChanged();
    }

    /**
     * 关联数据支持同时切换fragments
     * Linked data supports while switching fragments
     * @param titles          the titles 标题
     * @param fa              the fa fa
     * @param containerViewId the container view id 容器视图id
     * @param fragments       the fragments fragment
     */
    public void setTabData(String[] titles, FragmentActivity fa, int containerViewId, List<Fragment> fragments) {
        mFragmentChangeManager = new FragmentChangeManager(fa.getSupportFragmentManager(), containerViewId, fragments);
        setTabData(titles);
    }

    /**
     * 更新数据
     * refresh data
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTitles.length;
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.third_layout_tab_segment, null);
            tabView.setTag(i);
            addTab(i, tabView);
        }

        updateTabStyles();
    }

    /**
     * 创建并添加tab
     * Create and add tabs
     */
    private void addTab(final int position, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(mTitles[position]);

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelect(position);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onTabReselect(position);
                    }
                }
            }
        });

        /*
        * 每一个Tab的布局参数
        *  Layout parameters for each Tab
        * */
        LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (mTabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) mTabWidth, LayoutParams.MATCH_PARENT);
        }
        mTabsContainer.addView(tabView, position, lp_tab);
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View tabView = mTabsContainer.getChildAt(i);
            tabView.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
            TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
            tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnselectedColor);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
//            tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
            if (mTextAllCaps) {
                tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
            }

            if (mTextBold == TEXT_BOLD_BOTH) {
                tv_tab_title.getPaint().setFakeBoldText(true);
            } else if (mTextBold == TEXT_BOLD_NONE) {
                tv_tab_title.getPaint().setFakeBoldText(false);
            }
        }
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);
            tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectedColor);
            if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                tab_title.getPaint().setFakeBoldText(isSelect);
            }
        }
    }

    private void calcOffset() {
        final View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        mCurrentP.left = currentTabView.getLeft();
        mCurrentP.right = currentTabView.getRight();

        final View lastTabView = mTabsContainer.getChildAt(this.mLastTab);
        mLastP.left = lastTabView.getLeft();
        mLastP.right = lastTabView.getRight();

//        Log.d("AAA", "mLastP--->" + mLastP.left + "&" + mLastP.right);
//        Log.d("AAA", "mCurrentP--->" + mCurrentP.left + "&" + mCurrentP.right);
        if (mLastP.left == mCurrentP.left && mLastP.right == mCurrentP.right) {
            invalidate();
        } else {
            mValueAnimator.setObjectValues(mLastP, mCurrentP);
            if (mIndicatorBounceEnable) {
                mValueAnimator.setInterpolator(mInterpolator);
            }

            if (mIndicatorAnimDuration < 0) {
                mIndicatorAnimDuration = mIndicatorBounceEnable ? 500 : 250;
            }
            mValueAnimator.setDuration(mIndicatorAnimDuration);
            mValueAnimator.start();
        }
    }

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;

        if (!mIndicatorAnimEnable) {
            if (mCurrentTab == 0) {
                /*
                * The corners are ordered top-left, top-right, bottom-right, bottom-left
                * 角落的顺序是左上，右上，右下，左下
                * */
                mRadiusArr[0] = mIndicatorCornerRadius;
                mRadiusArr[1] = mIndicatorCornerRadius;
                mRadiusArr[2] = 0;
                mRadiusArr[3] = 0;
                mRadiusArr[4] = 0;
                mRadiusArr[5] = 0;
                mRadiusArr[6] = mIndicatorCornerRadius;
                mRadiusArr[7] = mIndicatorCornerRadius;
            } else if (mCurrentTab == mTabCount - 1) {
                /*
                * The corners are ordered top-left, top-right, bottom-right, bottom-left
                * 角落的顺序是左上，右上，右下，左下
                * */
                mRadiusArr[0] = 0;
                mRadiusArr[1] = 0;
                mRadiusArr[2] = mIndicatorCornerRadius;
                mRadiusArr[3] = mIndicatorCornerRadius;
                mRadiusArr[4] = mIndicatorCornerRadius;
                mRadiusArr[5] = mIndicatorCornerRadius;
                mRadiusArr[6] = 0;
                mRadiusArr[7] = 0;
            } else {
               /*
               * The corners are ordered top-left, top-right, bottom-right, bottom-left
               * 角落的顺序是左上，右上，右下，左下
               * */
                mRadiusArr[0] = 0;
                mRadiusArr[1] = 0;
                mRadiusArr[2] = 0;
                mRadiusArr[3] = 0;
                mRadiusArr[4] = 0;
                mRadiusArr[5] = 0;
                mRadiusArr[6] = 0;
                mRadiusArr[7] = 0;
            }
        } else {
            /*
            * The corners are ordered top-left, top-right, bottom-right, bottom-left
            * 角落的顺序是左上，右上，右下，左下
            * */
            mRadiusArr[0] = mIndicatorCornerRadius;
            mRadiusArr[1] = mIndicatorCornerRadius;
            mRadiusArr[2] = mIndicatorCornerRadius;
            mRadiusArr[3] = mIndicatorCornerRadius;
            mRadiusArr[4] = mIndicatorCornerRadius;
            mRadiusArr[5] = mIndicatorCornerRadius;
            mRadiusArr[6] = mIndicatorCornerRadius;
            mRadiusArr[7] = mIndicatorCornerRadius;
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        IndicatorPoint p = (IndicatorPoint) animation.getAnimatedValue();
        mIndicatorRect.left = (int) p.left;
        mIndicatorRect.right = (int) p.right;
        invalidate();
    }

    private boolean mIsFirstDraw = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();

        if (mIndicatorHeight < 0) {
            mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
        }

        if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
            mIndicatorCornerRadius = mIndicatorHeight / 2;
        }

        /*
        * draw rect
        * 画矩形
        * */
        mRectDrawable.setColor(mBarColor);
        mRectDrawable.setStroke((int) mBarStrokeWidth, mBarStrokeColor);
        mRectDrawable.setCornerRadius(mIndicatorCornerRadius);
        mRectDrawable.setBounds(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mRectDrawable.draw(canvas);

        /*
        * draw divider
        * 画分频器
        * */
        if (!mIndicatorAnimEnable && mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
            }
        }


        /*
        * draw indicator line
        * 画线指标
        * */
        if (mIndicatorAnimEnable) {
            if (mIsFirstDraw) {
                mIsFirstDraw = false;
                calcIndicatorRect();
            }
        } else {
            calcIndicatorRect();
        }

        mIndicatorDrawable.setColor(mIndicatorColor);
        mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                (int) (mIndicatorMarginTop + mIndicatorHeight));
        mIndicatorDrawable.setCornerRadii(mRadiusArr);
        mIndicatorDrawable.draw(canvas);

    }

    /**
     * Sets current tab.
     * 设置当前标签
     * setter and getter
     * setter和getter
     * @param currentTab the current tab 当前标签
     */

    public void setCurrentTab(int currentTab) {
        mLastTab = this.mCurrentTab;
        this.mCurrentTab = currentTab;
        updateTabSelection(currentTab);
        if (mFragmentChangeManager != null) {
            mFragmentChangeManager.setFragments(currentTab);
        }
        if (mIndicatorAnimEnable) {
            calcOffset();
        } else {
            invalidate();
        }
    }

    /**
     * Sets tab padding.
     * 设置标签内边距
     * @param tabPadding the tab padding 标签内边距
     */
    public void setTabPadding(float tabPadding) {
        this.mTabPadding = dp2px(tabPadding);
        updateTabStyles();
    }

    /**
     * Sets tab space equal.
     * 设置标签空间相等
     * @param tabSpaceEqual the tab space equal 标签空间相等
     */
    public void setTabSpaceEqual(boolean tabSpaceEqual) {
        this.mTabSpaceEqual = tabSpaceEqual;
        updateTabStyles();
    }

    /**
     * Sets tab width.
     * 设置标签宽度
     * @param tabWidth the tab width 标签宽度
     */
    public void setTabWidth(float tabWidth) {
        this.mTabWidth = dp2px(tabWidth);
        updateTabStyles();
    }

    /**
     * Sets indicator color.
     * 设置指示器颜色
     * @param indicatorColor the indicator color 标志的颜色
     */
    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * Sets indicator height.
     * 设置指示器高
     * @param indicatorHeight the indicator height 指示器高
     */
    public void setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = dp2px(indicatorHeight);
        invalidate();
    }

    /**
     * Sets indicator corner radius.
     * 设置指示器转角半径
     * @param indicatorCornerRadius the indicator corner radius 指示器转角半径
     */
    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
        invalidate();
    }

    /**
     * Sets indicator margin.
     * 指示器边距
     * @param indicatorMarginLeft   the indicator margin left 指示器左边距
     * @param indicatorMarginTop    the indicator margin top 指示器顶边距
     * @param indicatorMarginRight  the indicator margin right 指示器右边距
     * @param indicatorMarginBottom the indicator margin bottom 指示器底边距
     */
    public void setIndicatorMargin(float indicatorMarginLeft, float indicatorMarginTop,
                                   float indicatorMarginRight, float indicatorMarginBottom) {
        this.mIndicatorMarginLeft = dp2px(indicatorMarginLeft);
        this.mIndicatorMarginTop = dp2px(indicatorMarginTop);
        this.mIndicatorMarginRight = dp2px(indicatorMarginRight);
        this.mIndicatorMarginBottom = dp2px(indicatorMarginBottom);
        invalidate();
    }

    /**
     * Sets indicator anim duration.
     * 设置指示器动画时长
     * @param indicatorAnimDuration the indicator anim duration 指示器动画时长
     */
    public void setIndicatorAnimDuration(long indicatorAnimDuration) {
        this.mIndicatorAnimDuration = indicatorAnimDuration;
    }

    /**
     * Sets indicator anim enable.
     * 设置指示器动画启动
     * @param indicatorAnimEnable the indicator anim enable 指示器动画启动
     */
    public void setIndicatorAnimEnable(boolean indicatorAnimEnable) {
        this.mIndicatorAnimEnable = indicatorAnimEnable;
    }

    /**
     * Sets indicator bounce enable.
     * 设置指示器反弹
     * @param indicatorBounceEnable the indicator bounce enable 指示器反弹
     */
    public void setIndicatorBounceEnable(boolean indicatorBounceEnable) {
        this.mIndicatorBounceEnable = indicatorBounceEnable;
    }

    /**
     * Sets divider color.
     * 设置分频器的颜色
     * @param dividerColor the divider color 分频器的颜色
     */
    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    /**
     * Sets divider width.
     * 设置分频器的宽
     * @param dividerWidth the divider width 分频器的宽
     */
    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = dp2px(dividerWidth);
        invalidate();
    }

    /**
     * Sets divider padding.
     * 设置分频器内边距
     * @param dividerPadding the divider padding 分频器内边距
     */
    public void setDividerPadding(float dividerPadding) {
        this.mDividerPadding = dp2px(dividerPadding);
        invalidate();
    }

    /**
     * Sets text size.
     * 设置文字大小
     * @param textSize the text size 文字大小
     */
    public void setTextSize(float textSize) {
        this.mTextSize = sp2px(textSize);
        updateTabStyles();
    }

    /**
     * Sets text select color.
     * 设置文字选择颜色
     * @param textSelectColor the text select color 文字选择颜色
     */
    public void setTextSelectColor(int textSelectColor) {
        this.mTextSelectColor = textSelectColor;
        updateTabStyles();
    }

    /**
     * Sets text unselected color.
     * 设置文本没有选择颜色
     * @param textUnselectedColor the text unselected color 文本没有选择颜色
     */
    public void setTextUnselectedColor(int textUnselectedColor) {
        this.mTextUnselectedColor = textUnselectedColor;
        updateTabStyles();
    }

    /**
     * Sets text bold.
     * 设置文字粗体
     * @param textBold the text bold 文字粗体
     */
    public void setTextBold(int textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    /**
     * Sets text all caps.
     * 设置文本全部大写
     * @param textAllCaps the text all caps 文本全部大写
     */
    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }

    /**
     * Gets tab count.
     * 获取标签数量
     * @return the tab count 标签数量
     */
    public int getTabCount() {
        return mTabCount;
    }

    /**
     * Gets current tab.
     * 获取当前标签
     * @return the current tab 当前标签
     */
    public int getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * Gets tab padding.
     * 获取标签内边距
     * @return the tab padding 标签内边距
     */
    public float getTabPadding() {
        return mTabPadding;
    }

    /**
     * Is tab space equal boolean.
     * 标签空间是否相等
     * @return the boolean
     */
    public boolean isTabSpaceEqual() {
        return mTabSpaceEqual;
    }

    /**
     * Gets tab width.
     * 获得标签宽度
     * @return the tab width 标签宽度
     */
    public float getTabWidth() {
        return mTabWidth;
    }

    /**
     * Gets indicator color.
     * 得到指示器的颜色
     * @return the indicator color 指示器的颜色
     */
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    /**
     * Gets indicator height.
     * 或者指示器的高度
     * @return the indicator height 指示器的高度
     */
    public float getIndicatorHeight() {
        return mIndicatorHeight;
    }

    /**
     * Gets indicator corner radius.
     * 获取指示器转角半径
     * @return the indicator corner radius 指示器圆角半径
     */
    public float getIndicatorCornerRadius() {
        return mIndicatorCornerRadius;
    }

    /**
     * Gets indicator margin left.
     * 获取指示器左边距
     * @return the indicator margin left 指示器左边距
     */
    public float getIndicatorMarginLeft() {
        return mIndicatorMarginLeft;
    }

    /**
     * Gets indicator margin top.
     * 获取指示器边距顶部
     * @return the indicator margin top 指示器边距顶部
     */
    public float getIndicatorMarginTop() {
        return mIndicatorMarginTop;
    }

    /**
     * Gets indicator margin right.
     * 获得指示器右边距
     * @return the indicator margin right 指示器右边距
     */
    public float getIndicatorMarginRight() {
        return mIndicatorMarginRight;
    }

    /**
     * Gets indicator margin bottom.
     * 获得指示器底边距
     * @return the indicator margin bottom 指示器底边距
     */
    public float getIndicatorMarginBottom() {
        return mIndicatorMarginBottom;
    }

    /**
     * Gets indicator anim duration.
     * 获取动画持续时间指示器
     * @return the indicator anim duration 动画持续时间指示器
     */
    public long getIndicatorAnimDuration() {
        return mIndicatorAnimDuration;
    }

    /**
     * Is indicator anim enable boolean.
     * 指示器动画是否开启
     * @return the boolean
     */
    public boolean isIndicatorAnimEnable() {
        return mIndicatorAnimEnable;
    }

    /**
     * Is indicator bounce enable boolean.
     * 指示器是否开启反弹
     * @return the boolean
     */
    public boolean isIndicatorBounceEnable() {
        return mIndicatorBounceEnable;
    }

    /**
     * Gets divider color.
     * 获得分频器的颜色
     * @return the divider color 分频器的颜色
     */
    public int getDividerColor() {
        return mDividerColor;
    }

    /**
     * Gets divider width.
     * 获得分频器宽度
     * @return the divider width
     */
    public float getDividerWidth() {
        return mDividerWidth;
    }

    /**
     * Gets divider padding.
     * 获得分频器的内边距
     * @return the divider padding 分频器的内边距
     */
    public float getDividerPadding() {
        return mDividerPadding;
    }

    /**
     * Gets text size.
     * 获得文字大小
     * @return the text size 文字大小
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * Gets text select color.
     * 获取文本选择颜色
     * @return the text select color 文本选择颜色
     */
    public int getTextSelectColor() {
        return mTextSelectColor;
    }

    /**
     * Gets text unselected color.
     * 获取未选定的文本颜色
     * @return the text unselected color 未选定的文本颜色
     */
    public int getTextUnselectedColor() {
        return mTextUnselectedColor;
    }

    /**
     * Gets text bold.
     * 获得粗体文字
     * @return the text bold 粗体文字
     */
    public int getTextBold() {
        return mTextBold;
    }

    /**
     * Is text all caps boolean.
     * 文本是否全部大写
     * @return the boolean
     */
    public boolean isTextAllCaps() {
        return mTextAllCaps;
    }

    /**
     * Gets title view.
     * 标题视图
     * @param tab the tab 标签
     * @return the title view 标题视图
     */
    public TextView getTitleView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        return tabView.findViewById(R.id.tv_tab_title);
    }

    /*
    * setter and getter
    * setter和getter
    * show MsgTipView
    * 显示MsgTipView
    * */

    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SparseBooleanArray mInitSetMap = new SparseBooleanArray();

    /**
     * 显示未读消息
     * Displays unread messages
     * @param position 显示tab位置
     * @param num      num小于等于0显示红点,num大于0显示数字
     */
    public void showMsg(int position, int num) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num);

            if (mInitSetMap.get(position)) {
                return;
            }

            setMsgMargin(position, 2, 2);

            mInitSetMap.put(position, true);
        }
    }

    /**
     * 显示未读红点
     * Displays unread red dots
     * @param position 显示tab位置
     */
    public void showDot(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        showMsg(position, 0);
    }

    /**
     * Hide msg.
     * 隐藏msg
     * @param position the position 位置
     */
    public void hideMsg(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            tipView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置提示红点偏移,注意
     * 1.控件为固定高度:参照点为tab内容的右上角
     * 2.控件高度不固定(WRAP_CONTENT):参照点为tab内容的右上角,此时高度已是红点的最高显示范围,所以这时bottomPadding其实就是topPadding
     * Set prompt for red dot offset, note
     * 1. Control is fixed height: the reference point is the upper-right corner of the TAB content
     * 2. Control height is not fixed (WRAP_CONTENT): refer to the top right corner of TAB content where the height is the highest display range of the red dot so bottomPadding is actually topPadding
     * @param position      the position 位置
     * @param leftPadding   the left padding 左内边距
     * @param bottomPadding the bottom padding 底部内边距
     */
    public void setMsgMargin(int position, float leftPadding, float bottomPadding) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            // TextView tv_tab_title =  tabView.findViewById(R.id.tv_tab_title);
            mTextPaint.setTextSize(mTextSize);
            //float textWidth = mTextPaint.measureText(tv_tab_title.getText().toString());
            float textHeight = mTextPaint.descent() - mTextPaint.ascent();
            MarginLayoutParams lp = (MarginLayoutParams) tipView.getLayoutParams();

            lp.leftMargin = dp2px(leftPadding);
            lp.topMargin = mHeight > 0 ? (int) (mHeight - textHeight) / 2 - dp2px(bottomPadding) : dp2px(bottomPadding);

            tipView.setLayoutParams(lp);
        }
    }

    /**
     * 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置
     * The current class provides only a few methods to set unread message properties, which can be used to get the MsgView object for various Settings
     * @param position the position 位置
     * @return the msg view msg视图
     */
    public MsgView getMsgView(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        return tabView.findViewById(R.id.rtv_msg_tip);
    }

    private OnTabSelectListener mListener;

    /**
     * Sets on tab select listener.
     * 设置标签选择监听器
     * @param listener the listener 监听
     */
    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
            }
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * The type Indicator point.
     * 类型指示点
     */
    class IndicatorPoint {
        /**
         * The Left.
         * 左
         */
        public float left;
        /**
         * The Right.
         * 右
         */
        public float right;
    }

    private IndicatorPoint mCurrentP = new IndicatorPoint();
    private IndicatorPoint mLastP = new IndicatorPoint();

    /**
     * The type Point evaluator.
     * 类型点求值器
     */
    class PointEvaluator implements TypeEvaluator<IndicatorPoint> {
        @Override
        public IndicatorPoint evaluate(float fraction, IndicatorPoint startValue, IndicatorPoint endValue) {
            float left = startValue.left + fraction * (endValue.left - startValue.left);
            float right = startValue.right + fraction * (endValue.right - startValue.right);
            IndicatorPoint point = new IndicatorPoint();
            point.left = left;
            point.right = right;
            return point;
        }
    }

    /**
     * Dp 2 px int.
     *   Dp 2 px
     * @param dp the dp  dp
     * @return the int
     */
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Sp 2 px int.
     * Sp 2 px
     * @param sp the sp
     * @return the int
     */
    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
