package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.MineCollectionInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.view.RatingBar;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的-收藏列表
 *
 * @author 86188
 */
public class MineCollectionListHolder extends BaseViewHolder {

    /////////////////商品//////////////////////////////////
    /*商品封面*/
    private ImageView iv_feed_goods_cover;
    /*商品名称*/
    private TextView tv_feed_goods_title;
    /*商品价格*/
    private TextView tv_feed_goods_price;
    /*商品销量*/
    private TextView tv_feed_goods_sales;


    //////////////////////////店铺/////////////////////////////////

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
    /*收藏商品*/
    private View rl_mine_collect_goods;
    /*收藏店铺*/
    private View rl_mine_collection_shopping;


    public MineCollectionListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mItemView=itemView;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //商品
        rl_mine_collect_goods = view.findViewById(R.id.rl_mine_collect_goods);
        iv_feed_goods_cover = view.findViewById(R.id.iv_feed_goods_cover);
        tv_feed_goods_title = view.findViewById(R.id.tv_feed_goods_title);
        tv_feed_goods_price = view.findViewById(R.id.tv_feed_goods_price);
        tv_feed_goods_sales = view.findViewById(R.id.tv_feed_goods_sales);

        //////////////////////////店铺//////////////////////////
        rl_mine_collection_shopping = view.findViewById(R.id.rl_mine_collection_shopping);
        iv_feed_cover = view.findViewById(R.id.iv_feed_cover);
        tv_shopping_name = view.findViewById(R.id.tv_shopping_name);
        tv_shopping_score = view.findViewById(R.id.tv_shopping_score);
        tv_feed_selling = view.findViewById(R.id.tv_feed_selling);
        tv_shopping_address = view.findViewById(R.id.tv_shopping_address);
        tv_feed_distance = view.findViewById(R.id.tv_feed_distance);

        rb_feed_start = view.findViewById(R.id.rb_feed_start);



    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineCollectionInfo) {

            int state = ((MineCollectionInfo) info).getType();
            if (state== Constants.TYPE_COMMON_MINE_COLLECT_SHOPPING){
                //店铺
               rl_mine_collection_shopping.setVisibility(View.VISIBLE);
               rl_mine_collect_goods.setVisibility(View.GONE);

                tv_shopping_name.setText(((MineCollectionInfo) info).getName());
                tv_shopping_score.setText("评分："+((MineCollectionInfo) info).getScore());
                tv_shopping_address.setText(((MineCollectionInfo) info).getAddress());
                tv_feed_distance.setVisibility(View.GONE);
                tv_feed_selling.setText("月售："+((MineCollectionInfo) info).getSellAmount());
                rb_feed_start.setStartTotalNumber(5);

                float score = new BigDecimal(((MineCollectionInfo) info).getScore()).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
                rb_feed_start.setSelectedNumber((int) score);
                String coverUrl = ((MineCollectionInfo) info).getImageUrl();
                if (!TextUtils.isEmpty(coverUrl)) {
                    GlideUtil.getInstance().loadUrl(coverUrl,iv_feed_cover);
                } else {
                    GlideUtil.getInstance().loadUrl("",iv_feed_cover);
                }

//                mItemView.setTag(info);
//                mItemView.setOnClickListener(listener);

                rb_feed_start.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

            }else if (state== Constants.TYPE_COMMON_MINE_COLLECT_GOODS){
                //商品
                rl_mine_collection_shopping.setVisibility(View.GONE);
                rl_mine_collect_goods.setVisibility(View.VISIBLE);

                tv_feed_goods_title.setText(((MineCollectionInfo) info).getName());
                tv_feed_goods_price.setText("¥"+((MineCollectionInfo) info).getPrice()+"/斤");
                tv_feed_goods_sales.setText("销量"+((MineCollectionInfo) info).getSellAmount());
                String coverUrl = ((MineCollectionInfo) info).getImageUrl();
                if (!TextUtils.isEmpty(coverUrl)) {
                    GlideUtil.getInstance().loadUrl(coverUrl,iv_feed_cover);
                } else {
                    GlideUtil.getInstance().loadUrl("",iv_feed_cover);
                }

                mItemView.setTag(info);
                mItemView.setOnClickListener(listener);

            }

        }

//        btn_bu_edit_goods.setTag(info);
//        btn_bu_edit_goods.setOnClickListener(listener);


    }


}
