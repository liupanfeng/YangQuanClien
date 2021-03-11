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
import com.meishe.yangquan.bean.MineCollectionInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.utils.Constants;

/**
 * 我的-收藏列表
 *
 * @author 86188
 */
public class MineCollectionListHolder extends BaseViewHolder {

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
    private View rl_mine_collection_shopping;
    private View rl_mine_collect_goods;


    public MineCollectionListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        //comment view
        riv_bu_goods_cover = view.findViewById(R.id.riv_goods_cover);
        tv_bu_good_title = view.findViewById(R.id.tv_good_title);
        tv_bu_goods_amount = view.findViewById(R.id.tv_goods_amount);


        rl_mine_collect_goods = view.findViewById(R.id.rl_mine_collect_goods);
        rl_mine_collection_shopping = view.findViewById(R.id.rl_mine_collection_shopping);


    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MineCollectionInfo) {

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

            int state = ((MineCollectionInfo) info).getType();
            if (state== Constants.TYPE_MINE_COLLECT_SHOPPING){
                //店铺
               rl_mine_collection_shopping.setVisibility(View.VISIBLE);
               rl_mine_collect_goods.setVisibility(View.GONE);
            }else if (state== Constants.TYPE_MINE_COLLECT_GOODS){
                //商品
                rl_mine_collection_shopping.setVisibility(View.GONE);
                rl_mine_collect_goods.setVisibility(View.VISIBLE);
            }

        }

//        btn_bu_edit_goods.setTag(info);
//        btn_bu_edit_goods.setOnClickListener(listener);


    }


}
