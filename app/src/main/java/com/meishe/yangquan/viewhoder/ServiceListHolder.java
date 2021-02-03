package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
 *
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
    /*团队描述*/
    private TextView tv_team_desc;
    /*车对名称*/
    private TextView tv_sheep_dung_name;
    /*车对人数*/
    private TextView tv_sheep_dung_person_number;
    /*车数量*/
    private TextView tv_sheep_dung_cat_number;
    /*车数量*/
    private TextView tv_sheep_dung_team_price;


    /*车类型*/
    private TextView tv_find_car_type;
    /*车服务类型  */
    private TextView tv_find_car_service_type;
    /*车长宽高*/
    private TextView tv_find_car_sheep_length_width;
    /*限高*/
    private TextView tv_find_car_limit_height;

    /*拨打电话*/
    private ImageView iv_call_phone_number;



    private ImageView iv_market_phone;
    private View rl_cut_sheep_hair;
    private View rl_find_car;
    private View rl_cut_sheep_dung;


    public ServiceListHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;

        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        rl_cut_sheep_hair = view.findViewById(R.id.rl_cut_sheep_hair);
        rl_cut_sheep_dung = view.findViewById(R.id.rl_cut_sheep_dung);
        rl_find_car = view.findViewById(R.id.rl_find_car);

        civ_photo_circle = view.findViewById(R.id.civ_photo_circle);
        tv_market_name = view.findViewById(R.id.tv_market_name);

        tv_market_address = view.findViewById(R.id.tv_market_address);
        iv_market_cover = view.findViewById(R.id.iv_service_cover);

        //////////////////////剪羊毛/////////////////////////
        tv_service_name = view.findViewById(R.id.tv_service_name);
        tv_person_number = view.findViewById(R.id.tv_person_number);
        tv_sheep_price = view.findViewById(R.id.tv_sheep_price);
        tv_team_desc = view.findViewById(R.id.tv_team_desc);
        tv_market_price = view.findViewById(R.id.tv_market_price);

        ////////////////////////////拉羊粪//////////////////////
        tv_sheep_dung_name = view.findViewById(R.id.tv_sheep_dung_name);
        tv_sheep_dung_person_number = view.findViewById(R.id.tv_sheep_dung_person_number);
        tv_sheep_dung_cat_number = view.findViewById(R.id.tv_sheep_dung_car_number);
        tv_sheep_dung_team_price = view.findViewById(R.id.tv_sheep_dung_team_price);

        ///////////////////////////找车辆///////////////////////////////////
        tv_find_car_type = view.findViewById(R.id.tv_find_car_type);
        tv_find_car_service_type = view.findViewById(R.id.tv_find_car_service_type);
        tv_find_car_sheep_length_width = view.findViewById(R.id.tv_find_car_sheep_length_width);
        tv_find_car_limit_height = view.findViewById(R.id.tv_find_car_limit_height);

        iv_call_phone_number = view.findViewById(R.id.iv_find_car_call_phone_number);



        iv_market_phone = view.findViewById(R.id.iv_market_phone);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof ServiceInfo) {
            int serverType = ((ServiceInfo) info).getServerType();
            switch (serverType) {
                case HomeServiceFragment.TYPE_SERVICE_CUT_WOOL:
                case HomeServiceFragment.TYPE_SERVICE_VACCINE:
                    rl_cut_sheep_hair.setVisibility(View.VISIBLE);
                    rl_cut_sheep_dung.setVisibility(View.GONE);
                    rl_find_car.setVisibility(View.GONE);

                    tv_service_name.setText(((ServiceInfo) info).getTeamName());
                    tv_person_number.setText(((ServiceInfo) info).getTeamHumanScale() + "");
                    tv_sheep_price.setText(((ServiceInfo) info).getPrice() + "");
                    tv_team_desc.setText(((ServiceInfo) info).getTeamDesc());

                    break;
                case HomeServiceFragment.TYPE_SERVICE_SHEEP_DUNG:
                    rl_cut_sheep_hair.setVisibility(View.GONE);
                    rl_cut_sheep_dung.setVisibility(View.VISIBLE);
                    rl_find_car.setVisibility(View.GONE);

                    tv_sheep_dung_name.setText(((ServiceInfo) info).getTeamName());
                    tv_sheep_dung_person_number.setText(((ServiceInfo) info).getTeamHumanScale() + "");
                    tv_sheep_dung_cat_number.setText(((ServiceInfo) info).getTeamCarScale() + "");
                    tv_sheep_dung_team_price.setText(((ServiceInfo) info).getPrice() + "/车");

                    break;
                case HomeServiceFragment.TYPE_SERVICE_LOOK_CAR:
                    rl_cut_sheep_hair.setVisibility(View.GONE);
                    rl_cut_sheep_dung.setVisibility(View.GONE);
                    rl_find_car.setVisibility(View.VISIBLE);
                    if (((ServiceInfo) info).getCarType() != 0) {
                        tv_find_car_type.setText(((ServiceInfo) info).getCarType() == 1 ? "长途车" : "短途车");
                    }
                    String carServiceType;

                    switch (((ServiceInfo) info).getServiceType()) {
                        case 1:
                            carServiceType = "拉成品羊";
                            break;
                        case 2:
                            carServiceType = "拉饲料";
                            break;
                        case 3:
                            carServiceType = "拉玉米";
                            break;
                        case 4:
                            carServiceType = "冷藏车";
                            break;
                        default:
                            carServiceType = "多功能";
                            break;
                    }
                    tv_find_car_service_type.setText(carServiceType);
                    tv_find_car_sheep_length_width.setText(((ServiceInfo) info).getCarVolume());
                    tv_find_car_limit_height.setText(((ServiceInfo) info).getMaxHeight() + "");
                    break;
                default:
                    break;
            }


//            tv_market_address.setText(((ServiceInfo) info).getAddress());
//            tv_market_type_name.setText(((ServiceInfo) info).getVariety());
//            tv_market_sheep_number.setText(((ServiceInfo) info).getAmount()+"");
//            tv_market_price.setText(((ServiceInfo) info).getPrice()+"");

            List<String> images = ((ServiceInfo) info).getImages();
            if (images != null && images.size() > 0) {
                String coverUrl = images.get(0);
                Glide.with(context)
                        .asBitmap()
                        .load(coverUrl)
                        .apply(options)
                        .into(iv_market_cover);

            }


            iv_call_phone_number.setTag(info);
            iv_call_phone_number.setOnClickListener(listener);

        }
    }


}
