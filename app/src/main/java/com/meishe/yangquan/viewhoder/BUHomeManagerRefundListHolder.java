package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUGoodsRefundListInfo;
import com.meishe.yangquan.bean.BUManagerCommentInfo;
import com.meishe.yangquan.bean.BUManagerRefundInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.RatingBar;

/**
 * 商版-主页-订单管理-订单列表
 *
 * @author 86188
 */
public class BUHomeManagerRefundListHolder extends BaseViewHolder {

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

    private View btn_bu_refund;
    private View rl_bu_refund;


    public BUHomeManagerRefundListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
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

        btn_bu_refund = view.findViewById(R.id.btn_bu_refund);
        rl_bu_refund = view.findViewById(R.id.rl_bu_refund);



    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUGoodsRefundListInfo) {

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

            int state = ((BUManagerRefundInfo) info).getState();
            switch (state){
                case Constants.TYPE_REFUND_ING_TYPE:
                    tv_bu_order_right_state.setText("退货中");
                    rl_bu_refund.setVisibility(View.VISIBLE);
                    break;
                case Constants.TYPE_REFUND_FINISH_TYPE:
                    tv_bu_order_right_state.setText("已完成");
                    rl_bu_refund.setVisibility(View.GONE);
                    break;
            }
        }

        btn_bu_refund.setTag(info);
        btn_bu_refund.setOnClickListener(listener);


    }


}
