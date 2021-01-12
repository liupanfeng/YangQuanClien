package com.meishe.libbase.adpater.decoration;

import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.meishe.libbase.adpater.BaseSectionMultiItemQuickAdapter;
import com.meishe.libbase.adpater.entity.SectionMultiEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应。<br>
 * 配合Brvah的SectionMulti使用，不对Head生效，仅对每个Head的子Grid列表生效<br>
 * 使用{@link #addSectionDecoration(int, SectionDecoration)}方法来增加对应的section定义，key值对应{@link SectionMultiEntity#getItemType()}<br>
 * 使用{@link #setLastSectionBottomMarginDp(float)}来定义最后一个section中最后一行的底部间距，设置后会覆盖最后一个section的底部间距<br>
 * Section Grid中Item的宽度应设为MATCH_PARAENT<br>
 *
 * For the static GridLayoutManager of RecyclerView, the interval size should be fixed horizontally so that the item width can be adaptive.
 *
 * * for SectionMulti with Brvah, it does not take effect for heads, but only for subgrid lists for each Head
 *
 * * use the {@link #addSectionDecoration(int, SectionDecoration)} method to add the corresponding section definition. The key value corresponds to {@link SectionMultiEntity#getItemType()}
 *
 * * use {@ # link setLastSectionBottomMarginDp (float)} to define the last line of the last section at the bottom of the spacing, after setting overrides the last section at the bottom of the distance between the < br >
 * the width of Item in Section Grid should be set to MATCH_PARAENT
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class GridSectionMultiAvgGapItemDecoration extends RecyclerView.ItemDecoration {

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

    /**
     * The type Section decoration.
     * 部分装饰类
     */
    public static class SectionDecoration {
        private float gapHorizontalDp;
        private float gapVerticalDp;
        private float sectionEdgeHPaddingDp;
        private float sectionTopMarginDp;
        private float sectionBottomMarginDp;
        private int gapHSizePx = -1;
        private int gapVSizePx = -1;
        private int sectionEdgeHPaddingPx;
        /*
        * 每个条目应该在水平方向上加的padding 总大小，即=paddingLeft+paddingRight
        * The total padding that each item should get added to the horizontal direction is equal to paddingLeft+paddingRight
        * */
        private int eachItemHPaddingPx;
        private int sectionTopMarginPx;
        private int sectionBottomMarginPx;

        /**
         * 部分装饰
         * furnish partly
         *
         * @param gapHorizontalDp       item之间的水平间距
         * @param gapVerticalDp         item之间的垂直间距
         * @param sectionEdgeHPaddingDp section左右两端的padding大小
         * @param sectionEdgeVMarginDp  section上下两端的margin大小
         */
        public SectionDecoration(float gapHorizontalDp, float gapVerticalDp, float sectionEdgeHPaddingDp, float sectionEdgeVMarginDp) {
            this(gapHorizontalDp, gapVerticalDp, sectionEdgeHPaddingDp, sectionEdgeVMarginDp, sectionEdgeVMarginDp);
        }

        /**
         *  部分装饰
         *furnish partly
         *
         * @param gapHorizontalDp       item之间的水平间距
         * @param gapVerticalDp         item之间的垂直间距
         * @param sectionEdgeHPaddingDp section左右两端的padding大小
         * @param sectionTopMarginDp    section的top margin大小
         * @param sectionBottomMarginDp section的bottom margin大小
         */
        public SectionDecoration(float gapHorizontalDp, float gapVerticalDp, float sectionEdgeHPaddingDp, float sectionTopMarginDp, float sectionBottomMarginDp) {
            this.gapHorizontalDp = gapHorizontalDp;
            this.gapVerticalDp = gapVerticalDp;
            this.sectionEdgeHPaddingDp = sectionEdgeHPaddingDp;
            this.sectionTopMarginDp = sectionTopMarginDp;
            this.sectionBottomMarginDp = sectionBottomMarginDp;
        }

        private void transformGapDefinition(RecyclerView parent, int spanCount) {
            if (gapHSizePx < 0) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    parent.getDisplay().getMetrics(displayMetrics);
                }
                gapHSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapHorizontalDp, displayMetrics);
                gapVSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapVerticalDp, displayMetrics);
                sectionEdgeHPaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sectionEdgeHPaddingDp, displayMetrics);
                sectionTopMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sectionTopMarginDp, displayMetrics);
                sectionBottomMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sectionBottomMarginDp, displayMetrics);
                eachItemHPaddingPx = (sectionEdgeHPaddingPx * 2 + gapHSizePx * (spanCount - 1)) / spanCount;
            }
        }

    }


    private List<Section> mSectionList = new ArrayList<>();
    private LongSparseArray<SectionDecoration> sectionDecorationMap = new LongSparseArray<>();
    private BaseSectionMultiItemQuickAdapter baseMultiItemQuickAdapter;
    /*
    * 最后一个Section的BottomMargin，在计算最后一个seciton的底部间距时，会替换掉相应sectionDecoration中的定义
    * The BottomMargin of the last Section replaces the definition in the corresponding sectionDecoration when BottomMargin is calculated for the last sectionDecoration
    * */
    private float mLastSectionBottomMarginDp = -1;
    private int mLastSectionBottomMarginPx = -1;
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            markSectionsForMultiSectionAdapter();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            markSectionsForMultiSectionAdapter();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            markSectionsForMultiSectionAdapter();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            markSectionsForMultiSectionAdapter();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            markSectionsForMultiSectionAdapter();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            markSectionsForMultiSectionAdapter();
        }
    };

    public float getLastSectionBottomMarginDp() {
        return mLastSectionBottomMarginDp;
    }

    public void setLastSectionBottomMarginDp(float lastSectionBottomMarginDp) {
        if (lastSectionBottomMarginDp < 0) {
            throw new IllegalArgumentException("mLastSectionBottomMarginDp must >=0!");
        }
        this.mLastSectionBottomMarginDp = lastSectionBottomMarginDp;
    }

    /**
     * Add section decoration.
     * 添加部分装饰
     * @param type              the type 类型
     * @param sectionDecoration the section decoration 部分装饰
     */
    public void addSectionDecoration(int type, @NonNull SectionDecoration sectionDecoration) {
        if (sectionDecorationMap.get(type) != null) {
            throw new RuntimeException(String.format("SectionDecoration for %d already exist!", type));
        }
        sectionDecorationMap.put(type, sectionDecoration);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof BaseSectionMultiItemQuickAdapter && parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            BaseSectionMultiItemQuickAdapter adapter = (BaseSectionMultiItemQuickAdapter) parent.getAdapter();
            if (baseMultiItemQuickAdapter != adapter) {
                setUpWithMultiItemAdapter(adapter);
            }
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);
            SectionMultiEntity entity = (SectionMultiEntity) adapter.getItem(position);

            SectionDecoration sectionDecoration = entity != null ? sectionDecorationMap.get(entity.getItemType()) : null;
            if ((entity != null && entity.isHeader) || sectionDecoration == null) {
                /*
                * 不处理header
                * Does not handle the header
                * */
                outRect.set(0, 0, 0, 0);
//                Log.w("GridAverageGapItem", "pos=" + position + "," + outRect.toShortString());
                return;
            }
            if (mLastSectionBottomMarginDp >= 0) {
                mLastSectionBottomMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mLastSectionBottomMarginDp, parent.getResources().getDisplayMetrics());
            }
            sectionDecoration.transformGapDefinition(parent, spanCount);
            Section section = findSectionByPosition(position);
            if (section == null) {
                super.getItemOffsets(outRect, view, parent, state);
                return;
            }
            outRect.top = sectionDecoration.gapVSizePx;
            outRect.bottom = 0;

            /*
            * 下面的visualPos为单个Section内的视觉Pos
            * The visualPos below are visualPos within a single Section
            * */
            int visualPos = position + 1 - section.startPos;
            if (visualPos % spanCount == 1) {
                /*
                * 第一列
                * The first column
                *
                * */
                outRect.left = sectionDecoration.sectionEdgeHPaddingPx;
                outRect.right = sectionDecoration.eachItemHPaddingPx - sectionDecoration.sectionEdgeHPaddingPx;
            } else if (visualPos % spanCount == 0) {
                /*
                * 最后一列
                * Last column
                * */
                outRect.left = sectionDecoration.eachItemHPaddingPx - sectionDecoration.sectionEdgeHPaddingPx;
                outRect.right = sectionDecoration.sectionEdgeHPaddingPx;
            } else {
                outRect.left = sectionDecoration.gapHSizePx - (sectionDecoration.eachItemHPaddingPx - sectionDecoration.sectionEdgeHPaddingPx);
                outRect.right = sectionDecoration.eachItemHPaddingPx - outRect.left;
            }

            if (visualPos - spanCount <= 0) {
                /*
                * 第一行
                * first line
                * */
                outRect.top = sectionDecoration.sectionTopMarginPx;
            }

            if (isLastRow(visualPos, spanCount, section.getCount())) {
                /*
                * 最后一行
                * last column
                * */
                if (mLastSectionBottomMarginPx >= 0 && mSectionList.indexOf(section) == mSectionList.size() - 1) {
                    /*
                    * 最后一个section
                    * The last section
                    * */
                    outRect.bottom = mLastSectionBottomMarginPx;
                } else {
                    outRect.bottom = sectionDecoration.sectionBottomMarginPx;
                }
//                Log.w("GridAverageGapItem", "last row " + position);
            }
//            Log.w("GridAverageGapItem", "pos=" + position + ",vPos=" + visualPos + "," + outRect.toShortString());
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }

    }

    private void setUpWithMultiItemAdapter(BaseSectionMultiItemQuickAdapter adapter) {
        if (baseMultiItemQuickAdapter != null) {
            baseMultiItemQuickAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        baseMultiItemQuickAdapter = adapter;
        baseMultiItemQuickAdapter.registerAdapterDataObserver(mDataObserver);
        markSectionsForMultiSectionAdapter();
    }

    private void markSectionsForMultiSectionAdapter() {
        if (baseMultiItemQuickAdapter != null) {
            BaseSectionMultiItemQuickAdapter adapter = baseMultiItemQuickAdapter;
            mSectionList.clear();
            Section section = new Section();
            for (int i = 0, size = adapter.getItemCount(); i < size; i++) {
                Object obj = adapter.getItem(i);
                if (obj != null && ((SectionMultiEntity) obj).isHeader) {
                    /*
                    * 找到新Section起点
                    * Find the new Section starting point
                    * */
                    if (i != 0 && section.startPos != i) {
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
            // Log.w("GridAverageGapItem", "section list=" + mSectionList);
        }
    }


    private Section findSectionByPosition(int curPos) {
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
