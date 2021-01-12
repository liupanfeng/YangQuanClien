package com.meishe.libbase.adpater.listener;

import android.os.Build;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.libbase.adpater.BaseQuickAdapter;
import com.meishe.libbase.adpater.BaseViewHolder;

import java.util.Set;

import static com.meishe.libbase.adpater.BaseQuickAdapter.EMPTY_VIEW;
import static com.meishe.libbase.adpater.BaseQuickAdapter.FOOTER_VIEW;
import static com.meishe.libbase.adpater.BaseQuickAdapter.HEADER_VIEW;
import static com.meishe.libbase.adpater.BaseQuickAdapter.LOADING_VIEW;


/**
 * <p>
 * This can be useful for applications that wish to implement various forms of click and longclick and childView click
 * manipulation of item views within the RecyclerView. SimpleClickListener may intercept
 * a touch interaction already in progress even if the SimpleClickListener is already handling that
 * gesture stream itself for the purposes of scrolling.
 * 这对于那些希望实现各种形式的单击、长单击和子视图单击的应用程序是有用的
 * *在回收视图中操作项目视图。SimpleClickListener可能拦截
 * *触摸交互已经在进行中，即使SimpleClickListener已经在处理它
 * *用于滚动的手势流本身。
 * @see RecyclerView.OnItemTouchListener
 */
public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {
    public static String TAG = "SimpleClickListener";

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;
    protected BaseQuickAdapter baseQuickAdapter;
    private boolean mIsPrepressed = false;
    private boolean mIsShowPress = false;
    private View mPressedView = null;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (recyclerView == null) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
        } else if (recyclerView != rv) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
        }
        if (!mGestureDetector.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);
                if (vh == null || !isHeaderOrFooterView(vh.getItemViewType())) {
                    mPressedView.setPressed(false);
                }
            }
            mIsShowPress = false;
            mIsPrepressed = false;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener implements GestureDetector.OnGestureListener {

        private RecyclerView recyclerView;

        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrepressed = true;
            mPressedView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                mIsShowPress = true;
            }
        }

        ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    return false;
                }
                final View pressedView = mPressedView;
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(pressedView);

                int position = vh.getAdapterPosition();
                if(position == RecyclerView.NO_POSITION){
                    return false;
                }
                if (isHeaderOrFooterPosition(position)) {
                    return false;
                }
                position -= baseQuickAdapter.getHeaderLayoutCount();

                Set<Integer> childClickViewIds = vh.getChildClickViewIds();
                Set<Integer> nestViewIds = vh.getNestViews();
                if (childClickViewIds != null && childClickViewIds.size() > 0) {
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        if (childView != null) {
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {
                                if (nestViewIds != null && nestViewIds.contains(childClickViewId)) {
                                    return false;
                                }
                                setPressViewHotSpot(e, childView);
                                childView.setPressed(true);
                                onItemChildClick(baseQuickAdapter, childView, position);
                                resetPressedView(childView);
                                return true;
                            } else {
                                childView.setPressed(false);
                            }
                        }
                    }
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        if (childView != null) {
                            childView.setPressed(false);
                        }
                    }
                    onItemClick(baseQuickAdapter, pressedView, position);
                } else {
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer childClickViewId : childClickViewIds) {
                            View childView = pressedView.findViewById(childClickViewId);
                            if (childView != null) {
                                childView.setPressed(false);
                            }
                        }
                    }
                    onItemClick(baseQuickAdapter, pressedView, position);
                }
                resetPressedView(pressedView);

            }
            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 50);
            }

            mIsPrepressed = false;
            mPressedView = null;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                return;
            }
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);
                int position = vh.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                if (!isHeaderOrFooterPosition(position)) {
                    Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();
                    Set<Integer> nestViewIds = vh.getNestViews();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        for (Integer longClickViewId : longClickViewIds) {
                            View childView = mPressedView.findViewById(longClickViewId);
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {
                                if (nestViewIds != null && nestViewIds.contains(longClickViewId)) {
                                    isChildLongClick = true;
                                    break;
                                }
                                setPressViewHotSpot(e, childView);
                                onItemChildLongClick(baseQuickAdapter, childView, position - baseQuickAdapter.getHeaderLayoutCount());
                                childView.setPressed(true);
                                mIsShowPress = true;
                                isChildLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isChildLongClick) {
                        onItemLongClick(baseQuickAdapter, mPressedView, position - baseQuickAdapter.getHeaderLayoutCount());
                        setPressViewHotSpot(e, mPressedView);
                        mPressedView.setPressed(true);
                        if (longClickViewIds != null) {
                            for (Integer longClickViewId : longClickViewIds) {
                                View childView = mPressedView.findViewById(longClickViewId);
                                if (childView != null) {
                                    childView.setPressed(false);
                                }
                            }
                        }
                        mIsShowPress = true;
                    }
                }
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void setPressViewHotSpot(final MotionEvent e, final View mPressedView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * when   click   Outside the region  ,mPressedView is null
             * 当在区域之外单击时，mPressedView为空
             */
            if (mPressedView != null && mPressedView.getBackground() != null) {
                mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * @param adapter
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    public abstract void onItemClick(BaseQuickAdapter adapter, View view, int position);

    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     * @param adapter
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    public abstract void onItemLongClick(BaseQuickAdapter adapter, View view, int position);
    /**
     * callback method to be invoked when an itemchild in this view has been click
     * @param adapter
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    public abstract void onItemChildClick(BaseQuickAdapter adapter, View view, int position);
    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     * 当已调用此视图中的项时调用的回调方法
     * 点击并保持
     * @param adapter  the adapter 适配器
     * @param view     The view whihin the AbsListView that was clicked  在被单击的AbsListView上的视图
     * @param position The position of the view int the adapter 视图在适配器中的位置
     * @return true if the callback consumed the long click ,false otherwise 如果回调占用了长时间单击，则为true，否则为false
     */
    public abstract void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position);

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view == null || !view.isShown()) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    private boolean isHeaderOrFooterPosition(int position) {
        /**
         *  have a headview and EMPTY_VIEW FOOTER_VIEW LOADING_VIEW
         *  有一个头视图和EMPTY_VIEW FOOTER_VIEW LOADING_VIEW
         */
        if (baseQuickAdapter == null) {
            if (recyclerView != null) {
                baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            } else {
                return false;
            }
        }
        int type = baseQuickAdapter.getItemViewType(position);
        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW);
    }

    private boolean isHeaderOrFooterView(int type) {
        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW);
    }
}


