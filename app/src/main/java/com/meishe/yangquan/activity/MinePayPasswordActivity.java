package com.meishe.yangquan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;

/**
 * @author 86188
 * 我的-支付密码
 */
public class MinePayPasswordActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_pay_password;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("支付密码");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

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