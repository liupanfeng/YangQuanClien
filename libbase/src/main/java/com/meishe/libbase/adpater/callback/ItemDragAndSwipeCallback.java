package com.meishe.libbase.adpater.callback;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.libbase.R;
import com.meishe.libbase.adpater.BaseItemDraggableAdapter;
import com.meishe.libbase.adpater.BaseQuickAdapter;

/**
 * The type Item drag and swipe callback.
 * 项目拖拽和滑动回调类
 * @author luoxw
 * @date 2016 /6/20
 */
public class ItemDragAndSwipeCallback extends ItemTouchHelper.Callback {

//    private static final String TAG = ItemDragAndSwipeCallback.class.getSimpleName();

    private BaseItemDraggableAdapter mAdapter;

    private float mMoveThreshold = 0.1f;
    private float mSwipeThreshold = 0.7f;

    private int mDragMoveFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private int mSwipeMoveFlags = ItemTouchHelper.END;

    public ItemDragAndSwipeCallback(BaseItemDraggableAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mAdapter.isItemSwipeEnable();
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG
                && !isViewCreateByAdapter(viewHolder)) {
            mAdapter.onItemDragStart(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, true);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                && !isViewCreateByAdapter(viewHolder)) {
            mAdapter.onItemSwipeStart(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (isViewCreateByAdapter(viewHolder)) {
            return;
        }

        if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support) != null
                && (Boolean) viewHolder.itemView.getTag(R.id.BaseQuickAdapter_dragging_support)) {
            mAdapter.onItemDragEnd(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_dragging_support, false);
        }
        if (viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support) != null
                && (Boolean) viewHolder.itemView.getTag(R.id.BaseQuickAdapter_swiping_support)) {
            mAdapter.onItemSwipeClear(viewHolder);
            viewHolder.itemView.setTag(R.id.BaseQuickAdapter_swiping_support, false);
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return makeMovementFlags(0, 0);
        }

        return makeMovementFlags(mDragMoveFlags, mSwipeMoveFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return source.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        mAdapter.onItemDragMoving(source, target);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (!isViewCreateByAdapter(viewHolder)) {
            mAdapter.onItemSwiped(viewHolder);
        }
    }

    @Override
    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return mMoveThreshold;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return mSwipeThreshold;
    }

    /**
     * Set the fraction that the user should move the View to be considered as swiped.
     * The fraction is calculated with respect to RecyclerView's bounds.
     * <p>
     * Default value is .5f, which means, to swipe a View, user must move the View at least
     * half of RecyclerView's width or height, depending on the swipe direction.
     *
     * 将用户应该移动视图的比例设置为被滑动。
     * *这个分数是根据RecyclerView的边界计算的。
     *  < p >
     * 默认值为。5f，这意味着用户至少要移动视图才能滑动视图
     * 一半的宽度或高度，取决于滑动方向。
     * @param swipeThreshold A float value that denotes the fraction of the View size. Default value  is .8f .
     *                       表示视图大小的分数的浮点值。默认值是.8f。
     */
    public void setSwipeThreshold(float swipeThreshold) {
        mSwipeThreshold = swipeThreshold;
    }


    /**
     * Set the fraction that the user should move the View to be considered as it is
     * dragged. After a view is moved this amount, ItemTouchHelper starts checking for Views
     * below it for a possible drop.
     *
     * 设置用户应该移动视图的比例
     * 拖。在视图移动了这个量之后，ItemTouchHelper开始检查视图
     * 低于它可能下降。
     * @param moveThreshold A float value that denotes the fraction of the View size. Default value is .1f .
     *                      表示视图大小的分数的浮点值。默认值为。1f。
     */
    public void setMoveThreshold(float moveThreshold) {
        mMoveThreshold = moveThreshold;
    }

    /**
     * <p>Set the drag movement direction.</p>
     * <p>The value should be ItemTouchHelper.UP, ItemTouchHelper.DOWN, ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT or their combination.</p>
     * You can combine them like ItemTouchHelper.UP | ItemTouchHelper.DOWN, it means that the item could only move up and down when dragged.
     *
     * 设置拖动移动方向。</p
     * 值应该是ItemTouchHelper。ItemTouchHelper。下来,ItemTouchHelper。离开了,ItemTouchHelper。对或它们的组合，
     *
     * *你可以像ItemTouchHelper一样组合它们。起 ItemTouchHelper。向下，这意味着项目只能在拖动时上下移动。
     * @param dragMoveFlags the drag movement direction. Default value is ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT.
     *                      拖动移动方向。默认值是ItemTouchHelper。起| ItemTouchHelper。下来| ItemTouchHelper。左| ItemTouchHelper.RIGHT。
     */
    public void setDragMoveFlags(int dragMoveFlags) {
        mDragMoveFlags = dragMoveFlags;
    }

    /**
     * <p>Set the swipe movement direction.</p>
     * <p>The value should be ItemTouchHelper.START, ItemTouchHelper.END or their combination.</p>
     * You can combine them like ItemTouchHelper.START | ItemTouchHelper.END, it means that the item could swipe to both left or right.
     *
     * 设置滑动移动方向。</p
     * 值应该是ItemTouchHelper。首先,ItemTouchHelper。结束或他们的组合
     * 你可以像ItemTouchHelper一样组合它们。开始| ItemTouchHelper。结束，这意味着物品可以向左或向右滑动。
     * @param swipeMoveFlags the swipe movement direction. Default value is ItemTouchHelper.END.
     *                       滑动方向。默认值是ItemTouchHelper.END。
     */
    public void setSwipeMoveFlags(int swipeMoveFlags) {
        mSwipeMoveFlags = swipeMoveFlags;
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder.itemView;

            c.save();
            if (dX > 0) {
                c.clipRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom());
                c.translate(itemView.getLeft(), itemView.getTop());
            } else {
                c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                c.translate(itemView.getRight() + dX, itemView.getTop());
            }

            mAdapter.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            c.restore();

        }
    }

    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        return type == BaseQuickAdapter.HEADER_VIEW || type == BaseQuickAdapter.LOADING_VIEW
                || type == BaseQuickAdapter.FOOTER_VIEW || type == BaseQuickAdapter.EMPTY_VIEW;
    }
}
