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
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.GlideUtil;

import java.util.List;

/**
 * 饲料商品列表
 *
 * @author 86188
 */
public class FeedFoodsHolder extends BaseViewHolder {

    private View mItemView;

    /*商品封面*/
    private ImageView iv_feed_goods_cover;
    /*商品名称*/
    private TextView tv_feed_goods_title;
    /*商品价格*/
    private TextView tv_feed_goods_price;
    /*商品销量*/
    private TextView tv_feed_goods_sales;


    public FeedFoodsHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mItemView = itemView;
        mAdapter = adapter;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        iv_feed_goods_cover = view.findViewById(R.id.iv_feed_goods_cover);
        tv_feed_goods_title = view.findViewById(R.id.tv_feed_goods_title);
        tv_feed_goods_price = view.findViewById(R.id.tv_feed_goods_price);
        tv_feed_goods_sales = view.findViewById(R.id.tv_feed_goods_sales);
//        tv_shopping_address = view.findViewById(R.id.tv_shopping_address);
//        tv_feed_distance = view.findViewById(R.id.tv_feed_distance);
    }

    @Override
    public void bindViewHolder(Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedGoodsInfo) {
            tv_feed_goods_title.setText(((FeedGoodsInfo) info).getTitle());
            tv_feed_goods_price.setText("¥"+((FeedGoodsInfo) info).getPrice()+"/斤");
            tv_feed_goods_sales.setText("销量"+((FeedGoodsInfo) info).getSellAmount());
            List<String> goodsImageUrls = ((FeedGoodsInfo) info).getGoodsImageUrls();
            if (!CommonUtils.isEmpty(goodsImageUrls)) {
                String coverUrl=goodsImageUrls.get(0);
                GlideUtil.getInstance().loadUrl(coverUrl,iv_feed_goods_cover);
            } else {
                GlideUtil.getInstance().loadUrl("",iv_feed_goods_cover);
            }

            mItemView.setTag(info);
            mItemView.setOnClickListener(listener);
        }

    }


}
