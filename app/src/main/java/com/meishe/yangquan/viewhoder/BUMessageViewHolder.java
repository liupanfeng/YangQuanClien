package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BaseInfo;

/**
 * 商版-消息
 *
 * @author 86188
 */
public class BUMessageViewHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*数量*/
    private TextView tv_bu_shop_amount;
    /*店铺名称*/
    private TextView tv_bu_shop_name;


    public BUMessageViewHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
//        tv_bu_shop_amount = view.findViewById(R.id.tv_bu_shop_amount);
//        tv_bu_shop_name = view.findViewById(R.id.tv_bu_shop_name);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
//        if (info instanceof BUShopDataInfo) {
//            tv_bu_shop_name.setText(((BUShopDataInfo) info).getName());
//            tv_bu_shop_amount.setText(((BUShopDataInfo) info).getAmount() + "");
//        }

    }


}
