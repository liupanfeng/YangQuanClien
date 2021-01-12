package com.meishe.libbase;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.meishe.libbase.listener.CustomTabEntity;
import com.meishe.libbase.listener.OnTabSelectListener;
import com.meishe.libbase.utils.FragmentChangeManager;
import com.meishe.libbase.utils.UnreadMsgUtils;
import com.meishe.libbase.widget.MsgView;

import java.util.ArrayList;
import java.util.List;

/**
 * 没有继承HorizontalScrollView不能滑动,对于ViewPager无依赖
 * An uninherited HorizontalScrollView cannot slide and has no dependency on a ViewPager
 */
public class CommonTabLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {
    private Context mContext;
    private ArrayList<CustomTabEntity> mTabEntityList = new ArrayList<>();
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

    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mTrianglePath = new Path();
    private static final int STYLE_NORMAL = 0;
    private static final int STYLE_TRIANGLE = 1;
    private static final int STYLE_BLOCK = 2;
    private int mIndicatorStyle = STYLE_NORMAL;

    private float mTabPadding;
    private boolean mTabSpaceEqual;
    private float mTabWidth;

    /**
     * indicator
     * 指标
     */
    private int mIndicatorColor;
    private float mIndicatorHeight;
    private float mIndicatorWidth;
    private float mIndicatorCornerRadius;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginTop;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginBottom;
    private long mIndicatorAnimDuration;
    private boolean mIndicatorAnimEnable;
    private boolean mIndicatorBounceEnable;
    private int mIndicatorGravity;

    /**
     * underline
     * 下划线
     */
    private int mUnderlineColor;
    private float mUnderlineHeight;
    private int mUnderlineGravity;

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

    /**
     * icon
     * 图标
     */
    private boolean mIconVisible;
    private int mIconGravity;
    private float mIconWidth;
    private float mIconHeight;
    private float mIconMargin;

    private int mHeight;

    /**
     * anim
     * 动画
     */
    private ValueAnimator mValueAnimator;
    private OvershootInterpolator mInterpolator = new OvershootInterpolator(1.5f);

    private FragmentChangeManager mFragmentChangeManager;

