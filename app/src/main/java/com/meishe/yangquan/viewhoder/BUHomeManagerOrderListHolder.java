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
import com.meishe.yangquan.bean.BUManagerOrderInfo;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.OrderContentsInfo;
import com.meishe.yangquan.bean.OrdersInfo;
import com.meishe.yangquan.bean.ReceiverInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 商版-主页-订单管理-订单列表
 *
 * @author 86188
 */
public class BUHomeManagerOrderListHolder extends BaseViewHolder {

    /*一下这些是从用户版复制过来的，名称暂时不修改*/

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
    private View rl_bu_order_wait_pay;
    /*待付-昵称*/
    private TextView tv_bu_buy_user_nickname;
    /*待付-地址*/
    private TextView tv_bu_buy_user_address;
    /*待付-电话*/
    private TextView tv_bu_buy_user_phone_number;
    /*待付-改价*/
    private Button btn_bu_order_change_price;




    private View rl_middle_container_one_goods;
    private View rl_middle_container_more_goods;

    private ImageView riv_goods_cover_1;
    private ImageView riv_goods_cover_2;
    private ImageView riv_goods_cover_3;
    private TextView tv_goods_price_more;
    private TextView tv_goods_amount_more;

    //    /*待发货*/
//    private View rl_bu_order_wait_send;
    /*底部功能区域 待发货*/
//    private View riv_bu_buy_user_photo;
//    private TextView tv_bu_wait_send_buy_user_nickname;
//    private TextView tv_bu_wait_send_buy_user_address;
//    private TextView btn_bu_order_send;

    /*顶部区域*/
    private TextView tv_bu_order_right_state;
    private TextView tv_bu_order_id;


    public BUHomeManagerOrderListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {

        //comment view
//        riv_bu_goods_cover = view.findViewById(R.id.riv_bu_goods_cover);
//        tv_bu_good_title = view.findViewById(R.id.tv_bu_good_title);
//        tv_bu_goods_amount = view.findViewById(R.id.tv_bu_goods_amount);

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


        //wait pay
        rl_bu_order_wait_pay = view.findViewById(R.id.rl_bu_order_wait_pay);
        tv_bu_buy_user_nickname = view.findViewById(R.id.tv_bu_buy_user_nickname);
        tv_bu_buy_user_address = view.findViewById(R.id.tv_bu_buy_user_address);
        tv_bu_buy_user_phone_number = view.findViewById(R.id.tv_bu_buy_user_phone_number);
        btn_bu_order_change_price = view.findViewById(R.id.btn_bu_order_change_price);

        //wait sned
//        rl_bu_order_wait_send = view.findViewById(R.id.rl_bu_order_wait_send);
//        riv_bu_buy_user_photo = view.findViewById(R.id.riv_bu_buy_user_photo);
//        tv_bu_wait_send_buy_user_nickname = view.findViewById(R.id.tv_bu_wait_send_buy_user_nickname);
//        tv_bu_wait_send_buy_user_address = view.findViewById(R.id.tv_bu_wait_send_buy_user_address);
//        btn_bu_order_send = view.findViewById(R.id.btn_bu_order_send);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUOrderInfo) {

            //通用数据赋值
            tv_bu_order_id.setText("订单编号: " + ((BUOrderInfo) info).getOrderId());
            ReceiverInfo receiverInfo = ((BUOrderInfo) info).getReceiverInfo();
            if (receiverInfo!=null){
                tv_bu_buy_user_nickname.setText("买家姓名: "+receiverInfo.getName());
                tv_bu_buy_user_address.setText("买家收货地址: "+receiverInfo.getArea()+" "+receiverInfo.getAddress());
                tv_bu_buy_user_phone_number.setText("买家联系方式: "+receiverInfo.getPhone());
            }


            int state = ((BUOrderInfo) info).getState();
            if (state == Constants.TYPE_COMMON_BU_ORDER_WAIT_PAY_TYPE) {
                //待付
                tv_bu_order_right_state.setText("待付");
                btn_bu_order_change_price.setText("改价");

            } else if (state == Constants.TYPE_COMMON_BU_ORDER_WAIT_SEND_TYPE) {
                //待发货
                tv_bu_order_right_state.setText("待发货");
                btn_bu_order_change_price.setText("发货");

            } else if (state == Constants.TYPE_COMMON_BU_ORDER_ALREADY_SEND_TYPE) {
                //已发货
                tv_bu_order_right_state.setText("已发货");
            } else if (state == Constants.TYPE_COMMON_BU_ORDER_FINISH_TYPE) {
                //完成
                tv_bu_order_right_state.setText("完成");
            }


            int goodsCounts = 0;
            //封面图
            List<String> coverList = new ArrayList<>();
            String goodsTitle = "";
            float price = ((BUOrderInfo) info).getPrice();
            List<OrdersInfo> orders = ((BUOrderInfo) info).getOrders();
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

        }

        btn_bu_order_change_price.setTag(info);
        btn_bu_order_change_price.setOnClickListener(listener);


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
