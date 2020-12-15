package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.Comment;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * 市场
 * @author 86188
 */
public class MarketListHolder extends BaseViewHolder {


    private final RequestOptions options;
    /*圆头像*/
    private RoundAngleImageView civ_photo_circle;
    /*昵称*/
    private TextView tv_market_name;
    private TextView tv_market_address;
    private ImageView iv_market_cover;

    private TextView tv_market_type_name;
    private TextView tv_place_of_origin;
    private TextView tv_market_specification;
    private TextView tv_market_sheep_number;

    private TextView tv_market_price;

    private ImageView iv_market_1;
    private ImageView iv_market_2;
    private ImageView iv_market_3;
    private ImageView iv_market_4;
    private ImageView iv_market_5;
    private ImageView iv_market_6;

    private ImageView iv_market_phone;

    public MarketListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;

        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        civ_photo_circle=view.findViewById(R.id.civ_photo_circle);
        tv_market_name=view.findViewById(R.id.tv_market_name);

        tv_market_address=view.findViewById(R.id.tv_market_address);
        iv_market_cover=view.findViewById(R.id.iv_market_cover);

        tv_market_type_name=view.findViewById(R.id.tv_market_type_name);
        tv_place_of_origin=view.findViewById(R.id.tv_place_of_origin);
        tv_market_specification=view.findViewById(R.id.tv_market_specification);
        tv_market_sheep_number=view.findViewById(R.id.tv_market_sheep_number);

        tv_market_price=view.findViewById(R.id.tv_market_price);

        iv_market_1=view.findViewById(R.id.iv_market_1);
        iv_market_2=view.findViewById(R.id.iv_market_2);
        iv_market_3=view.findViewById(R.id.iv_market_3);
        iv_market_4=view.findViewById(R.id.iv_market_4);
        iv_market_5=view.findViewById(R.id.iv_market_5);
        iv_market_6=view.findViewById(R.id.iv_market_6);

        iv_market_phone=view.findViewById(R.id.iv_market_phone);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position,View.OnClickListener listener) {
        if (info instanceof MarketInfo){
            tv_market_name.setText(((MarketInfo) info).getNickname());
//            Comment comment= (Comment) info;
//            String photoUrl=comment.getPhotoUrl();
//            Glide.with(context)
//                    .asBitmap()
//                    .load(HttpUrl.URL_IMAGE+photoUrl)
//                    .apply(options)
//                    .into(iv_comment_photo);

//            tv_comment_nickname.setText(comment.getNickName());
//            byte[] bytes=Base64.decode(comment.getContent(), Base64.DEFAULT);
//            tv_comment_content.setText(new String(bytes));
//            tv_comment_time.setText(FormatCurrentData.getTimeRange(comment.getCreateTime()+""));

        }
    }


}