package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;

/**
 * 信息列表holder
 */
public class MessageListHolder extends BaseViewHolder {

    private TextView mTvServiceType;
    private ImageView mIvServiceType;
    private LinearLayout mLlServiceType;
    private Button mBtnMessageStartConnect;
    private TextView mTvMessageComment;
    private ImageView mIvMessageComment;

    public MessageListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        mIvMessageComment=view.findViewById(R.id.iv_message_comment);
        mTvMessageComment=view.findViewById(R.id.tv_message_comment);
        mBtnMessageStartConnect=view.findViewById(R.id.btn_message_start_connect);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, View.OnClickListener listener) {
//        ServerCustomer serverCustomer= (ServerCustomer) info;
//        mTvServiceType.setText(serverCustomer.getDescription());
        mBtnMessageStartConnect.setOnClickListener(listener);
        mTvMessageComment.setOnClickListener(listener);
        mIvMessageComment.setOnClickListener(listener);
        mBtnMessageStartConnect.setTag(info);
        mTvMessageComment.setTag(info);
        mIvMessageComment.setTag(info);
    }


}
