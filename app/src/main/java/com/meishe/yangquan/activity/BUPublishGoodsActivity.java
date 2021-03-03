package com.meishe.yangquan.activity;

import android.view.View;
import com.meishe.yangquan.R;

/**
 * 商版发布商品
 */
public class BUPublishGoodsActivity extends BaseActivity {


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_publish_goods;
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
        mTvTitle.setText("发布商品");
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