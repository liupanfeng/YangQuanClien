package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.OrderContentsInfo;
import com.meishe.yangquan.bean.OrdersInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户版-我的-订单列表
 *
 * @author 86188
 */
public class MineOrderListHolder extends BaseViewHolder {

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

    /*待付*/
    private View rl_bottom_function_view;
    /*待付-昵称*/
    private TextView tv_buy_user_nickname;
    /*倒计时取消订单*/
//    private TextView tv_time_cancel_order;
    private Button btn_left_function;
    private Button btn_right_function;

    private View rl_middle_container_one_goods;
    private View rl_middle_container_more_goods;

    private ImageView riv_goods_cover_1;
    private ImageView riv_goods_cover_2;
    private ImageView riv_goods_cover_3;
    private TextView tv_goods_price_more;
    private TextView tv_goods_amount_more;


    public MineOrderListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
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


        //bottom function view
        rl_bottom_function_view = view.findViewById(R.id.rl_bottom_function_view);
//        tv_time_cancel_order = view.findViewById(R.id.tv_time_cancel_order);
        btn_left_function = view.findViewById(R.id.btn_left_function);
        btn_right_function = view.findViewById(R.id.btn_right_function);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineOrderInfo) {
            int state = ((MineOrderInfo) info).getType();
            if (state == Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE) {
                //待付
                tv_order_right_state.setText("待付");
//                tv_time_cancel_order.setVisibility(View.VISIBLE);
                btn_left_function.setText("取消订单");
                btn_left_function.setVisibility(View.VISIBLE);
                btn_right_function.setText("去支付");
            } else if (state == Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE) {
                //待收货
                tv_order_right_state.setText("待收货");
//                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("申请退款");
                btn_left_function.setVisibility(View.VISIBLE);
                btn_right_function.setText("确认收货");
            } else if (state == Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE) {
                //评价
                tv_order_right_state.setText("已完成");
//                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("再次购买");
                btn_left_function.setVisibility(View.GONE);
                btn_right_function.setText("评价");
            } else if (state == Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE) {
                //退款
                tv_order_right_state.setText("退款");
//                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("再次购买");
                btn_left_function.setVisibility(View.GONE);
                btn_right_function.setText("退货进度");
            }


            //中间内容区域
//            tv_shopping_name.setText(((MineOrderInfo) info).getOrders().get(0).getShopName());
//            GlideUtil.getInstance().loadUrl(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsImageUrls().get(0),
//                    riv_bu_goods_cover);
//            tv_bu_good_title.setText(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsTitle());
//            tv_bu_goods_amount.setText(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsAmount()+"");

            //先统计有这个订单有几件商品
            int goodsCounts = 0;
            //封面图
            List<String> coverList = new ArrayList<>();
            String goodsTitle = "";
            float price = ((MineOrderInfo) info).getPrice();
            List<OrdersInfo> orders = ((MineOrderInfo) info).getOrders();
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


            //底部按钮点击事件
            btn_left_function.setTag(info);
            btn_left_function.setOnClickListener(listener);

            btn_right_function.setTag(info);
            btn_right_function.setOnClickListener(listener);

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
        if (!CommonUtils.isEmpty(coverList)){
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


        if (!CommonUtils.isEmpty(coverList)){
            for (int i = 0; i < coverList.size() ; i++) {
                String coverUrl = coverList.get(i);
                if (!TextUtils.isEmpty(coverUrl)){
                    if (i==0){
                        riv_goods_cover_1.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl,riv_goods_cover_1);
                    }else if (i==1){
                        riv_goods_cover_2.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl,riv_goods_cover_2);
                    }else if (i==2){
                        riv_goods_cover_3.setVisibility(View.VISIBLE);
                        GlideUtil.getInstance().loadUrl(coverUrl,riv_goods_cover_3);
                    }
                }

            }
        }
    }

}
