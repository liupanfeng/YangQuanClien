package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.CommonPictureInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-市场
 *
 * @author 86188
 */
public class HomeMarketBuyLittleSheepListHolder extends BaseViewHolder {


    /*圆头像*/
    private CircleImageView civ_photo_circle;
    /*昵称*/
    private TextView tv_market_name;
    private TextView tv_market_address;
    private ImageView iv_market_cover;

    private TextView tv_market_type_name;
    private TextView tv_place_of_origin;
    private TextView tv_market_specification;
    private TextView tv_market_sheep_number;

    private TextView tv_market_price;


    private ImageView iv_market_phone;

    private RecyclerView grid_recycler;

    private MultiFunctionAdapter mGrideAdapter;
    private View ll_sell_big;
    private View ll_sell_little;
    private View ll_buy_little;
    private View ll_buy_big;
    private TextView tv_market_desc;


    public HomeMarketBuyLittleSheepListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

    }

    @Override
    protected void initViewHolder(View view, Object... obj) {

        ll_sell_big = view.findViewById(R.id.ll_sell_big);
        ll_sell_little = view.findViewById(R.id.ll_sell_little);
        ll_buy_little = view.findViewById(R.id.ll_buy_little);
        ll_buy_big = view.findViewById(R.id.ll_buy_big);

        tv_market_desc = view.findViewById(R.id.tv_market_desc);


        civ_photo_circle = view.findViewById(R.id.civ_photo_circle);
        tv_market_name = view.findViewById(R.id.tv_market_nickname);

        tv_market_address = view.findViewById(R.id.tv_market_address);
        iv_market_cover = view.findViewById(R.id.iv_cover);

        tv_market_type_name = view.findViewById(R.id.tv_market_type_name);
        tv_place_of_origin = view.findViewById(R.id.tv_place_of_origin);
        tv_market_specification = view.findViewById(R.id.tv_market_specification);
        tv_market_sheep_number = view.findViewById(R.id.tv_market_sheep_number);

        tv_market_price = view.findViewById(R.id.tv_market_price);

        grid_recycler = view.findViewById(R.id.grid_recycler);
        iv_market_phone = view.findViewById(R.id.iv_market_phone);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(grid_recycler.getContext(), 3);
        mGrideAdapter = new MultiFunctionAdapter(grid_recycler.getContext(), grid_recycler);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(ScreenUtils.dip2px(grid_recycler.getContext(), 3));
        grid_recycler.addItemDecoration(customGridItemDecoration);
        grid_recycler.setLayoutManager(gridLayoutManager);
        grid_recycler.setAdapter(mGrideAdapter);

    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof MarketInfo) {
            tv_market_name.setText(((MarketInfo) info).getNickname());
            tv_market_address.setText(((MarketInfo) info).getAddress());
            tv_market_type_name.setText(((MarketInfo) info).getTitle());
            tv_market_sheep_number.setText(((MarketInfo) info).getAmount() + "只");
//            tv_market_price.setText(((MarketInfo) info).getPrice() + "元/只");
            tv_market_specification.setText(((MarketInfo) info).getWeight() + "斤");
            tv_place_of_origin.setText(((MarketInfo) info).getVariety());
            if (tv_market_desc!=null){
                tv_market_desc.setText(((MarketInfo) info).getDescription());
            }
            List<String> images = ((MarketInfo) info).getImages();
            String coverUrl="";
            if (images != null && images.size() > 0) {
                coverUrl = images.get(0);

            } else {
                coverUrl="";
            }

            GlideUtil.getInstance().loadUrl(coverUrl,iv_market_cover);
            GlideUtil.getInstance().loadPhotoUrl(((MarketInfo) info).getIconUrl(),civ_photo_circle);

            List<HomeMarketPictureInfo> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(images) && images.size() > 1) {
                for (int i = 1; i < images.size(); i++) {
                    HomeMarketPictureInfo homeMarketPictureInfo = new HomeMarketPictureInfo();
                    homeMarketPictureInfo.setFilePath(images.get(i));
                    homeMarketPictureInfo.setType(CommonPictureInfo.TYPE_URL_PIC);
                    list.add(homeMarketPictureInfo);
                }
                mGrideAdapter.addAll(list);
            } else {
                mGrideAdapter.addAll(null);
            }

            iv_market_phone.setTag(info);
            iv_market_phone.setOnClickListener(listener);


            iv_market_cover.setOnClickListener(listener);
            iv_market_cover.setTag(info);


            tv_market_address.setOnClickListener(listener);
            tv_market_address.setTag(info);
        }
    }


}
