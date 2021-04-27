package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUManagerCommentChildInfo;
import com.meishe.yangquan.bean.BUManagerCommentInfo;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.OrderContentsInfo;
import com.meishe.yangquan.bean.OrdersInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.view.RatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 商版-主页-订单管理-订单列表
 *
 * @author 86188
 */
public class BUHomeManagerCommentListHolder extends BaseViewHolder {


    /*顶部区域*/
    private TextView tv_bu_order_right_state;
    private TextView tv_bu_order_id;


    /*商品封面*/
    private ImageView riv_goods_cover;
    /*店铺名称*/
    private TextView tv_shopping_name;
    /*右边状态*/
    private TextView tv_order_right_state;
    /*商品标题*/
    private TextView tv_good_title;
    /*商品价格*/
    private TextView tv_goods_price;
    /*商品数量*/
    private TextView tv_goods_amount;




    private View rl_middle_container_one_goods;
    private View rl_middle_container_more_goods;

    private ImageView riv_goods_cover_1;
    private ImageView riv_goods_cover_2;
    private ImageView riv_goods_cover_3;
    private TextView tv_goods_price_more;
    private TextView tv_goods_amount_more;

    /*底部区域*/
    private RatingBar rb_bu_start;
    private TextView tv_bu_comment_content;
    private TextView tv_bu_comment_star_desc;


    public BUHomeManagerCommentListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_bu_order_id = view.findViewById(R.id.tv_bu_order_id);
        tv_bu_order_right_state = view.findViewById(R.id.tv_bu_order_right_state);


        /*一个订单只包含一个商品的中间容器*/
        rl_middle_container_one_goods = view.findViewById(R.id.rl_middle_container_one_goods);
        tv_shopping_name = view.findViewById(R.id.tv_shopping_name);
        tv_order_right_state = view.findViewById(R.id.tv_order_right_state);
        riv_goods_cover = view.findViewById(R.id.riv_goods_cover);
        tv_good_title = view.findViewById(R.id.tv_good_title);
        tv_goods_amount = view.findViewById(R.id.tv_goods_amount);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);

        /*一个订单多件商品中间容器  多件的情况不显示标题*/
        rl_middle_container_more_goods = view.findViewById(R.id.rl_middle_container_more_goods);
        riv_goods_cover_1 = view.findViewById(R.id.riv_goods_cover_1);
        riv_goods_cover_2 = view.findViewById(R.id.riv_goods_cover_2);
        riv_goods_cover_3 = view.findViewById(R.id.riv_goods_cover_3);
        /*多件商品总价*/
        tv_goods_price_more = view.findViewById(R.id.tv_goods_price_more);
        /*共几件*/
        tv_goods_amount_more = view.findViewById(R.id.tv_goods_amount_more);







        rb_bu_start = view.findViewById(R.id.rb_bu_start);
        tv_bu_comment_star_desc = view.findViewById(R.id.tv_bu_comment_star_desc);
        tv_bu_comment_content = view.findViewById(R.id.tv_bu_comment_content);



    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUManagerCommentChildInfo) {

            int state = ((BUManagerCommentChildInfo) info).getType();
            switch (state){
                case Constants.TYPE_COMMENT_GOOD_TYPE:
                    tv_bu_comment_star_desc.setText("好评");
                    break;
                case Constants.TYPE_COMMENT_MIDDLE_TYPE:
                    tv_bu_comment_star_desc.setText("中评");
                    break;
                case Constants.TYPE_COMMENT_BAD_TYPE:
                    tv_bu_comment_star_desc.setText("差评");
                    break;
            }

            tv_bu_order_id.setText("订单编号："+((BUManagerCommentChildInfo) info).getOrderId());
            tv_bu_order_right_state.setText("已评价");


            int goodsCounts = 0;
            //封面图
            List<String> coverList = new ArrayList<>();
            String goodsTitle = "";
            String goodsDesc= "";
            float score=0;
            float price = ((BUManagerCommentChildInfo) info).getPrice();
            List<OrdersInfo> orders = ((BUManagerCommentChildInfo) info).getOrders();
            if (!CommonUtils.isEmpty(orders)) {
                for (int i = 0; i < orders.size(); i++) {
                    OrdersInfo ordersInfo = orders.get(i);
                    if (ordersInfo == null) {
                        continue;
                    }
                    List<OrderContentsInfo> orderContents = ordersInfo.getOrderContents();
                    if (!CommonUtils.isEmpty(orderContents)) {
                        for (int j = 0; j < orderContents.size(); j++) {
                            OrderContentsInfo orderContentsInfo = orderContents.get(j);
                            if (orderContentsInfo == null) {
                                continue;
                            }
                            goodsCounts+=orderContentsInfo.getGoodsAmount();
                            List<String> goodsImageUrls = orderContentsInfo.getGoodsImageUrls();
                            goodsTitle = orderContentsInfo.getGoodsTitle();
                            goodsDesc = orderContentsInfo.getDescription();
                            score = orderContentsInfo.getScore();
                            if (!CommonUtils.isEmpty(goodsImageUrls)) {
                                coverList.add(goodsImageUrls.get(0));
                            }
                        }
                    }
                }
            }


            if (goodsCounts > 0) {
                if (goodsCounts == 1) {
                    updateGoodsOneUI(coverList, goodsTitle, price);
                } else {
                    updateMoreGoodsUI(coverList, goodsCounts, price);
                }
            }

            rb_bu_start.setSelectedNumber((int) score);
            tv_bu_comment_content.setText(goodsDesc);



        rb_bu_start.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }


    }


    /**
     * 只有单个商品
     */
    private void updateGoodsOneUI(List<String> coverList, String title, float price) {
        rl_middle_container_one_goods.setVisibility(View.VISIBLE);
        rl_middle_container_more_goods.setVisibility(View.GONE);

        tv_good_title.setText(title);
        tv_goods_price.setText("" + price);
        if (!CommonUtils.isEmpty(coverList)) {
            GlideUtil.getInstance().loadUrl(coverList.get(0), riv_goods_cover);
        }


    }


    /**
     * 有多个商品
     */
    private void updateMoreGoodsUI(List<String> coverList, int goodsCount, float price) {
        rl_middle_container_one_goods.setVisibility(View.GONE);
        rl_middle_container_more_goods.setVisibility(View.VISIBLE);

        tv_goods_price_more.setText("" + price);
        tv_goods_amount_more.setText("共" + goodsCount + "件");

        riv_goods_cover_1.setVisibility(View.GONE);
        riv_goods_cover_2.setVisibility(View.GONE);
        riv_goods_cover_3.setVisibility(View.GONE);


        if (!CommonUtils.isEmpty(coverList)) {
            for (int i = 0; i < coverList.size(); i++) {
                String coverUrl = coverList.get(i);
                if (!TextUtils.isEmpty(coverUrl)) {
                    if (i == 0) {
                        riv_goods_cover_1.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl, riv_goods_cover_1);
                    } else if (i == 1) {
                        riv_goods_cover_2.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl, riv_goods_cover_2);
                    } else if (i == 2) {
                        riv_goods_cover_3.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl, riv_goods_cover_3);
                    }
                }

            }
        }
    }



}
