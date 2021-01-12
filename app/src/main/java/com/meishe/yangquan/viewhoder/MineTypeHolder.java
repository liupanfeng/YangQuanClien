package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineTypeInfo;

public class MineTypeHolder extends BaseViewHolder {

    private TextView mTvMineType;
    private ImageView mIvMineType;
    private RelativeLayout mRlMineType;

    public MineTypeHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvMineType =view.findViewById(R.id.tv_mine_type);
        mIvMineType =view.findViewById(R.id.iv_mine_type);
        mRlMineType =view.findViewById(R.id.ll_mine_type);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        MineTypeInfo mineTypeInfo= (MineTypeInfo) info;
        mTvMineType.setText(mineTypeInfo.getName());
        mIvMineType.setBackgroundResource(mineTypeInfo.getIcon());
        mRlMineType.setOnClickListener(listener);
        mRlMineType.setTag(info);
    }


}
