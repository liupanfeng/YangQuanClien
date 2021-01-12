package com.meishe.libbase.adpater.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.meishe.libbase.adpater.BaseSectionQuickAdapter;
import com.meishe.libbase.adpater.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应。<br>
 * 配合Brvah的Section使用，不对Head生效，仅对每个Head的子Grid列表生效<br>
 * Section Grid中Item的宽度应设为MATCH_PARAENT
 *
 * For the static GridLayoutManager of RecyclerView, the interval size should be fixed horizontally so that the item width can be adaptive.
 *
 * with Brvah's Section, it does not take effect for a Head, but only for a subgrid list for each Head
 *
 * The width of Item in Section Grid should be set to MATCH_PARAENT
 * @author : renpeng
 * @since : 2018/9/29 lhz:如果有问题请修改
 */
public class GridSectionAverageGapItemDecoration extends RecyclerView.ItemDecoration {

    private class Section {
        private int startPos = 0;
        private int endPos = 0;

        private int getCount() {
            return endPos - startPos + 1;
        }

        private boolean contains(int pos) {
            return pos >= startPos && pos <= endPos;
        }

        @NonNull
        @Override
        public String toString() {
            return "Section{" +
                    "startPos=" + startPos +
                    ", endPos=" + endPos +
                    '}';
        }
    }

    private int gapHSizePx;
    private int gapVSizePx;
    private int sectionEdgeVPaddingPx;
    private List<Section> mSectionList = new ArrayList<>();
    private BaseSectionQuickAdapter mAdapter;
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            markSections();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            markSections();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            markSections();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            markSections();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            markSections();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            markSections();
        }
    };


    /**
     * 网格分段平均间隙项装饰
     * Mesh segment mean clearance item decoration
     * @param gapHorizontalDp       item之间的水平间距
     * @param gapVerticalDp         item之间的垂直间距
     * @param sectionEdgeVPaddingDp section上下两端的padding大小
     */
    public GridSectionAverageGapItemDecoration(float gapHorizontalDp, float gapVerticalDp, float sectionEdgeVPaddingDp) {
        gapHSizePx = dp2px(gapHorizontalDp);
        gapVSizePx = dp2px(gapVerticalDp);
        sectionEdgeVPaddingPx = dp2px(sectionEdgeVPaddingDp);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager && parent.getAdapter() instanceof BaseSectionQuickAdapter) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            BaseSectionQuickAdapter adapter = (BaseSectionQuickAdapter) parent.getAdapter();
            if (mAdapter != adapter) {
                setUpWithAdapter(adapter);
            }
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);
            SectionEntity entity = (SectionEntity) adapter.getItem(position);

            if (entity != null && entity.isHeader) {
                /*
                * 不处理header
                * Does not handle the header
                * */
                outRect.set(0, 0, 0, 0);
                return;
            }

            Section section = findSectionLastItemPos(position);
            if (section == null) {
                super.getItemOffsets(outRect, view, parent, state);
                return;
            }

            outRect.top = gapVSizePx;
            outRect.bottom = 0;

            /*
            * 下面的visualPos为单个Section内的视觉Pos
            * The visualPos below are visualPos within a single Section
            * */
            int visualPos = position + 1 - section.startPos;
            outRect.right = 0;
            if (visualPos % spanCount == 1) {
                /*
                * 第一列
                * The first column
                * */
                outRect.left = 0;
            } else {
                outRect.left = gapHSizePx;
            }

            if (visualPos - spanCount <= 0) {
                /*
                * 第一行
                * first line
                * */
                outRect.top = sectionEdgeVPaddingPx;
            }

            if (isLastRow(visualPos, spanCount, section.getCount())) {
                /*
                * 最后一行
                * last column
                * */
                outRect.bottom = sectionEdgeVPaddingPx;
            }
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    private void setUpWithAdapter(BaseSectionQuickAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mDataObserver);
        markSections();
    }

    private void markSections() {
        if (mAdapter != null) {
            BaseSectionQuickAdapter adapter = mAdapter;
            mSectionList.clear();
            Section section = new Section();
            for (int i = 0, size = adapter.getItemCount(); i < size; i++) {
                Object obj = adapter.getItem(i);
                if (obj != null && ((SectionEntity) obj).isHeader) {
                    /*
                    * 找到新Section起点
                    * Find the new Section starting point
                    * */
                    if (i != 0) {
                        /*
                        * 已经有待添加的section
                        * Section that has yet to be added
                        * */
                        section.endPos = i - 1;
                        mSectionList.add(section);
                    }
                    section = new Section();
                    section.startPos = i + 1;
                } else {
                    section.endPos = i;
                }
            }
            /*
            * 处理末尾情况
            * Handle the end case
            * */
            if (!mSectionList.contains(section)) {
                mSectionList.add(section);
            }
        }
    }

    private int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private Section findSectionLastItemPos(int curPos) {
        for (Section section : mSectionList) {
            if (section.contains(curPos)) {
                return section;
            }
        }
        return null;
    }

    private boolean isLastRow(int visualPos, int spanCount, int sectionItemCount) {
        int lastRowCount = sectionItemCount % spanCount;
        lastRowCount = lastRowCount == 0 ? spanCount : lastRowCount;
        return visualPos > sectionItemCount - lastRowCount;
    }
}
