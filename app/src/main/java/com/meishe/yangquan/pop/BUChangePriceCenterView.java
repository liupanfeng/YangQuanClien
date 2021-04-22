package com.meishe.yangquan.pop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.CenterPopupView;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ReceiverInfo;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.RoundAngleImageView;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description : 商版-改价
 */
public class BUChangePriceCenterView extends CenterPopupView {

    private Context mContext;
    private BaseInfo mBaseInfo;
    private TextView mTvTitle;
    private View mIvBack;

    /*新价格*/
    private EditText et_bu_new_price;
    /*原价*/
    private TextView tv_bu_old_price;

    private Button btn_change_price;


    public static BUChangePriceCenterView create(Context context, BaseInfo baseInfo, OnChangePriceListener listener) {
        return (BUChangePriceCenterView) new XPopup.Builder(context)
                .asCustom(new BUChangePriceCenterView(context, baseInfo).setOnReceiveAddressListener(listener));
    }

    public BUChangePriceCenterView(@NonNull Context context, BaseInfo baseInfo) {
        super(context);
        this.mBaseInfo = baseInfo;
        this.mContext = context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.bu_pop_change_price_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(GONE);
        mTvTitle.setText("修改价格");

        tv_bu_old_price = findViewById(R.id.tv_bu_old_price);
        et_bu_new_price = findViewById(R.id.et_bu_new_price);
        btn_change_price = findViewById(R.id.btn_change_price);

        if (mBaseInfo instanceof BUOrderInfo){
            float price = ((BUOrderInfo) mBaseInfo).getPrice();
            tv_bu_old_price.setText(price+"");
        }

        btn_change_price.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPrice = tv_bu_old_price.getText().toString();
                if (TextUtils.isEmpty(oldPrice)){
                    return;
                }

                String newPrice = et_bu_new_price.getText().toString();
                if (TextUtils.isEmpty(newPrice)){
                    return;
                }

                if (mOnChangePriceListener!=null){
                    mOnChangePriceListener.onChangePrice(oldPrice,newPrice);
                }

                dismiss();

            }
        });
    }


    private OnChangePriceListener mOnChangePriceListener;

    public BUChangePriceCenterView setOnReceiveAddressListener(OnChangePriceListener onReceiveAddressListener){
        mOnChangePriceListener=onReceiveAddressListener;
        return this;
    }

    public interface OnChangePriceListener {

        void onChangePrice(String oldPrice,String newPrice);

    }


}
