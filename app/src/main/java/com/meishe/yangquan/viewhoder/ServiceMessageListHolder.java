package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceMessage;

/**
 * 服务 无线循环消息
 */
public class ServiceMessageListHolder extends BaseViewHolder {
    private TextView mTvContent;
    public ServiceMessageListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mTvContent=view.findViewById(R.id.tv_message_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof ServiceMessage){
            ServiceMessage serviceMessage= (ServiceMessage) info;
            mTvContent.setText(serviceMessage.getContent());
        }

    }


}
