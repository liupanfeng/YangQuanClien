package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineTypeInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;

public class MineTypeHolder extends BaseViewHolder {

    private TextView mTvServiceType;
    private ImageView mIvServiceType;
    private RelativeLayout mRlServiceType;

    public MineTypeHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvServiceType=view.findViewById(R.id.tv_mine_type);
        mIvServiceType=view.findViewById(R.id.iv_mine_type);
        mRlServiceType=view.findViewById(R.id.ll_mine_type);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        MineTypeInfo mineTypeInfo= (MineTypeInfo) info;
        mTvServiceType.setText(mineTypeInfo.getName());
        mIvServiceType.setBackgroundResource(mineTypeInfo.getIcon());
        mRlServiceType.setOnClickListener(listener);
        mRlServiceType.setTag(info);
    }


}
