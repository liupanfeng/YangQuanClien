package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.HomeMarketPictureInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.bean.SheepBarPictureInfo;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-市场
 *
 * @author 86188
 */
public class MarketListHolder extends BaseViewHolder {


    private final RequestOptions options;
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


    public MarketListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        civ_photo_circle = view.findViewById(R.id.civ_photo_circle);
        tv_market_name = view.findViewById(R.id.tv_market_name);

        tv_market_address = view.findViewById(R.id.tv_market_address);
        iv_market_cover = view.findViewById(R.id.iv_market_cover);

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
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(5);
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
            tv_market_sheep_number.setText(((MarketInfo) info).getAmount() + "");
            tv_market_price.setText(((MarketInfo) info).getPrice() + "元/只");
            tv_market_specification.setText(((MarketInfo) info).getWeight()+"斤");
            tv_place_of_origin.setText(((MarketInfo) info).getAddress());

            List<String> images = ((MarketInfo) info).getImages();
            if (images != null && images.size() > 0) {
                String coverUrl = images.get(0);
                Glide.with(context)
                        .asBitmap()
                        .load(coverUrl)
                        .apply(options)
                        .into(iv_market_cover);
            }


            List<HomeMarketPictureInfo> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(images)&&images.size()>1) {
                for (int i = 1; i < images.size(); i++) {
                    HomeMarketPictureInfo homeMarketPictureInfo = new HomeMarketPictureInfo();
                    homeMarketPictureInfo.setFilePath(images.get(i));
                    homeMarketPictureInfo.setType(SheepBarPictureInfo.TYPE_URL_PIC);
                    list.add(homeMarketPictureInfo);
                }
                mGrideAdapter.addAll(list);
            }else{
                mGrideAdapter.addAll(null);
            }

            iv_market_phone.setTag(info);
            iv_market_phone.setOnClickListener(listener);

        }
    }


}
