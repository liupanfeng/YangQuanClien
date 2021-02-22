package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.yangquan.R;

/**
 * 商品详情页面
 * @author 86188
 */
public class FeedGoodsDetailActivity extends BaseActivity {


    @Override
    protected int initRootView() {
        return R.layout.activity_feed_goods_detail;
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
        mTvTitle.setText("商品详情");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }
}