package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.libbase.adpater.BaseViewHolder;
import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;


/**
 * 商版-申请开店页面
 */
public class BUApplyShoppingActivity extends BaseActivity {


    private View btn_bu_to_base_info;
    private View btn_bu_to_real_name;
    private View btn_bu_to_business_license;

    @Override
    protected int initRootView() {
        return R.layout.activity_b_u_apply_shopping;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        btn_bu_to_base_info = findViewById(R.id.btn_bu_to_base_info);
        btn_bu_to_real_name = findViewById(R.id.btn_bu_to_real_name);
        btn_bu_to_business_license = findViewById(R.id.btn_bu_to_business_license);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("申请开店");
    }

    @Override
    public void initListener() {
        btn_bu_to_base_info.setOnClickListener(this);
        btn_bu_to_real_name.setOnClickListener(this);
        btn_bu_to_business_license.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.btn_bu_to_base_info:
                AppManager.getInstance().jumpActivity(mContext, BUShoppingBaseInfoActivity.class);
                //基本信息
                break;
            case R.id.btn_bu_to_real_name:
                //实名认证
                break;
            case R.id.btn_bu_to_business_license:
                //营业执照
                break;
        }
    }
}