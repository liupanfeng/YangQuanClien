package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * s首页-市场发布
 */
public class PublishMarketActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;

    /*标题*/
    private EditText mEtMarketInputTitle;
    /*品种*/
    private EditText mEtMarketInputVarity;
    /*重量*/
    private EditText mEtMarketInputWeight;
    /*数量*/
    private EditText mEtMarketInputAmount;
    /*价格*/
    private EditText mEtMarketInputPrice;
    /*电话*/
    private EditText mEtMarketInputPhone;
    /*发布*/
    private Button mBtnPublish;

    private int mMarketType;
    private String mTitle;
    private String mVarity;
    private String mAmount;
    private String mPrice;
    private String mPhone;
    private String mWeight;

    @Override
    protected int initRootView() {
        return R.layout.activity_market_publish;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnPublish = findViewById(R.id.btn_publish);

        mEtMarketInputTitle = findViewById(R.id.et_market_input_title);
        mEtMarketInputVarity = findViewById(R.id.et_market_input_variety);
        mEtMarketInputWeight = findViewById(R.id.et_market_input_weight);
        mEtMarketInputAmount = findViewById(R.id.et_market_input_amount);
        mEtMarketInputPrice = findViewById(R.id.et_market_input_price);
        mEtMarketInputPhone = findViewById(R.id.et_market_input_phone);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mMarketType = extras.getInt("market_type");
            }
        }
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("市场发布");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnPublish.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_publish:
                mTitle = mEtMarketInputTitle.getText().toString().trim();
                if (Util.checkNull(mTitle)) {
                    ToastUtil.showToast(mContext, "标题必须填写");
                    return;
                }

                mVarity = mEtMarketInputVarity.getText().toString().trim();
                if (Util.checkNull(mVarity)) {
                    ToastUtil.showToast(mContext, "品种必须填写");
                    return;
                }

                mWeight = mEtMarketInputWeight.getText().toString().trim();
                if (Util.checkNull(mWeight)) {
                    ToastUtil.showToast(mContext, "重量必须填写");
                    return;
                }
                mAmount = mEtMarketInputAmount.getText().toString().trim();
                if (Util.checkNull(mAmount)) {
                    ToastUtil.showToast(mContext, "数量必须填写");
                    return;
                }
                mPrice = mEtMarketInputPrice.getText().toString().trim();
                if (Util.checkNull(mPrice)) {
                    ToastUtil.showToast(mContext, "价格必须填写");
                    return;
                }
                mPhone = mEtMarketInputPhone.getText().toString().trim();
                if (Util.checkNull(mPhone)) {
                    ToastUtil.showToast(mContext, "电话必须填写");
                    return;
                }

                publishMarket();
                break;
        }
    }

    /**
     * 发布市场
     */
    private void publishMarket() {
        String token = UserManager.getInstance(mContext).getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", mMarketType);
        param.put("title", mTitle);
        param.put("variety", mVarity);
        param.put("weight", mWeight);
        param.put("amount", mAmount);
        param.put("phone", mPhone);
        param.put("price", mPrice);

        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_ADD_MARKET, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult serverResult) {
                if (serverResult != null) {
                    if (serverResult.getCode() != 1) {
                        ToastUtil.showToast(mContext, serverResult.getMsg());
                    } else {
                        ToastUtil.showToast(mContext, "发布成功");
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {

    }

    @Override
    public void onError(int type, Object obj) {

    }
}