    public CommonTabLayout(Context context) {
        this(context, null, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTabLayout);

        mIndicatorStyle = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_style, 0);
        mIndicatorColor = ta.getColor(R.styleable.CommonTabLayout_tl_indicator_color, Color.parseColor(mIndicatorStyle == STYLE_BLOCK ? "#4B6A87" : "#ffffff"));
        mIndicatorHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_height,
                dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 4 : (mIndicatorStyle == STYLE_BLOCK ? -1 : 2)));
        mIndicatorWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_width, dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 10 : -1));
        mIndicatorCornerRadius = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_corner_radius, dp2px(mIndicatorStyle == STYLE_BLOCK ? -1 : 0));
        mIndicatorMarginLeft = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_left, dp2px(0));
        mIndicatorMarginTop = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_top, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorMarginRight = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_right, dp2px(0));
        mIndicatorMarginBottom = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_bottom, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorAnimEnable = ta.getBoolean(R.styleable.CommonTabLayout_tl_indicator_anim_enable, true);
        mIndicatorBounceEnable = ta.getBoolean(R.styleable.CommonTabLayout_tl_indicator_bounce_enable, true);
        mIndicatorAnimDuration = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_anim_duration, -1);
        mIndicatorGravity = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_gravity, Gravity.BOTTOM);

        mUnderlineColor = ta.getColor(R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff"));
        mUnderlineHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_underline_height, dp2px(0));
        mUnderlineGravity = ta.getInt(R.styleable.CommonTabLayout_tl_underline_gravity, Gravity.BOTTOM);

        mDividerColor = ta.getColor(R.styleable.CommonTabLayout_tl_divider_color, Color.parseColor("#ffffff"));
        mDividerWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_divider_width, dp2px(0));
        mDividerPadding = ta.getDimension(R.styleable.CommonTabLayout_tl_divider_padding, dp2px(12));

        mTextSize = ta.getDimension(R.styleable.CommonTabLayout_tl_textSize, sp2px(13f));
        mTextSelectColor = ta.getColor(R.styleable.CommonTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        mTextUnselectedColor = ta.getColor(R.styleable.CommonTabLayout_tl_textUnselectedColor, Color.parseColor("#AAffffff"));
        mTextBold = ta.getInt(R.styleable.CommonTabLayout_tl_textBold, TEXT_BOLD_NONE);
        mTextAllCaps = ta.getBoolean(R.styleable.CommonTabLayout_tl_textAllCaps, false);

        mIconVisible = ta.getBoolean(R.styleable.CommonTabLayout_tl_iconVisible, true);
        mIconGravity = ta.getInt(R.styleable.CommonTabLayout_tl_iconGravity, Gravity.TOP);
        mIconWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_iconWidth, dp2px(0));
        mIconHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_iconHeight, dp2px(0));
        mIconMargin = ta.getDimension(R.styleable.CommonTabLayout_tl_iconMargin, dp2px(2.5f));

        mTabSpaceEqual = ta.getBoolean(R.styleable.CommonTabLayout_tl_tab_space_equal, true);
        mTabWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_tab_width, dp2px(-1));
        mTabPadding = ta.getDimension(R.styleable.CommonTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(10));

        ta.recycle();
    }

    /**
     * Sets tab data.
     * 标签数据
     *
     * @param tabList the tab list
     */
    public void setTabData(List<CustomTabEntity> tabList) {
        if (tabList == null || tabList.size() == 0) {
            throw new IllegalStateException("tabList can not be NULL or EMPTY !");
        }

        this.mTabEntityList.clear();
        this.mTabEntityList.addAll(tabList);

        notifyDataSetChanged();
    }

    /**
     * 关联数据支持同时切换fragments
     * Linked data supports while switching fragments
     *
     * @param tabList         the tab list 标签表
     * @param fa              the fa   fa
     * @param containerViewId the container view id 容器视图id
     * @param fragments       the fragments  fragments
     */
    public void setTabData(List<CustomTabEntity> tabList, FragmentActivity fa, int containerViewId, List<Fragment> fragments) {
        mFragmentChangeManager = new FragmentChangeManager(fa.getSupportFragmentManager(), containerViewId, fragments);
        setTabData(tabList);
    }

    /**
     * 更新数据
     * refresh data
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTabEntityList.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            if (mIconGravity == Gravity.START) {
                tabView = View.inflate(mContext, R.layout.third_layout_tab_left, null);
            } else if (mIconGravity == Gravity.END) {
                tabView = View.inflate(mContext, R.layout.third_layout_tab_right, null);
            } else if (mIconGravity == Gravity.BOTTOM) {
                tabView = View.inflate(mContext, R.layout.third_layout_tab_bottom, null);
            } else {
                tabView = View.inflate(mContext, R.layout.third_layout_tab_top, null);
            }

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
        tv_tab_title.setText(mTabEntityList.get(position).getTabTitle());
        ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
        iv_tab_icon.setImageResource(mTabEntityList.get(position).getTabUnselectedIcon());

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
        * Layout parameters for each Tab
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

            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            if (mIconVisible) {
                iv_tab_icon.setVisibility(View.VISIBLE);
                CustomTabEntity tabEntity = mTabEntityList.get(i);
                iv_tab_icon.setImageResource(i == mCurrentTab ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        mIconWidth <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) mIconWidth,
                        mIconHeight <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : (int) mIconHeight);
                if (mIconGravity == Gravity.START) {
                    lp.rightMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.END) {
                    lp.leftMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.BOTTOM) {
                    lp.topMargin = (int) mIconMargin;
                } else {
                    lp.bottomMargin = (int) mIconMargin;
                }

                iv_tab_icon.setLayoutParams(lp);
            } else {
                iv_tab_icon.setVisibility(View.GONE);
            }
        }
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);
            tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectedColor);
            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            CustomTabEntity tabEntity = mTabEntityList.get(i);
            iv_tab_icon.setImageResource(isSelect ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
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

        /*
        * indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip
        * IndicatorWidth is less than 0, with the original Jpardogo's PagerSlidingTabStrip
        * */
        if (mIndicatorWidth < 0) {
        } else {
            /*
            * indicatorWidth大于0时,圆角矩形以及三角形
            * IndicatorWidth > 0, rounded rectangles and triangles
            * */
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        IndicatorPoint p = (IndicatorPoint) animation.getAnimatedValue();
        mIndicatorRect.left = (int) p.left;
        mIndicatorRect.right = (int) p.right;

        if (mIndicatorWidth < 0) {
            /*
            * indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip
            * IndicatorWidth is less than 0, with the original Jpardogo's PagerSlidingTabStrip
            * */

        } else {
            /*
            * indicatorWidth大于0时,圆角矩形以及三角形
            * IndicatorWidth > 0, rounded rectangles and triangles
            * */
            float indicatorLeft = p.left + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
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
        /*
        * draw divider
        * 画分频器
        * */
        if (mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
            }
        }

        /*
        * draw underline
        * 画下划线
        * */
        if (mUnderlineHeight > 0) {
            mRectPaint.setColor(mUnderlineColor);
            if (mUnderlineGravity == Gravity.BOTTOM) {
                canvas.drawRect(paddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + paddingLeft, height, mRectPaint);
            } else {
                canvas.drawRect(paddingLeft, 0, mTabsContainer.getWidth() + paddingLeft, mUnderlineHeight, mRectPaint);
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


        if (mIndicatorStyle == STYLE_TRIANGLE) {
            if (mIndicatorHeight > 0) {
                mTrianglePaint.setColor(mIndicatorColor);
                mTrianglePath.reset();
                mTrianglePath.moveTo(paddingLeft + mIndicatorRect.left, height);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.left / 2f + mIndicatorRect.right / 2f, height - mIndicatorHeight);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.right, height);
                mTrianglePath.close();
                canvas.drawPath(mTrianglePath, mTrianglePaint);
            }
        } else if (mIndicatorStyle == STYLE_BLOCK) {
            if (mIndicatorHeight < 0) {
                mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
            } else {

            }

            if (mIndicatorHeight > 0) {
                if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                    mIndicatorCornerRadius = mIndicatorHeight / 2;
                }

                mIndicatorDrawable.setColor(mIndicatorColor);
                mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                        (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                        (int) (mIndicatorMarginTop + mIndicatorHeight));
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        } else {
               /* mRectPaint.setColor(mIndicatorColor);
                calcIndicatorRect();
                canvas.drawRect(getPaddingLeft() + mIndicatorRect.left, getHeight() - mIndicatorHeight,
                        mIndicatorRect.right + getPaddingLeft(), getHeight(), mRectPaint);*/

            if (mIndicatorHeight > 0) {
                mIndicatorDrawable.setColor(mIndicatorColor);
                if (mIndicatorGravity == Gravity.BOTTOM) {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            height - (int) mIndicatorMarginBottom);
                } else {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            (int) mIndicatorMarginTop,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            (int) mIndicatorHeight + (int) mIndicatorMarginTop);
                }
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        }
    }


    /**
     * Sets current tab.
     * 设置当前标签
     * @param currentTab the current tab 当前标签
     */
    /*
    * setter and getter
    * setter和getter
    * */
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
     * Sets indicator style.
     * 设置指标风格
     *
     * @param indicatorStyle the indicator style 指标的风格
     */
    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        invalidate();
    }

    /**
     * Sets tab padding.
     * 设置标签填充
     *
     * @param tabPadding the tab padding 标签填充
     */
    public void setTabPadding(float tabPadding) {
        this.mTabPadding = dp2px(tabPadding);
        updateTabStyles();
    }

    /**
     * Sets tab space equal.
     * 设置标签空间平等
     *
     * @param tabSpaceEqual the tab space equal 标签空间平等
     */
    public void setTabSpaceEqual(boolean tabSpaceEqual) {
        this.mTabSpaceEqual = tabSpaceEqual;
        updateTabStyles();
    }

    /**
     * Sets tab width.
     * 设置标签宽度
     *
     * @param tabWidth the tab width 标签宽度
     */
    public void setTabWidth(float tabWidth) {
        this.mTabWidth = dp2px(tabWidth);
        updateTabStyles();
    }

    /**
     * Sets indicator color.
     * 设置指示灯颜色
     *
     * @param indicatorColor the indicator color 指示灯颜色
     */
    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * Sets indicator height.
     * 设置指标高
     *
     * @param indicatorHeight the indicator height 指标高
     */
    public void setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = dp2px(indicatorHeight);
        invalidate();
    }

    /**
     * Sets indicator width.
     * 设置指标的宽度
     *
     * @param indicatorWidth the indicator width 指标的宽度
     */
    public void setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = dp2px(indicatorWidth);
        invalidate();
    }

    /**
     * Sets indicator corner radius.
     * 设置指标转角半径
     *
     * @param indicatorCornerRadius the indicator corner radius 指标转角半径
     */
    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
        invalidate();
    }

    /**
     * Sets indicator gravity.
     * 设置指标重力
     *
     * @param indicatorGravity the indicator gravity 指标重力
     */
    public void setIndicatorGravity(int indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        invalidate();
    }

    /**
     * Sets indicator margin.
     * 设置指标边距
     *
     * @param indicatorMarginLeft   the indicator margin left 指标左边距
     * @param indicatorMarginTop    the indicator margin top 指标顶边距
     * @param indicatorMarginRight  the indicator margin right 指标右边距
     * @param indicatorMarginBottom the indicator margin bottom 指标底边距
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
     * 设置指标动画时间
     *
     * @param indicatorAnimDuration the indicator anim duration 指标动画时间
     */
    public void setIndicatorAnimDuration(long indicatorAnimDuration) {
        this.mIndicatorAnimDuration = indicatorAnimDuration;
    }

    /**
     * Sets indicator anim enable.
     * 设置动画指标是否开启
     *
     * @param indicatorAnimEnable the indicator anim enable 动画指标是否开启
     */
    public void setIndicatorAnimEnable(boolean indicatorAnimEnable) {
        this.mIndicatorAnimEnable = indicatorAnimEnable;
    }

    /**
     * Sets indicator bounce enable.
     * 设置指标是否反弹
     *
     * @param indicatorBounceEnable the indicator bounce enable
     */
    public void setIndicatorBounceEnable(boolean indicatorBounceEnable) {
        this.mIndicatorBounceEnable = indicatorBounceEnable;
    }

    /**
     * Sets underline color.
     * 设置下划线
     *
     * @param underlineColor the underline color 下划线颜色
     */
    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    /**
     * Sets underline height.
     * 设置下划线高度
     *
     * @param underlineHeight the underline height 下划线高度
     */
    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = dp2px(underlineHeight);
        invalidate();
    }

    /**
     * Sets underline gravity.
     * 设置下划线重力
     *
     * @param underlineGravity the underline gravity 下划线重力
     */
    public void setUnderlineGravity(int underlineGravity) {
        this.mUnderlineGravity = underlineGravity;
        invalidate();
    }

    /**
     * Sets divider color.
     * 设置分频器的颜色
     *
     * @param dividerColor the divider color 分频器的颜色
     */
    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    /**
     * Sets divider width.
     * 设置分频器宽度
     *
     * @param dividerWidth the divider width 分频器宽度
     */
    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = dp2px(dividerWidth);
        invalidate();
    }

    /**
     * Sets divider padding.
     * 设置分频器内边距
     *
     * @param dividerPadding the divider padding 分频器内边距
     */
    public void setDividerPadding(float dividerPadding) {
        this.mDividerPadding = dp2px(dividerPadding);
        invalidate();
    }

    /**
     * Sets text size.
     * 设置字体大小
     *
     * @param textSize the text size 字体大小
     */
    public void setTextSize(float textSize) {
        this.mTextSize = sp2px(textSize);
        updateTabStyles();
    }

    /**
     * Sets text select color.
     * 设置文本选择颜色
     *
     * @param textSelectColor the text select color 文本选择颜色
     */
    public void setTextSelectColor(int textSelectColor) {
        this.mTextSelectColor = textSelectColor;
        updateTabStyles();
    }

    /**
     * Sets text unselected color.
     * 设置文本没有选择颜色
     *
     * @param textUnselectedColor the text unselected color 文本没有选择颜色
     */
    public void setTextUnselectedColor(int textUnselectedColor) {
        this.mTextUnselectedColor = textUnselectedColor;
        updateTabStyles();
    }

    /**
     * Sets text bold.
     * 设置粗体文本
     *
     * @param textBold the text bold 粗体文本
     */
    public void setTextBold(int textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    /**
     * Sets icon visible.
     * 设置图标可见
     *
     * @param iconVisible the icon visible 图标可见
     */
    public void setIconVisible(boolean iconVisible) {
        this.mIconVisible = iconVisible;
        updateTabStyles();
    }

    /**
     * Sets icon gravity.
     * 设置图标重力
     *
     * @param iconGravity the icon gravity 图标重力
     */
    public void setIconGravity(int iconGravity) {
        this.mIconGravity = iconGravity;
        notifyDataSetChanged();
    }

    /**
     * Sets icon width.
     * 设置图标的宽度
     *
     * @param iconWidth the icon width 图标的宽度
     */
    public void setIconWidth(float iconWidth) {
        this.mIconWidth = dp2px(iconWidth);
        updateTabStyles();
    }

    /**
     * Sets icon height.
     * 设置图标的高度
     *
     * @param iconHeight the icon height 图标的高度
     */
    public void setIconHeight(float iconHeight) {
        this.mIconHeight = dp2px(iconHeight);
        updateTabStyles();
    }

    /**
     * Sets icon margin.
     * 设置图标边距
     *
     * @param iconMargin the icon margin 图标边距
     */
    public void setIconMargin(float iconMargin) {
        this.mIconMargin = dp2px(iconMargin);
        updateTabStyles();
    }

    /**
     * Sets text all caps.
     * 设置文本全部大写
     *
     * @param textAllCaps the text all caps 文本全部大写
     */
    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }


    /**
     * Gets tab count.
     * 获得标签数量
     *
     * @return the tab count 标签数量
     */
    public int getTabCount() {
        return mTabCount;
    }

    /**
     * Gets current tab.
     * 获得当前标签
     *
     * @return the current tab 当前标签
     */
    public int getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * Gets indicator style.
     * 获得指标的风格
     *
     * @return the indicator style 指标的风格
     */
    public int getIndicatorStyle() {
        return mIndicatorStyle;
    }

    /**
     * Gets tab padding.
     * 获得标签内边距
     *
     * @return the tab padding 标签内边距
     */
    public float getTabPadding() {
        return mTabPadding;
    }

    public boolean isTabSpaceEqual() {
        return mTabSpaceEqual;
    }

    public float getTabWidth() {
        return mTabWidth;
    }
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    public float getIndicatorHeight() {
        return mIndicatorHeight;
    }

    public float getIndicatorWidth() {
        return mIndicatorWidth;
    }

    public float getIndicatorCornerRadius() {
        return mIndicatorCornerRadius;
    }

    public float getIndicatorMarginLeft() {
        return mIndicatorMarginLeft;
    }

    public float getIndicatorMarginTop() {
        return mIndicatorMarginTop;
    }

    public float getIndicatorMarginRight() {
        return mIndicatorMarginRight;
    }

    public float getIndicatorMarginBottom() {
        return mIndicatorMarginBottom;
    }

    public long getIndicatorAnimDuration() {
        return mIndicatorAnimDuration;
    }
    public boolean isIndicatorAnimEnable() {
        return mIndicatorAnimEnable;
    }

    public boolean isIndicatorBounceEnable() {
        return mIndicatorBounceEnable;
    }

    public int getUnderlineColor() {
        return mUnderlineColor;
    }

    public float getUnderlineHeight() {
        return mUnderlineHeight;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public float getDividerWidth() {
        return mDividerWidth;
    }

    public float getDividerPadding() {
        return mDividerPadding;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getTextSelectColor() {
        return mTextSelectColor;
    }

    public int getTextUnselectedColor() {
        return mTextUnselectedColor;
    }

    public int getTextBold() {
        return mTextBold;
    }

    public boolean isTextAllCaps() {
        return mTextAllCaps;
    }

    public int getIconGravity() {
        return mIconGravity;
    }

    public float getIconWidth() {
        return mIconWidth;
    }

    public float getIconHeight() {
        return mIconHeight;
    }

    public float getIconMargin() {
        return mIconMargin;
    }

    public boolean isIconVisible() {
        return mIconVisible;
    }


    /**
     * Gets icon view.
     * 获得图标视图
     *
     * @param tab the tab 标签
     * @return the icon view  图标视图
     */
    public ImageView getIconView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        return tabView.findViewById(R.id.iv_tab_icon);
    }

    /**
     * Gets title view.
     * 获得标题的视图
     *
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
    * */

    /*
    * show MsgTipView
    * 显示MsgTipView
    * */
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SparseBooleanArray mInitSetMap = new SparseBooleanArray();

    /**
     * 显示未读消息
     * Displays unread messages
     *
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

            if (!mIconVisible) {
                setMsgMargin(position, 2, 2);
            } else {
                setMsgMargin(position, 0,
                        mIconGravity == Gravity.START || mIconGravity == Gravity.END ? 4 : 0);
            }

            mInitSetMap.put(position, true);
        }
    }

    /**
     * 显示未读红点
     * Displays unread red dots
     *
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
     *
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
     *
     * @param position      the position 位置
     * @param leftPadding   the left padding 左边内边距
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

            float iconH = mIconHeight;
            float margin = 0;
            if (mIconVisible) {
                if (iconH <= 0) {
                    iconH = mContext.getResources().getDrawable(mTabEntityList.get(position).getTabSelectedIcon()).getIntrinsicHeight();
                }
                margin = mIconMargin;
            }

            if (mIconGravity == Gravity.TOP || mIconGravity == Gravity.BOTTOM) {
                lp.leftMargin = dp2px(leftPadding);
                lp.topMargin = mHeight > 0 ? (int) (mHeight - textHeight - iconH - margin) / 2 - dp2px(bottomPadding) : dp2px(bottomPadding);
            } else {
                lp.leftMargin = dp2px(leftPadding);
                lp.topMargin = mHeight > 0 ? (int) (mHeight - Math.max(textHeight, iconH)) / 2 - dp2px(bottomPadding) : dp2px(bottomPadding);
            }

            tipView.setLayoutParams(lp);
        }
    }

    /**
     * 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置
     * The current class provides only a few methods to set unread message properties, which can be used to get the MsgView object for various Settings
     *
     * @param position the position 位置
     * @return the msg view 消息视图
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
     * 设置标签选择监听
     *
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
     * 指示器类
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
     * Dp 2 px
     *
     * @param dp the dp
     * @return the int
     */
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * Sp 2 px int.
     * Sp 2 px
     *
     * @param sp the sp
     * @return the int
     */
    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

}
