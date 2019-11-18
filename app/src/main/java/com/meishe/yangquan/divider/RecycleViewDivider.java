package com.meishe.yangquan.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;


/**
 * Created by lpf on 2016/9/12.
 */
public class RecycleViewDivider extends BaseDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private BaseRecyclerAdapter mAdapter;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public RecycleViewDivider(BaseRecyclerAdapter adapter, int mSpace, Context context, int orientation) {
        super(adapter,mSpace);
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        mDividerHeight=mSpace;
        mAdapter=adapter;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.divide));
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

//
//    @Override
//    protected void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position) {
//        BaseInfo info=mAdapter.getItem(position);
//        if (info==null)return;
//        if (position<1){
//            if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_DISCOVER_VIDEO_TOP){
//                outRect.set(0, 0, 0, mDividerHeight);
//            } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_APP_GIFT_LIST_TOP_BANNER){
//                outRect.set(0, 0, 0, mDividerHeight);
//            }
//            return;
//        }
//        if (info instanceof AppInfo){
//            outRect.set(0, 0, 0, 0);
//        } else if (info instanceof GIftInfo){
//            outRect.set(0, 0, 0, 0);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_CLASSIFY_INTO){
//            outRect.set(0, 0, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_AD){
//            outRect.set(0, 0, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_TOP_PIC){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_BOTTOM_PIC){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_MID_PIC){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_MENU_SWITCH){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_TOP_PIC_SCREENSHOT){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info instanceof SubjectInfo && info.getShowtype()==SubjectInfo.VIEW_TYPE_HOT_WORD_INNER){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if (info.getShowtype() == BaseInfo.VISIBLE_TYPE_HOTWORD_ICON){
//            outRect.set(mDividerHeight, mDividerHeight, mDividerHeight, mDividerHeight);
//        } else if (info.getShowtype() == BaseInfo.VISIBLE_TYPE_HOTWORD_TITLE){
//            outRect.set(mDividerHeight, mDividerHeight, mDividerHeight, mDividerHeight);
//        } else if (info instanceof ClassifyInfo && ((ClassifyInfo) info).getType()==0){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_GAME_SELECT) {
//            outRect.set(0, 0, 0, mDividerHeight);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_CLASSIFY_NO_PIC_INNER){
//            outRect.set(0, mDividerHeight, 0, 0);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_SEARCH_SCREENSHOT){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_SEARCH_WITH_GIFT){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_DISCOVER_VIDEO_TOP){
//            outRect.set(0, 0, 0, mDividerHeight);
//        } else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_DISCOVER_CIRCLE){
//            outRect.set(0, mDividerHeight, 0, 0);
//        }else if(info instanceof  SubjectInfo && info.getShowtype() == SubjectInfo.VIEW_TYPE_APP_GIFT_LIST_TOP_BANNER){
//            outRect.set(0, mDividerHeight, 0, mDividerHeight);
//        }else{
//            outRect.set(0, 0, 0, 0);
//        }
//
//
//    }
}
