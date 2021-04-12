package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingCarInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.AddSubView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 我的-购物车列表
 *
 * @author 86188
 */
public class FeedShoppingCarListHolder extends BaseViewHolder {

    /*商品封面*/
    private ImageView riv_goods_cover;
    /*店铺名称*/
    private TextView tv_shopping_name;
    /*右边状态*/
    private TextView tv_bu_order_right_state;
    /*商品标题*/
    private TextView tv_bu_good_title;
    /*商品价格*/
    private TextView tv_goods_price;

    private ImageView iv_feed_shopping_car;

    private AddSubView tv_goods_amount;

    private View view;

    public FeedShoppingCarListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        view=itemView;
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_shopping_name = view.findViewById(R.id.tv_shopping_name);
        tv_bu_order_right_state = view.findViewById(R.id.tv_order_right_state);
        riv_goods_cover = view.findViewById(R.id.riv_goods_cover);
        tv_bu_good_title = view.findViewById(R.id.tv_good_title);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);
        iv_feed_shopping_car = view.findViewById(R.id.iv_feed_shopping_car);
        tv_goods_amount = view.findViewById(R.id.tv_goods_amount);


    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FeedShoppingCarGoodsInfo) {

            List<String> goodsImageUrls = ((FeedShoppingCarGoodsInfo) info).getGoodsImageUrls();
            if (!CommonUtils.isEmpty(goodsImageUrls)) {
                GlideUtil.getInstance().loadUrl(goodsImageUrls.get(0),riv_goods_cover);
            }

            tv_shopping_name.setText(((FeedShoppingCarGoodsInfo) info).getShopName());
            tv_bu_good_title.setText(((FeedShoppingCarGoodsInfo) info).getTitle());
            tv_goods_price.setText(((FeedShoppingCarGoodsInfo) info).getPrice()+"/桶");

            if (((FeedShoppingCarGoodsInfo) info).isNeedHideSelect()) {
                iv_feed_shopping_car.setVisibility(View.GONE);
            } else {
                iv_feed_shopping_car.setVisibility(View.VISIBLE);
                boolean select = ((FeedShoppingCarGoodsInfo) info).isSelect();
                if (select) {
                    iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
                } else {
                    iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                }
            }

            tv_goods_amount.setValue(((FeedShoppingCarGoodsInfo) info).getSelectAmount());
            tv_goods_amount.setMaxNumber(((FeedShoppingCarGoodsInfo) info).getStoreAmount());
            tv_goods_amount.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
                @Override
                public void OnNumberChanger(int value) {
                    ((FeedShoppingCarGoodsInfo) info).setSelectAmount(value);
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setBaseInfo(info);
                    messageEvent.setEventType(MessageEvent.MESSAGE_TYPE_FEED_GOODS_AMOUNT);
                    EventBus.getDefault().post(messageEvent);
                }
            });


            view.setTag(info);
            view.setOnClickListener(listener);
        }

    }


}
