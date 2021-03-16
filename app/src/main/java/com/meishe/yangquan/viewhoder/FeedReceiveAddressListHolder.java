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
import com.meishe.yangquan.bean.FeedReceiverAddressInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.AddSubView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 饲料-收货地址
 *
 * @author 86188
 */
public class FeedReceiveAddressListHolder extends BaseViewHolder {

    /*区域*/
    private TextView tv_feed_order_area;
    /*详细地址*/
    private TextView tv_feed_order_detail_address;

    private View iv_feed_address_edit;
    /*名称*/
    private TextView tv_feed_order_real_name;
    /*电话*/
    private TextView tv_feed_order_phone_number;

    private View view;

    public FeedReceiveAddressListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        view=itemView;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_feed_order_area = view.findViewById(R.id.tv_feed_order_area);
        tv_feed_order_detail_address = view.findViewById(R.id.tv_feed_order_detail_address);
        iv_feed_address_edit = view.findViewById(R.id.iv_feed_address_edit);
        tv_feed_order_real_name = view.findViewById(R.id.tv_feed_order_real_name);
        tv_feed_order_phone_number = view.findViewById(R.id.tv_feed_order_phone_number);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedReceiverAddressInfo) {

            tv_feed_order_real_name.setText(((FeedReceiverAddressInfo) info).getReceiverName());
            tv_feed_order_phone_number.setText(Util.formatNumber(((FeedReceiverAddressInfo) info).getReceiverPhone()));
            tv_feed_order_area.setText(((FeedReceiverAddressInfo) info).getArea());
            tv_feed_order_detail_address.setText(((FeedReceiverAddressInfo) info).getDetailAddress());


            view.setTag(info);
            view.setOnClickListener(listener);

            iv_feed_address_edit.setTag(info);
            iv_feed_address_edit.setOnClickListener(listener);
        }

    }


}
