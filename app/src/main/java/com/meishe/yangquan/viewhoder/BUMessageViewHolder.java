package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUMessageDataInfo;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BaseInfo;

/**
 * 商版-消息
 *
 * @author 86188
 */
public class BUMessageViewHolder extends BaseViewHolder {

    private TextView tv_bu_message_content;


    public BUMessageViewHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_bu_message_content = view.findViewById(R.id.tv_bu_message_content);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUMessageDataInfo) {
            tv_bu_message_content.setText(((BUMessageDataInfo) info).getMsgContent());
        }

    }


}
