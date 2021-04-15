package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUManagerOrderInfo;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.Constants;

/**
 * 商版-主页-订单管理-订单列表
 *
 * @author 86188
 */
public class BUHomeManagerOrderListHolder extends BaseViewHolder {

    /*商品封面*/
    private ImageView riv_bu_goods_cover;
    /*订单编号*/
    private TextView tv_bu_order_id;
    /*右边状态*/
    private TextView tv_bu_order_right_state;
    /*商品标题*/
    private TextView tv_bu_good_title;
    /*商品价格*/
    private TextView tv_bu_goods_price;
    /*商品数量*/
    private TextView tv_bu_goods_amount;

    /*待付*/
    private View rl_bu_order_wait_pay;
    /*待付-昵称*/
    private TextView tv_bu_buy_user_nickname;
    /*待付-地址*/
    private TextView tv_bu_buy_user_address;
    /*待付-电话*/
    private TextView tv_bu_buy_user_phone_number;
    /*待付-改价*/
    private View btn_bu_order_change_price;

    /*待发货*/
    private View rl_bu_order_wait_send;
    /*头像*/
    private ImageView riv_bu_buy_user_photo;
    /*昵称*/
    private TextView tv_bu_wait_send_buy_user_nickname;
    /*地址*/
    private TextView tv_bu_wait_send_buy_user_address;
    /*发货*/
    private View btn_bu_order_send;


    public BUHomeManagerOrderListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        tv_bu_order_id = view.findViewById(R.id.tv_bu_order_id);
        tv_bu_order_right_state = view.findViewById(R.id.tv_bu_order_right_state);
        riv_bu_goods_cover = view.findViewById(R.id.riv_bu_goods_cover);
        tv_bu_good_title = view.findViewById(R.id.tv_bu_good_title);
        tv_bu_goods_amount = view.findViewById(R.id.tv_bu_goods_amount);

        //wait pay
        rl_bu_order_wait_pay = view.findViewById(R.id.rl_bu_order_wait_pay);
        tv_bu_buy_user_nickname = view.findViewById(R.id.tv_bu_buy_user_nickname);
        tv_bu_buy_user_address = view.findViewById(R.id.tv_bu_buy_user_address);
        tv_bu_buy_user_phone_number = view.findViewById(R.id.tv_bu_buy_user_phone_number);
        btn_bu_order_change_price = view.findViewById(R.id.btn_bu_order_change_price);

        //wait sned
        rl_bu_order_wait_send = view.findViewById(R.id.rl_bu_order_wait_send);
        riv_bu_buy_user_photo = view.findViewById(R.id.riv_bu_buy_user_photo);
        tv_bu_wait_send_buy_user_nickname = view.findViewById(R.id.tv_bu_wait_send_buy_user_nickname);
        tv_bu_wait_send_buy_user_address = view.findViewById(R.id.tv_bu_wait_send_buy_user_address);
        btn_bu_order_send = view.findViewById(R.id.btn_bu_order_send);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUOrderInfo) {

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

            int state = ((BUOrderInfo) info).getState();
            if (state== Constants.TYPE_COMMON_BU_ORDER_WAIT_PAY_TYPE){
                //待付
                rl_bu_order_wait_pay.setVisibility(View.VISIBLE);
                rl_bu_order_wait_send.setVisibility(View.GONE);
                tv_bu_order_right_state.setText("待付");

            }else if (state== Constants.TYPE_COMMON_BU_ORDER_WAIT_SEND_TYPE){
                //待发货
                rl_bu_order_wait_pay.setVisibility(View.GONE);
                rl_bu_order_wait_send.setVisibility(View.VISIBLE);
                tv_bu_order_right_state.setText("待发货");

            }else if (state== Constants.TYPE_COMMON_BU_ORDER_ALREADY_SEND_TYPE){
                //已发货
                tv_bu_order_right_state.setText("已发货");
                rl_bu_order_wait_pay.setVisibility(View.GONE);
                rl_bu_order_wait_send.setVisibility(View.GONE);
            }else if (state== Constants.TYPE_COMMON_BU_ORDER_FINISH_TYPE){
                //完成
                tv_bu_order_right_state.setText("完成");
                rl_bu_order_wait_pay.setVisibility(View.GONE);
                rl_bu_order_wait_send.setVisibility(View.GONE);
            }


        }

//        btn_bu_edit_goods.setTag(info);
//        btn_bu_edit_goods.setOnClickListener(listener);


    }


}
