package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.SheepHairInfo;
import com.meishe.yangquan.bean.SheepVaccineInfo;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.Util;

/**
 * 羊管家 养殖助手 剪羊毛
 *
 * @author 86188
 */
public class SheepCutHairHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*时间*/
    private TextView tv_time;
    /*数量*/
    private TextView tv_amount;
    /*单价*/
    private TextView tv_single_price;
    /*总价*/
    private EditText et_total_price;
    /*总价*/
    private TextView tv_save;

    public SheepCutHairHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_time = view.findViewById(R.id.tv_time);
        tv_amount = view.findViewById(R.id.tv_amount);
        et_total_price = view.findViewById(R.id.et_total_price);
        tv_save = view.findViewById(R.id.tv_save);

//        et_total_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        tv_amount.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public void bindViewHolder(Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepHairInfo) {
            String time = ((SheepHairInfo) info).getRecordDate();
            if (TextUtils.isEmpty(time)) {
                tv_time.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
                ((SheepHairInfo) info).setRecordDate(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
            } else {
                tv_time.setText(time);
            }
//
            int amount = ((SheepHairInfo) info).getAmount();
            if (amount != 0) {
                tv_amount.setText(amount + "");
            }

            float totalPrice = ((SheepHairInfo) info).getPrice();
            if (totalPrice != 0) {
                et_total_price.setText(totalPrice + "");
            }

            et_total_price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int length = charSequence.length();
                        if (length > 0) {
                            String totalPrice = charSequence.toString().trim();
                            Float price = Float.valueOf(totalPrice);
                            ((SheepHairInfo) info).setPrice(price);
                        } else {
                            ((SheepHairInfo) info).setPrice(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            tv_save.setTag(info);
            tv_save.setOnClickListener(listener);


        }

    }


}
