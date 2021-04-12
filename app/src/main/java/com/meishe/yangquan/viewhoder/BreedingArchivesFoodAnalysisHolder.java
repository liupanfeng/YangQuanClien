package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 养殖助手-饲料分析-饲料列表
 *
 * @author 86188
 */
public class BreedingArchivesFoodAnalysisHolder extends BaseViewHolder {


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
    public void bindViewHolder(Context context, final BaseInfo info, final int position, View.OnClickListener listener) {
        if (info instanceof FodderInfo) {
            tv_name.setText(((FodderInfo) info).getName());
            float percent = ((FodderInfo) info).getPercent();
            if (percent != 0) {
                tv_percent.setText(percent + "");
            }


            if (et_input_single_price.getTag() instanceof TextWatcher) {
                et_input_single_price.removeTextChangedListener((TextWatcher) et_input_single_price.getTag());
            }

            if (et_input_weight.getTag() instanceof TextWatcher) {
                et_input_weight.removeTextChangedListener((TextWatcher) et_input_weight.getTag());
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
                et_input_weight.setText(weight + "");
            }



             TextWatcher weightTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        int length = editable.length();
                        if (length > 0) {
                            String inputWeight = editable.toString().trim();
                            Float weight = Float.valueOf(inputWeight);
                            ((FodderInfo) info).setWeight(weight);

                            float price = ((FodderInfo) info).getPrice();
                            if (price != 0) {
                                tv_total_price.setText(price * weight + "");
                                ((FodderInfo) info).setTotalPrice(price * weight);

                            } else {
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
            };


            et_input_weight.addTextChangedListener(weightTextWatcher);
            et_input_weight.setTag(weightTextWatcher);


             TextWatcher singlePriceTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        int length = editable.length();
                        if (length > 0) {
                            String singlePrice = editable.toString().trim();
                            Float price = Float.valueOf(singlePrice);
                            ((FodderInfo) info).setPrice(price);

                            float weight = ((FodderInfo) info).getWeight();
                            if (weight != 0) {
                                tv_total_price.setText(price * weight + "");
                                ((FodderInfo) info).setTotalPrice(price * weight);

                            } else {
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
            };

            et_input_single_price.addTextChangedListener(singlePriceTextWatcher);
            et_input_single_price.setTag(singlePriceTextWatcher);


            if (((FodderInfo) info).getType() == 2) {
                et_input_single_price.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                et_input_weight.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        }

    }


}
