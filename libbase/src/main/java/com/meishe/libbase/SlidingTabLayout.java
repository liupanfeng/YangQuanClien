package com.meishe.libbase;

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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.meishe.libbase.listener.OnTabSelectListener;
import com.meishe.libbase.utils.UnreadMsgUtils;
import com.meishe.libbase.widget.MsgView;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动TabLayout,对于ViewPager的依赖性强
 * Slide TabLayout, strong dependency on ViewPager
 */
public class SlidingTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private LinearLayout mTabsContainer;
    private int mCurrentTab;
    private float mCurrentPositionOffset;
    private int mTabCount;
    /**
     * 用于绘制显示器
     * Used to draw displays
     */
    private Rect mIndicatorRect = new Rect();
    /**
     * 用于实现滚动居中
     * Used to achieve a rolling center
     */
    private Rect mTabRect = new Rect();
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
    private int mIndicatorGravity;
    private boolean mIndicatorWidthEqualTitle;

    /**
     * underline
     * 下划线
     */
    private int mUnderlineColor;
    private float mUnderlineHeight;
    private int mUnderlineGravity;

    /**
     * divider
     * 分配器
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

    private int mLastScrollX;
    private int mHeight;
    private boolean mSnapOnTabClick;

    public SlidingTabLayout(Context context) {
        this(context, null, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
        * 设置滚动视图是否可以伸缩其内容以填充视口
        * Sets whether the scroll view can scale its contents to fill the viewport
        * */
        setFillViewport(true);
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
        * 得到layout_height
        * */
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        if (height.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (height.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            a.recycle();
        }
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout);

        mIndicatorStyle = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_style, STYLE_NORMAL);
        mIndicatorColor = ta.getColor(R.styleable.SlidingTabLayout_tl_indicator_color, Color.parseColor(mIndicatorStyle == STYLE_BLOCK ? "#4B6A87" : "#ffffff"));
        mIndicatorHeight = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_height,
                dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 4 : (mIndicatorStyle == STYLE_BLOCK ? -1 : 2)));
        mIndicatorWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_width, dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 10 : -1));
        mIndicatorCornerRadius = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_corner_radius, dp2px(mIndicatorStyle == STYLE_BLOCK ? -1 : 0));
        mIndicatorMarginLeft = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_left, dp2px(0));
        mIndicatorMarginTop = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_top, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorMarginRight = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_right, dp2px(0));
        mIndicatorMarginBottom = ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_bottom, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_gravity, Gravity.BOTTOM);
        mIndicatorWidthEqualTitle = ta.getBoolean(R.styleable.SlidingTabLayout_tl_indicator_width_equal_title, false);

        mUnderlineColor = ta.getColor(R.styleable.SlidingTabLayout_tl_underline_color, Color.parseColor("#ffffff"));
        mUnderlineHeight = ta.getDimension(R.styleable.SlidingTabLayout_tl_underline_height, dp2px(0));
        mUnderlineGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_underline_gravity, Gravity.BOTTOM);

        mDividerColor = ta.getColor(R.styleable.SlidingTabLayout_tl_divider_color, Color.parseColor("#ffffff"));
        mDividerWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_width, dp2px(0));
        mDividerPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_padding, dp2px(12));

        mTextSize = ta.getDimension(R.styleable.SlidingTabLayout_tl_textSize, sp2px(14));
        mTextSelectColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        mTextUnselectedColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textUnselectedColor, Color.parseColor("#AAffffff"));
        mTextBold = ta.getInt(R.styleable.SlidingTabLayout_tl_textBold, TEXT_BOLD_NONE);
        mTextAllCaps = ta.getBoolean(R.styleable.SlidingTabLayout_tl_textAllCaps, false);

        mTabSpaceEqual = ta.getBoolean(R.styleable.SlidingTabLayout_tl_tab_space_equal, false);
        mTabWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_width, dp2px(-1));
        mTabPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(20));

        ta.recycle();
    }

    /**
     * 关联ViewPager
     * Associated ViewPager
     * @param vp the vp
     */
    public void setViewPager(ViewPager vp) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }

        this.mViewPager = vp;

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * Update titles.
     * 更新标题
     * @param titles the titles 标题
     */
    public void updateTitles(List<String> titles) {
        if(titles != null && titles.size() > 0){
            mTitles = titles;
            if(mViewPager.getAdapter() != null){
                mViewPager.getAdapter().notifyDataSetChanged();
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况
     * Associate ViewPager for cases where you do not want to set the titles data in the ViewPager adapter
     * @param vp     the vp  vp
     * @param titles the titles 标题
     */
    public void setViewPager(ViewPager vp, List<String> titles) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }

        if (titles == null || titles.size() == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        }

        if (titles.size() != vp.getAdapter().getCount()) {
            throw new IllegalStateException("Titles length must be the same as the page count !");
        }

        this.mViewPager = vp;
        mTitles = new ArrayList<>();
        mTitles.addAll(titles);

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 关联ViewPager,用于连适配器都不想自己实例化的情况
     * Associated ViewPager for cases where the adapter does not want to instantiate itself
     * @param vp        the vp vp
     * @param titles    the titles 标题
     * @param fa        the fa fa
     * @param fragments the fragments fragments
     */
    public void setViewPager(ViewPager vp, List<String> titles, FragmentActivity fa, List<Fragment> fragments) {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL !");
        }

        if (titles == null || titles.size() == 0) {
            throw new IllegalStateException("Titles can not be EMPTY !");
        }

        this.mViewPager = vp;
        this.mViewPager.setAdapter(new InnerPagerAdapter(fa.getSupportFragmentManager(), fragments, titles));

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     * update data
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTitles == null ? mViewPager.getAdapter() == null ? mTitles.size() : mViewPager.getAdapter().getCount() : mTitles.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.third_layout_tab, null);
            CharSequence pageTitle = mTitles == null ? mViewPager.getAdapter() == null ? mTitles.get(i) : mViewPager.getAdapter().getPageTitle(i) : mTitles.get(i);
            if (pageTitle != null) {
                addTab(i, pageTitle.toString(), tabView);
            }
        }

        updateTabStyles();
    }

    /**
     * Add new tab.
     * 添加新标签
     * @param title the title 标题
     */
    public void addNewTab(String title) {
        View tabView = View.inflate(mContext, R.layout.third_layout_tab, null);
        if (mTitles != null) {
            mTitles.add(title);
        }

        CharSequence pageTitle = mTitles == null ? mViewPager.getAdapter() == null ? mTitles.get(mTabCount) : mViewPager.getAdapter().getPageTitle(mTabCount) : mTitles.get(mTabCount);

        if (pageTitle != null) {
            addTab(mTabCount, pageTitle.toString(), tabView);
        }
        this.mTabCount = mTitles == null ? mViewPager.getAdapter() == null ? mTitles.size() : mViewPager.getAdapter().getCount() : mTitles.size();

        updateTabStyles();
    }

    /**
     * 创建并添加tab
     * Create and add tabs
     */
    private void addTab(final int position, String title, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        if (tv_tab_title != null) {
            if (title != null) tv_tab_title.setText(title);
        }

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mTabsContainer.indexOfChild(v);
                if (position != -1) {
                    if (mViewPager.getCurrentItem() != position) {
                        if (mSnapOnTabClick) {
                            mViewPager.setCurrentItem(position, false);
                        } else {
                            mViewPager.setCurrentItem(position);
                        }

                        if (mListener != null) {
                            mListener.onTabSelect(position);
                        }
                    } else {
                        if (mListener != null) {
                            mListener.onTabReselect(position);
                        }
                    }
                }
            }
        });

        /**
         * 每一个Tab的布局参数
         * Layout parameters for each Tab
         *  */
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
            View v = mTabsContainer.getChildAt(i);
