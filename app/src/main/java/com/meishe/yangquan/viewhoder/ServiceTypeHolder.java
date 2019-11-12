package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceNotifyInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;

public class ServiceTypeHolder extends BaseViewHolder {

    private TextView mTvServiceType;
    private ImageView mIvServiceType;
    private LinearLayout mLlServiceType;

    public ServiceTypeHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvServiceType=view.findViewById(R.id.tv_service_type);
        mIvServiceType=view.findViewById(R.id.iv_service_type);
        mLlServiceType=view.findViewById(R.id.ll_service_type);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        ServiceTypeInfo notifyInfo= (ServiceTypeInfo) info;
        mTvServiceType.setText(notifyInfo.getName());
        mLlServiceType.setOnClickListener(listener);
        mLlServiceType.setTag(info);
    }


}
