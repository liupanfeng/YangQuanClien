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
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;

/**
 * 我的-订单列表
 *
 * @author 86188
 */
public class MineOrderListHolder extends BaseViewHolder {

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

    /*待付*/
    private View rl_bottom_function_view;
    /*待付-昵称*/
    private TextView tv_bu_buy_user_nickname;
    /*倒计时取消订单*/
    private TextView tv_time_cancel_order;
    private Button btn_left_function;
    private Button btn_right_function;


    public MineOrderListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
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
        tv_bu_goods_amount = view.findViewById(R.id.tv_goods_amount);

        //bottom function view
        rl_bottom_function_view = view.findViewById(R.id.rl_bottom_function_view);
        tv_time_cancel_order = view.findViewById(R.id.tv_time_cancel_order);
        btn_left_function = view.findViewById(R.id.btn_left_function);
        btn_right_function = view.findViewById(R.id.btn_right_function);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineOrderInfo) {
            int state = ((MineOrderInfo) info).getOrderState();
            if (state== Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE){
                //待付
                tv_bu_order_right_state.setText("待付");
                tv_time_cancel_order.setVisibility(View.VISIBLE);
                btn_left_function.setText("取消订单");
                btn_right_function.setText("去支付");
            }else if (state== Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE){
                //待收货
                tv_bu_order_right_state.setText("待收货");
                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("申请退款");
                btn_right_function.setText("确认收货");
            }else if (state== Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE){
                //评价
                tv_bu_order_right_state.setText("已完成");
                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("再次购买");
                btn_right_function.setText("评价");
            }else if (state== Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE){
                //退款
                tv_bu_order_right_state.setText("退款");
                tv_time_cancel_order.setVisibility(View.GONE);
                btn_left_function.setText("再次购买");
                btn_right_function.setText("退货进度");
            }

            tv_shopping_name.setText(((MineOrderInfo) info).getOrders().get(0).getShopName());
            GlideUtil.getInstance().loadUrl(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsImageUrls().get(0),
                    riv_bu_goods_cover);
            tv_bu_good_title.setText(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsTitle());
            tv_bu_goods_amount.setText(((MineOrderInfo) info).getOrders().get(0).getOrderContents().get(0).getGoodsAmount()+"");

            btn_left_function.setTag(info);
            btn_left_function.setOnClickListener(listener);

            btn_right_function.setTag(info);
            btn_right_function.setOnClickListener(listener);

        }




    }


}
