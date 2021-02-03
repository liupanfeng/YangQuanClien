package com.meishe.yangquan.viewhoder;

import android.content.Context;
import android.text.Editable;
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
import com.meishe.yangquan.bean.SheepLossInfo;
import com.meishe.yangquan.bean.SheepVaccineInfo;
import com.meishe.yangquan.utils.FormatDateUtil;

/**
 * 羊管家 养殖助手  疫苗记录
 *
 * @author 86188
 */
public class SheepVaccinesHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*时间*/
    private TextView tv_time;
    /*疫苗内容*/
    private EditText et_vaccine_content;
    /*数量*/
    private TextView tv_amount;
    /*价格*/
    private EditText et_input_price;
    /*保存*/
    private TextView tv_save;


    public SheepVaccinesHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_time = view.findViewById(R.id.tv_time);
        et_vaccine_content = view.findViewById(R.id.et_vaccine_content);
        et_input_price = view.findViewById(R.id.et_input_price);
        tv_amount = view.findViewById(R.id.tv_amount);
        tv_save = view.findViewById(R.id.tv_save);

    }

    @Override
    public void bindViewHolder(Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepVaccineInfo) {
            String time = ((SheepVaccineInfo) info).getRecordDate();
            if (TextUtils.isEmpty(time)) {
                tv_time.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
                ((SheepVaccineInfo) info).setRecordDate(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
            } else {
                tv_time.setText(time);
            }

            int amount = ((SheepVaccineInfo) info).getAmount();
            if (amount != 0) {
                tv_amount.setText(amount + "支");
            }

            float price = ((SheepVaccineInfo) info).getPrice();
            if (price != 0) {
                et_input_price.setText(price + "");
            }
            String recordContent = ((SheepVaccineInfo) info).getRecordContent();
            if (!TextUtils.isEmpty(recordContent)) {
                et_vaccine_content.setText(recordContent);
            }

            et_vaccine_content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int length = charSequence.length();
                    if (length > 0) {
                        String content = charSequence.toString().trim();
                        ((SheepVaccineInfo) info).setRecordContent(content);
                    }else{
                        ((SheepVaccineInfo) info).setRecordContent(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            et_input_price.addTextChangedListener(new TextWatcher() {
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
                            ((SheepVaccineInfo) info).setPrice(price);
                        }else{
                            ((SheepVaccineInfo) info).setPrice(0);
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
