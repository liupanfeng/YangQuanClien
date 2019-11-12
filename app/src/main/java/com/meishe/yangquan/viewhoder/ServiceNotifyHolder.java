package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceNotifyInfo;

public class ServiceNotifyHolder extends BaseViewHolder {
    private TextView mTvContent;
    public ServiceNotifyHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvContent=view.findViewById(R.id.tv_notify_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
        ServiceNotifyInfo notifyInfo= (ServiceNotifyInfo) info;
        mTvContent.setText(notifyInfo.getContent());
    }


}
