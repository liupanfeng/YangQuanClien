package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.BreedingArchivesInfo;
import com.meishe.yangquan.bean.FodderInfo;
import com.meishe.yangquan.bean.HouseKeeperSourceAnalysisInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 养殖助手-饲料分析-饲料列表
 *
 * @author 86188
 */
public class BreedingArchivesFoodAnalysisHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*饲料名称*/
    private TextView tv_name;
    /*饲料重量*/
    private EditText et_input_weight;
    /*输入单价*/
    private EditText et_input_single_price;
    /*日配比*/
    private TextView tv_percent;
    /*日配比*/
    private TextView tv_total_price;


    public BreedingArchivesFoodAnalysisHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_name = view.findViewById(R.id.tv_name);
        et_input_weight = view.findViewById(R.id.et_input_weight);
        tv_percent = view.findViewById(R.id.tv_percent);
        et_input_single_price = view.findViewById(R.id.et_input_single_price);
        tv_total_price = view.findViewById(R.id.tv_total_price);
    }

    @Override
    public void bindViewHolder(Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof FodderInfo) {

            tv_name.setText(((FodderInfo) info).getName());
            float percent = ((FodderInfo) info).getPercent();
            if (percent != 0) {
                tv_percent.setText(percent + "");
            }

            float price = ((FodderInfo) info).getPrice();
            if (price != 0) {
                et_input_single_price.setText(price + "");
            }

             float totalPrice = ((FodderInfo) info).getTotalPrice();
            if (totalPrice != 0) {
                tv_total_price.setText(totalPrice + "");
            }

            float weight = ((FodderInfo) info).getWeight();
            if (weight != 0) {
                et_input_weight.setText(((FodderInfo) info).getWeight() + "");
            }

            et_input_weight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int length = charSequence.length();
                        if (length > 0) {
                            String inputWeight = charSequence.toString().trim();
                            Float weight = Float.valueOf(inputWeight);
                            ((FodderInfo) info).setWeight(weight);
                            float price = ((FodderInfo) info).getPrice();
                            if (price != 0) {
                                tv_total_price.setText(price*weight+"");
                                ((FodderInfo) info).setTotalPrice(price*weight);
                            }else{
                                tv_total_price.setText("");
                                ((FodderInfo) info).setTotalPrice(0);
                            }
                        } else {
                            tv_total_price.setText("");
                            ((FodderInfo) info).setWeight(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            et_input_single_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int length = charSequence.length();
                        if (length > 0) {
                            String singlePrice = charSequence.toString().trim();
                            Float price = Float.valueOf(singlePrice);
                            ((FodderInfo) info).setPrice(price);
                            float weight = ((FodderInfo) info).getWeight();
                            if (weight!=0){
                                tv_total_price.setText(price*weight+"");
                                ((FodderInfo) info).setTotalPrice(price*weight);
                            }else{
                                tv_total_price.setText("");
                                ((FodderInfo) info).setTotalPrice(0);
                            }

                        } else {
                            tv_total_price.setText("");
                            ((FodderInfo) info).setPrice(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }

    }


}
