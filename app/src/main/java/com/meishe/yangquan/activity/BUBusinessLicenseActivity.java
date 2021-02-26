package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.yangquan.R;

/**
 * 营业执照页面
 */
public class BUBusinessLicenseActivity extends BaseActivity {

    @Override
    protected int initRootView() {
        return R.layout.activity_bu_business_license;
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
        mTvTitle.setText("营业执照");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}