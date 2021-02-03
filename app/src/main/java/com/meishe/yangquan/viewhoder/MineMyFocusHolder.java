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
import com.meishe.yangquan.bean.MineMyFansInfo;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.SystemMessageInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 我的-我的关注
 *
 * @author 86188
 */
public class MineMyFocusHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*粉丝头像*/
    private ImageView iv_photo;
    /*粉丝名称*/
    private TextView tv_name;
    /*粉丝容器*/
    private LinearLayout ll_sheep_bar_focus;



    public MineMyFocusHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        iv_photo = view.findViewById(R.id.iv_photo);
        tv_name = view.findViewById(R.id.tv_name);
        ll_sheep_bar_focus = view.findViewById(R.id.ll_sheep_bar_focus);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineMyFocusInfo) {
            tv_name.setText(((MineMyFocusInfo) info).getNickname());

            ll_sheep_bar_focus.setTag(info);
            ll_sheep_bar_focus.setOnClickListener(listener);

            Glide.with(context).asBitmap().
                    load(((MineMyFocusInfo) info).getIconUrl())
                    .apply(options)
                    .into(iv_photo);
        }

    }


}
