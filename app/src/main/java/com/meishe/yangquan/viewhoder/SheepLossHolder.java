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
import com.meishe.yangquan.bean.SheepLossInfo;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.utils.FormatCurrentData;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.SharedPreferencesUtil;
import com.meishe.yangquan.utils.Util;

import org.greenrobot.eventbus.EventBus;

/**
 * 羊管家 养殖助手  损耗记录
 *
 * @author 86188
 */
public class SheepLossHolder extends BaseViewHolder {

    private final RequestOptions options;

    /*时间*/
    private TextView tv_time;
    /*数量*/
    private EditText et_amount;
    /*总价*/
    private TextView tv_price;
    /*保存*/
    private TextView tv_save;


    public SheepLossHolder(@NonNull View itemView, BaseRecyclerAdapter adapter) {
        super(itemView);
        mAdapter = adapter;
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);
    }

    @Override
    protected void initViewHolder(View view, Object... obj) {
        tv_time = view.findViewById(R.id.tv_time);
        et_amount = view.findViewById(R.id.et_input_amount);
        tv_price = view.findViewById(R.id.tv_price);
        tv_save = view.findViewById(R.id.tv_save);

    }

    @Override
    public void bindViewHolder(final Context context, final BaseInfo info, int position, View.OnClickListener listener) {
        if (info instanceof SheepLossInfo) {
            String time = ((SheepLossInfo) info).getRecordDate();
            if (TextUtils.isEmpty(time)) {
                tv_time.setText(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
                ((SheepLossInfo) info).setRecordDate(FormatDateUtil.longToString(System.currentTimeMillis(),
                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY).substring(5));
            } else {
                tv_time.setText(time);
            }

            int amount = ((SheepLossInfo) info).getAmount();
            if (amount != 0) {
                et_amount.setText(amount + "");
            }

            float totalPrice = ((SheepLossInfo) info).getPrice();
            if (totalPrice != 0) {
                tv_price.setText(totalPrice + "");
            }

            et_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int length = charSequence.length();
                    if (length > 0) {
                        String content = charSequence.toString().trim();
                        Integer amount = Integer.valueOf(content);
                        ((SheepLossInfo) info).setAmount(amount);
                        String priceStr = SharedPreferencesUtil.getInstance(context).getString(((SheepLossInfo) info).getBatchId() + "");
                        if (!TextUtils.isEmpty(priceStr)) {
                            float price = Float.valueOf(priceStr);
                            tv_price.setText(price * amount + "");
                            ((SheepLossInfo) info).setPrice(price * amount);
                        }
                    } else {
                        ((SheepLossInfo) info).setAmount(0);
                        ((SheepLossInfo) info).setPrice(0);
                        tv_price.setText(0 + "");
                    }
                    EventBus.getDefault().post(new MessageEvent().setEventType(MessageEvent.MESSAGE_TYPE_LOSS_TOTAL_PRICE));
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
