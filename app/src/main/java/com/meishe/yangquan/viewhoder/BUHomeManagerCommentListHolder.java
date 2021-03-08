package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUManagerCommentInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.RatingBar;

/**
 * 商版-主页-订单管理-订单列表
 *
 * @author 86188
 */
public class BUHomeManagerCommentListHolder extends BaseViewHolder {

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

    private RatingBar rb_bu_start;
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
        riv_bu_goods_cover = view.findViewById(R.id.riv_bu_goods_cover);
        tv_bu_good_title = view.findViewById(R.id.tv_bu_good_title);
        tv_bu_goods_amount = view.findViewById(R.id.tv_bu_goods_amount);
        rb_bu_start = view.findViewById(R.id.rb_bu_start);
        tv_bu_comment_star_desc = view.findViewById(R.id.tv_bu_comment_star_desc);



    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUManagerCommentInfo) {

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

            int state = ((BUManagerCommentInfo) info).getState();
            switch (state){
                case Constants.TYPE_COMMENT_GOOD_TYPE:
                    tv_bu_comment_star_desc.setText("好评");
                    rb_bu_start.setSelectedNumber(5);
                    break;
                case Constants.TYPE_COMMENT_MIDDLE_TYPE:
                    tv_bu_comment_star_desc.setText("中评");
                    rb_bu_start.setSelectedNumber(3);
                    break;
                case Constants.TYPE_COMMENT_BAD_TYPE:
                    tv_bu_comment_star_desc.setText("差评");
                    rb_bu_start.setSelectedNumber(1);
                    break;
            }
            rb_bu_start.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

//        btn_bu_edit_goods.setTag(info);
//        btn_bu_edit_goods.setOnClickListener(listener);


    }


}
