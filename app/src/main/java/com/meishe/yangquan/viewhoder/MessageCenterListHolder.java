package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SystemNotification;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 *  消息中心
 */
public class MessageCenterListHolder extends BaseViewHolder {

    private TextView tv_message_center_time;
    private TextView tv_message_center_content;

    public MessageCenterListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        tv_message_center_time=view.findViewById(R.id.tv_message_center_time);
        tv_message_center_content=view.findViewById(R.id.tv_message_center_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof SystemNotification){
            SystemNotification notification= (SystemNotification) info;
            long createTime=notification.getCreateTime();
            if (createTime>0){
                tv_message_center_time.setText(FormatDateUtil.longToString(createTime, FormatDateUtil.FORMAT_TYPE));
            }
            tv_message_center_content.setText(notification.getContent());
        }
    }


}
