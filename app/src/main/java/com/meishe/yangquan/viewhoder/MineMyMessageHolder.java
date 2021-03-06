package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.MineUserMessageInfo;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.GlideUtil;

/**
 * 我的-系统消息
 *
 * @author 86188
 */
public class MineMyMessageHolder extends BaseViewHolder {


    /*系统消息标题*/
    private TextView tv_my_message_title;
    /*系统消息内容*/
    private TextView tv_my_message_content;
    /*系统消息发布时间*/
    private TextView tv_time;

    /*头像信息*/
    private ImageView iv_my_message;



    public MineMyMessageHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_my_message_title = view.findViewById(R.id.tv_my_message_title);
        tv_my_message_content = view.findViewById(R.id.tv_my_message_content);
        iv_my_message = view.findViewById(R.id.iv_my_message);
        tv_time = view.findViewById(R.id.tv_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineUserMessageInfo) {
            tv_my_message_title.setText(((MineUserMessageInfo) info).getNickname());
            tv_time.setText(FormatDateUtil.longToString(((MineUserMessageInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            tv_my_message_content.setText(((MineUserMessageInfo) info).getTitle());
            GlideUtil.getInstance().loadUrl(((MineUserMessageInfo) info).getIconUrl(),iv_my_message);
        }

    }


}
