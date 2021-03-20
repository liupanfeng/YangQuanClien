package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.view.RatingBar;

import java.math.BigDecimal;
import java.util.List;

/**
 * 饲料商店列表
 * @author 86188
 */
public class FeedShoppingHolder extends BaseViewHolder {

    private final RequestOptions options;
    private View mItemView;

    /*店铺封面图*/
    private ImageView iv_feed_cover;
    /*店铺名称*/
    private TextView tv_shopping_name;
    /*店铺评分*/
    private TextView tv_shopping_score;
    /*月销售量*/
    private TextView tv_feed_selling;
    /*店铺地址*/
    private TextView tv_shopping_address;
    /*距离*/
    private TextView tv_feed_distance;
    private RatingBar rb_feed_start;


    public FeedShoppingHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mItemView = itemView;
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        iv_feed_cover = view.findViewById(R.id.iv_feed_cover);
        tv_shopping_name = view.findViewById(R.id.tv_shopping_name);
        tv_shopping_score = view.findViewById(R.id.tv_shopping_score);
        tv_feed_selling = view.findViewById(R.id.tv_feed_selling);
        tv_shopping_address = view.findViewById(R.id.tv_shopping_address);
        tv_feed_distance = view.findViewById(R.id.tv_feed_distance);

        rb_feed_start = view.findViewById(R.id.rb_feed_start);
    }

    @Override
    public void bindViewHolder(Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedShoppingInfo) {
            tv_shopping_name.setText(((FeedShoppingInfo) info).getName());
            tv_shopping_score.setText("评分："+((FeedShoppingInfo) info).getShopScore());
            tv_shopping_address.setText(((FeedShoppingInfo) info).getAddress());
            tv_feed_distance.setVisibility(View.GONE);
            tv_feed_selling.setText("月售："+((FeedShoppingInfo) info).getSellAmount());
            rb_feed_start.setStartTotalNumber(5);

            float score = new BigDecimal(((FeedShoppingInfo) info).getShopScore()).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
            rb_feed_start.setSelectedNumber((int) score);
            String coverUrl = ((FeedShoppingInfo) info).getShopOutSideImageUrl();
            if (coverUrl != null ) {
                Glide.with(context)
                        .asBitmap()
                        .load(coverUrl)
                        .apply(options)
                        .into(iv_feed_cover);
            } else {
                Glide.with(context)
                        .asBitmap()
                        .load("")
                        .apply(options)
                        .into(iv_feed_cover);
            }

            mItemView.setTag(info);
            mItemView.setOnClickListener(listener);

            rb_feed_start.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }

    }


}
