package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.PointRecordInfo;
import com.meishe.yangquan.bean.SystemMessageInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的-系统消息
 *
 * @author 86188
 */
public class MineSystemMessageHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*系统消息标题*/
    private TextView tv_system_message_title;
    /*系统消息内容*/
    private TextView tv_system_message_content;
    /*系统消息发布时间*/
    private TextView tv_time;



    public MineSystemMessageHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_system_message_title = view.findViewById(R.id.tv_system_message_title);
        tv_system_message_content = view.findViewById(R.id.tv_system_message_content);
        tv_time = view.findViewById(R.id.tv_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SystemMessageInfo) {
            tv_system_message_title.setText(((SystemMessageInfo) info).getTitle());
            tv_time.setText(FormatDateUtil.longToString(((SystemMessageInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            tv_system_message_content.setText(((SystemMessageInfo) info).getContent());
        }

    }


}
