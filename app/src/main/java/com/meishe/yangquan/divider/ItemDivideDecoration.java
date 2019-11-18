package com.meishe.yangquan.divider;


import com.meishe.yangquan.adapter.BaseRecyclerAdapter;


/**
 * Created by lpf on 2016/9/16.
 */
public class ItemDivideDecoration extends BaseItemDecoration {


    public ItemDivideDecoration(BaseRecyclerAdapter mAdapter, int mSpace) {
        super(mAdapter, mSpace);
    }

//    @Override
//    protected void handleItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state, int position) {
//        if (position < 0) return;
//        BaseInfo info = mAdapter.getItem(position);
//        if (info instanceof SubjectInfo) {
//            if (position < 1) return;
//            outRect.top = (int) (mSpace * 2.0 / 3);
//        } else if (info instanceof ClassifyInfo) {
//            if (info.getItemviewtype() == BaseInfo.ITEM_TYPE_LIST)
//                outRect.top = (int) (mSpace * 2.0 / 3);
//        } else if (info instanceof AppInfo) {
//            outRect.top = (int) (mSpace * 2.0 / 3);
//            outRect.left = (int) (mSpace * 2.0 / 3);
//            outRect.right = (int) (mSpace * 2.0 / 3);
//            outRect.bottom = (int) (mSpace * 2.0 / 3);
//        } else {
//            outRect.top = (int) (mSpace * 2.0 / 5);
//        }
//
//    }
}
