package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.fragment.HomeServiceFragment;
import com.meishe.yangquan.view.CircleImageView;
import com.meishe.yangquan.view.RoundAngleImageView;

import java.util.List;

/**
 * home - 服务
 * @author 86188
 */
public class ServiceListHolder extends BaseViewHolder {


    private final RequestOptions options;
    /*圆头像*/
    private CircleImageView civ_photo_circle;
    /*昵称*/
    private TextView tv_market_name;
    private TextView tv_market_address;
    private RoundAngleImageView iv_market_cover;
     /*团队名称*/
    private TextView tv_service_name;
    private TextView tv_place_of_origin;
    /*团队人数*/
    private TextView tv_person_number;
    /*服务价格*/
    private TextView tv_sheep_price;

    private TextView tv_market_price;

    private ImageView iv_market_1;
    private ImageView iv_market_2;
    private ImageView iv_market_3;
    private ImageView iv_market_4;
    private ImageView iv_market_5;
    private ImageView iv_market_6;

    private ImageView iv_market_phone;
    private View rl_cut_sheep_hair;
    private View rl_find_car;
    private View rl_cut_sheep_dung;

    public ServiceListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter=adapter;

        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object ...obj) {
        rl_cut_sheep_hair=view.findViewById(R.id.rl_cut_sheep_hair);
        rl_cut_sheep_dung=view.findViewById(R.id.rl_cut_sheep_dung);
        rl_find_car=view.findViewById(R.id.rl_find_car);

        civ_photo_circle=view.findViewById(R.id.civ_photo_circle);
        tv_market_name=view.findViewById(R.id.tv_market_name);

        tv_market_address=view.findViewById(R.id.tv_market_address);
        iv_market_cover=view.findViewById(R.id.iv_service_cover);

        tv_service_name =view.findViewById(R.id.tv_service_name);
        tv_person_number =view.findViewById(R.id.tv_person_number);
        tv_sheep_price =view.findViewById(R.id.tv_sheep_price);

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
        if (info instanceof ServiceInfo){
            int serverType = ((ServiceInfo) info).getServerType();
            switch (serverType){
                case HomeServiceFragment.TYPE_SERVICE_CUT_WOOL:
                case HomeServiceFragment.TYPE_SERVICE_VACCINE:
                    rl_cut_sheep_hair.setVisibility(View.VISIBLE);
                    rl_cut_sheep_dung.setVisibility(View.GONE);
                    rl_find_car.setVisibility(View.GONE);
                    break;
                case HomeServiceFragment.TYPE_SERVICE_SHEEP_DUNG:
                    rl_cut_sheep_hair.setVisibility(View.GONE);
                    rl_cut_sheep_dung.setVisibility(View.VISIBLE);
                    rl_find_car.setVisibility(View.GONE);
                    break;
                case HomeServiceFragment.TYPE_SERVICE_LOOK_CAR:
                    rl_cut_sheep_hair.setVisibility(View.GONE);
                    rl_cut_sheep_dung.setVisibility(View.GONE);
                    rl_find_car.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
//            tv_market_name.setText(((ServiceInfo) info).getNickname());

//            tv_market_address.setText(((ServiceInfo) info).getAddress());
//            tv_market_type_name.setText(((ServiceInfo) info).getVariety());
//            tv_market_sheep_number.setText(((ServiceInfo) info).getAmount()+"");
//            tv_market_price.setText(((ServiceInfo) info).getPrice()+"");

            List<String> images = ((ServiceInfo) info).getImages();
            if (images!=null&&images.size()>0){


                String coverUrl = images.get(0);
                Glide.with(context)
                        .asBitmap()
                        .load(coverUrl)
                        .apply(options)
                        .into(iv_market_cover);


            }
        }
    }


}
