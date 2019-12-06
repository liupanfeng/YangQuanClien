package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Label;
import com.meishe.yangquan.bean.SheepNews;

/**
 *  标签
 */
public class ServiceLabelHolder extends BaseViewHolder {

    private TextView mTvServiceLabelName;

    public ServiceLabelHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvServiceLabelName=view.findViewById(R.id.tv_label_name);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        if (info instanceof Label){
            Label label= (Label) info;
            mTvServiceLabelName.setText(label.getContent());
        }
    }


}
