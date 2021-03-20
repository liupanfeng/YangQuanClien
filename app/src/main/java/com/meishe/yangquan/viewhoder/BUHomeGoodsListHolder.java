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
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUShoppingUserInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.List;

/**
 * 商版-主页-商品列表
 *
 * @author 86188
 */
public class BUHomeGoodsListHolder extends BaseViewHolder {

    /*商品封面*/
    private ImageView riv_bu_goods_cover;
    /*商品标题*/
    private TextView tv_bu_goods_title;
    /*价格*/
    private TextView tv_bu_goods_price;
    /*已经售卖*/
    private TextView tv_bu_goods_sell_amount;
    /*库存*/
    private TextView tv_bu_goods_store_amount;
    /*编辑商品*/
    private View btn_bu_edit_goods;
    /*上架商品*/
    private Button btn_bu_shelves_goods;
    /*删除商品*/
    private Button btn_bu_delete_goods;


    public BUHomeGoodsListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        riv_bu_goods_cover = view.findViewById(R.id.riv_bu_goods_cover);
        tv_bu_goods_title = view.findViewById(R.id.tv_bu_goods_title);
        tv_bu_goods_price = view.findViewById(R.id.tv_bu_goods_price);
        tv_bu_goods_sell_amount = view.findViewById(R.id.tv_bu_goods_sell_amount);
        tv_bu_goods_store_amount = view.findViewById(R.id.tv_bu_goods_store_amount);
        btn_bu_edit_goods = view.findViewById(R.id.btn_bu_edit_goods);
        btn_bu_shelves_goods = view.findViewById(R.id.btn_bu_shelves_goods);
        btn_bu_delete_goods = view.findViewById(R.id.btn_bu_delete_goods);
    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof BUGoodsInfo) {
            List<String> goodsImageUrls = ((BUGoodsInfo) info).getGoodsImageUrls();
            if (!CommonUtils.isEmpty(goodsImageUrls)) {
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.mipmap.ic_message_list_photo_default);
                Glide.with(context)
                        .asBitmap()
                        .load(goodsImageUrls.get(0))
                        .apply(options)
                        .into(riv_bu_goods_cover);
            }

            tv_bu_goods_title.setText(((BUGoodsInfo) info).getTitle());
            tv_bu_goods_price.setText(((BUGoodsInfo) info).getPrice()+"/"+((BUGoodsInfo) info).getSpecification());
            tv_bu_goods_sell_amount.setText("已售："+((BUGoodsInfo) info).getSellAmount());
            tv_bu_goods_store_amount.setText("库存："+((BUGoodsInfo) info).getStoreAmount());

            int isPublic = ((BUGoodsInfo) info).getIsPublic();
            if (isPublic==0){
                btn_bu_shelves_goods.setText("上架");
                btn_bu_delete_goods.setText("删除");
                btn_bu_delete_goods.setVisibility(View.VISIBLE);
            }else if ((isPublic==1)){
                btn_bu_shelves_goods.setText("下架");
//                btn_bu_delete_goods.setText("分享");
                btn_bu_delete_goods.setVisibility(View.INVISIBLE);
            }
        }

        btn_bu_edit_goods.setTag(info);
        btn_bu_edit_goods.setOnClickListener(listener);

        btn_bu_shelves_goods.setTag(info);
        btn_bu_shelves_goods.setOnClickListener(listener);

        btn_bu_delete_goods.setTag(info);
        btn_bu_delete_goods.setOnClickListener(listener);

        riv_bu_goods_cover.setTag(info);
        riv_bu_goods_cover.setOnClickListener(listener);

    }


}
