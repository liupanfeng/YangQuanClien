package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingCarInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.utils.Constants;

/**
 * 我的-购物车列表
 *
 * @author 86188
 */
public class FeedShoppingCarListHolder extends BaseViewHolder {

    /*商品封面*/
    private ImageView riv_bu_goods_cover;
    /*店铺名称*/
    private TextView tv_shopping_name;
    /*右边状态*/
    private TextView tv_bu_order_right_state;
    /*商品标题*/
    private TextView tv_bu_good_title;
    /*商品价格*/
    private TextView tv_bu_goods_price;
    /*商品数量*/
    private TextView tv_bu_goods_amount;

    private ImageView iv_feed_shopping_car;


    public FeedShoppingCarListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_shopping_name = view.findViewById(R.id.tv_shopping_name);
        tv_bu_order_right_state = view.findViewById(R.id.tv_order_right_state);
        riv_bu_goods_cover = view.findViewById(R.id.riv_goods_cover);
        tv_bu_good_title = view.findViewById(R.id.tv_good_title);
        iv_feed_shopping_car = view.findViewById(R.id.iv_feed_shopping_car);
//        tv_bu_goods_amount = view.findViewById(R.id.tv_goods_amount);


    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedShoppingCarGoodsInfo) {

//            List<String> goodsImageUrls = ((BUGoodsInfo) info).getGoodsImageUrls();
//            if (!CommonUtils.isEmpty(goodsImageUrls)) {
//                RequestOptions options = new RequestOptions();
//                options.centerCrop();
//                options.placeholder(R.mipmap.ic_message_list_photo_default);
//                Glide.with(context)
//                        .asBitmap()
//                        .load(goodsImageUrls.get(0))
//                        .apply(options)
//                        .into(riv_bu_goods_cover);
//            }

            boolean select = ((FeedShoppingCarGoodsInfo) info).isSelect();
            if (select){
                iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
            }else{
                iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
            }

        }

        iv_feed_shopping_car.setTag(info);
        iv_feed_shopping_car.setOnClickListener(listener);


    }


}
