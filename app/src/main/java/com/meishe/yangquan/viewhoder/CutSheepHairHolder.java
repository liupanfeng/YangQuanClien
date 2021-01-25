package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
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
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.Util;

/**
 * 羊管家 养殖助手 剪羊毛
 *
 * @author 86188
 */
public class CutSheepHairHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*时间*/
    private TextView tv_time;
    /*数量*/
    private EditText et_amount;
    /*单价*/
    private TextView tv_single_price;
    /*总价*/
    private TextView et_total_price;


    public CutSheepHairHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_time = view.findViewById(R.id.tv_time);
        et_amount = view.findViewById(R.id.et_amount);
        tv_single_price = view.findViewById(R.id.tv_single_price);
        et_total_price = view.findViewById(R.id.et_total_price);

        et_total_price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_amount.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public void bindViewHolder(Context context, BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepHairInfo) {
            long time = ((SheepHairInfo) info).getTime();
            if (time == 0) {
                tv_time.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            } else {
                tv_time.setText(FormatDateUtil.longToString(time,
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
            }

            int amount = ((SheepHairInfo) info).getAmount();
            if (amount != 0) {
                et_amount.setText(amount + "");
            }
            float totalPrice = ((SheepHairInfo) info).getTotalPrice();
            if (totalPrice != 0) {
                et_total_price.setText(totalPrice + "");
            }

            if (amount != 0 && totalPrice != 0) {
                tv_single_price.setText(FormatCurrentData.getFormatStringFromFloat(totalPrice / amount * 1f));
            } else {
                tv_single_price.setText(0.0 + "");
            }

            et_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int length = charSequence.length();
                    if (length > 0) {
                        String amount = charSequence.toString().trim();
                        Integer integer = Integer.valueOf(amount);
                        String totalPrice = et_total_price.getText().toString().trim();
                        if (!Util.checkNull(totalPrice)) {
                            Float aFloat = Float.valueOf(totalPrice);
                            tv_single_price.setText(FormatCurrentData.
                                    getFormatStringFromFloat(aFloat / integer * 1f));
                        }
                    } else {
                        tv_single_price.setText(0.0 + "");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

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
                            Integer integer = Integer.valueOf(totalPrice);
                            String amount = et_amount.getText().toString().trim();
                            if (!Util.checkNull(amount)) {
                                Float aFloat = Float.valueOf(amount);
                                tv_single_price.setText(FormatCurrentData.
                                        getFormatStringFromFloat(integer / aFloat * 1f));
                            }
                        } else {
                            tv_single_price.setText(0.0 + "");
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