//            v.setPadding((int) mTabPadding, v.getPaddingTop(), (int) mTabPadding, v.getPaddingBottom());
            TextView tv_tab_title = v.findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnselectedColor);
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
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
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /*
         * position:当前View的位置
         * mCurrentPositionOffset:当前View的偏移量比例.[0,1)
         */
        this.mCurrentTab = position;
        this.mCurrentPositionOffset = positionOffset;
        scrollToCurrentTab();
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        updateTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     * HorizontalScrollView rolls to the current TAB and is centered
     */
    private void scrollToCurrentTab() {
        if (mTabCount <= 0) {
            return;
        }

        int offset = (int) (mCurrentPositionOffset * mTabsContainer.getChildAt(mCurrentTab).getWidth());
        /*
        当前Tab的left+当前Tab的Width乘以positionOffset
        Left of the current Tab + Width of the current Tab times positionOffset
        */
        int newScrollX = mTabsContainer.getChildAt(mCurrentTab).getLeft() + offset;

        if (mCurrentTab > 0 || offset > 0) {
            /*
            HorizontalScrollView移动到当前tab,并居中
            HorizontalScrollView moves to the current TAB and centers it
            */
            newScrollX -= getWidth() / 2 - getPaddingLeft();
            calcIndicatorRect();
            newScrollX += ((mTabRect.right - mTabRect.left) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            /* scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量 X and y don't represent coordinates, they represent offsets
             *  x:表示离起始位置的x水平方向的偏移量 Represents the horizontal offset of x from the starting position
             *  y:表示离起始位置的y垂直方向的偏移量 Represents the offset in the y direction from the starting position
             */
            scrollTo(newScrollX, 0);
        }
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);

            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectedColor);
                if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                    tab_title.getPaint().setFakeBoldText(isSelect);
                }
            }
        }
    }

    private float margin;

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        /*
        * for mIndicatorWidthEqualTitle
        * 对于mIndicatorWidthEqualTitle
        * */
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            TextView tab_title = (TextView) currentTabView.findViewById(R.id.tv_tab_title);
            mTextPaint.setTextSize(mTextSize);
            float textWidth = mTextPaint.measureText(tab_title.getText().toString());
            margin = (right - left - textWidth) / 2;
        }

        if (this.mCurrentTab < mTabCount - 1) {
            View nextTabView = mTabsContainer.getChildAt(this.mCurrentTab + 1);
            float nextTabLeft = nextTabView.getLeft();
            float nextTabRight = nextTabView.getRight();

            left = left + mCurrentPositionOffset * (nextTabLeft - left);
            right = right + mCurrentPositionOffset * (nextTabRight - right);

            /*
            * for mIndicatorWidthEqualTitle
            * 对于mIndicatorWidthEqualTitle
            * */
            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                TextView next_tab_title = nextTabView.findViewById(R.id.tv_tab_title);
                mTextPaint.setTextSize(mTextSize);
                float nextTextWidth = mTextPaint.measureText(next_tab_title.getText().toString());
                float nextMargin = (nextTabRight - nextTabLeft - nextTextWidth) / 2;
                margin = margin + mCurrentPositionOffset * (nextMargin - margin);
            }
        }

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;
        /*
        * for mIndicatorWidthEqualTitle
        * 对于mIndicatorWidthEqualTitle
        * */
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            mIndicatorRect.left = (int) (left + margin - 1);
            mIndicatorRect.right = (int) (right - margin - 1);
        }

        mTabRect.left = (int) left;
        mTabRect.right = (int) right;

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

            if (this.mCurrentTab < mTabCount - 1) {
                View nextTab = mTabsContainer.getChildAt(this.mCurrentTab + 1);
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2f + nextTab.getWidth() / 2f);
            }

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
    }

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

        calcIndicatorRect();
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
     * @param currentTab the current tab  当前标签
     * setter and getter
     * setter 和 getter
     */

    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        mViewPager.setCurrentItem(currentTab);

    }

    /**
     * Sets current tab.
     * 设置当前标签
     * @param currentTab   the current tab 当前标签
     * @param smoothScroll the smooth scroll 平滑上卷
     */
    public void setCurrentTab(int currentTab, boolean smoothScroll) {
        this.mCurrentTab = currentTab;
        mViewPager.setCurrentItem(currentTab, smoothScroll);
    }

    /**
     * Sets indicator style.
     * 设置指标的风格
     * @param indicatorStyle the indicator style 指标的风格
     */
    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        invalidate();
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
     * 设置标签空间是否相等
     * @param tabSpaceEqual the tab space equal 标签空间是否相等
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
     * 设置指示灯颜色
     * @param indicatorColor the indicator color 指示灯颜色
     */
    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * Sets indicator height.
     * 设置指示器的高
     * @param indicatorHeight the indicator height 指示器的高
     */
    public void setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = dp2px(indicatorHeight);
        invalidate();
    }

    public void setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = dp2px(indicatorWidth);
        invalidate();
    }

    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
        invalidate();
    }

    public void setIndicatorGravity(int indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        invalidate();
    }

    /**
     * Sets indicator margin.
     * 设置指示器边距
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
     * Sets indicator width equal title.
     * 设置指示器宽度等于标题
     * @param indicatorWidthEqualTitle the indicator width equal title 指示器宽度等于标题
     */
    public void setIndicatorWidthEqualTitle(boolean indicatorWidthEqualTitle) {
        this.mIndicatorWidthEqualTitle = indicatorWidthEqualTitle;
        invalidate();
    }

    /**
     * Sets underline color.
     * 设置下划线颜色
     * @param underlineColor the underline color 下划线颜色
     */
    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    /**
     * Sets underline height.
     * 设置下划线高度
     * @param underlineHeight the underline height 下划线高度
     */
    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = dp2px(underlineHeight);
        invalidate();
    }

    public void setUnderlineGravity(int underlineGravity) {
        this.mUnderlineGravity = underlineGravity;
        invalidate();
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = dp2px(dividerWidth);
        invalidate();
    }

    public void setDividerPadding(float dividerPadding) {
        this.mDividerPadding = dp2px(dividerPadding);
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.mTextSize = sp2px(textSize);
        updateTabStyles();
    }

    public void setTextSelectColor(int textSelectColor) {
        this.mTextSelectColor = textSelectColor;
        updateTabStyles();
    }

    public void setTextUnselectedColor(int textUnselectedColor) {
        this.mTextUnselectedColor = textUnselectedColor;
        updateTabStyles();
    }
    public void setTextBold(int textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }

    public void setSnapOnTabClick(boolean snapOnTabClick) {
        mSnapOnTabClick = snapOnTabClick;
    }


    public int getTabCount() {
        return mTabCount;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public int getIndicatorStyle() {
        return mIndicatorStyle;
    }
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

    public TextView getTitleView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        return tabView.findViewById(R.id.tv_tab_title);
    }


    /*
    * setter and getter
    * setter 和 getter
    * show MsgTipView
    * 显示MsgTipView
    * */
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SparseBooleanArray mInitSetMap = new SparseBooleanArray();

    /**
     * 显示未读消息
     * Displays unread messages
     * @param position 显示tab位置 Display TAB position
     * @param num      num小于等于0显示红点,num大于0显示数字 Num less than or equal to 0 shows red dots,num greater than 0 shows numbers
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

            setMsgMargin(position, 4, 2);
            mInitSetMap.put(position, true);
        }
    }

    /**
     * 显示未读红点
     * Displays unread red dots
     * @param position 显示tab位置 Display TAB position
     */
    public void showDot(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        showMsg(position, 0);
    }

    /**
     * 隐藏未读消息
     * Hide unread messages
     * @param position the position 位置
     */
    public void hideMsg(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            tipView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置未读消息偏移,原点为文字的右上角.当控件高度固定,消息提示位置易控制,显示效果佳
     * Sets the unread message offset with the origin at the upper right corner of the text. When the control height is fixed, the message prompt position is easy to control and displays well
     * @param position      the position 位置
     * @param leftPadding   the left padding 左边距
     * @param bottomPadding the bottom padding 底边距
     */
    public void setMsgMargin(int position, float leftPadding, float bottomPadding) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
            mTextPaint.setTextSize(mTextSize);
            float textWidth = mTextPaint.measureText(tv_tab_title.getText().toString());
            float textHeight = mTextPaint.descent() - mTextPaint.ascent();
            MarginLayoutParams lp = (MarginLayoutParams) tipView.getLayoutParams();
            lp.leftMargin = mTabWidth >= 0 ? (int) (mTabWidth / 2 + textWidth / 2 + dp2px(leftPadding)) : (int) (mTabPadding + textWidth + dp2px(leftPadding));
            lp.topMargin = mHeight > 0 ? (int) (mHeight - textHeight) / 2 - dp2px(bottomPadding) : 0;
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

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    /**
     * The type Inner pager adapter.
     * ‘内部的pager适配器
     */
    class InnerPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles;

        public InnerPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            /*
            * 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
            * Override destroyItem and empty the implementation so that views in each Fragment will not be destroyed
            * */
            // super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }
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
                scrollToCurrentTab();
            }
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * Dp 2 px int.
     * Dp 2 px
     * @param dp the dp dp
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
