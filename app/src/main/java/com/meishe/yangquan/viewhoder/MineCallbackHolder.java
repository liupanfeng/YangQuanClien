package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineCallbackInfo;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.SystemMessageInfo;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.UserManager;

/**
 * 我的-我的建议留言
 *
 * @author 86188
 */
public class MineCallbackHolder extends BaseViewHolder {


    /*反馈消息标题*/
    private TextView tv_system_message_title;
    /*反馈消息内容*/
    private TextView tv_system_message_content;
    /*反馈消息发布时间*/
    private TextView tv_time;
    /*头像*/
    private ImageView iv_callback_message;


    public MineCallbackHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_system_message_title = view.findViewById(R.id.tv_callback_message_title);
        tv_system_message_content = view.findViewById(R.id.tv_callback_message_content);
        iv_callback_message = view.findViewById(R.id.iv_callback_message);
        tv_time = view.findViewById(R.id.tv_time);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineCallbackInfo) {
            UserInfo user = UserManager.getInstance(context).getUser();
            if (user!=null){
                tv_system_message_title.setText(user.getNickname() + "");
                GlideUtil.getInstance().loadPhotoUrl(user.getIconUrl(),iv_callback_message);
            }
            tv_time.setText(FormatDateUtil.longToString(((MineCallbackInfo) info).getInitDate(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            tv_system_message_content.setText(((MineCallbackInfo) info).getContent());

        }

    }


}
