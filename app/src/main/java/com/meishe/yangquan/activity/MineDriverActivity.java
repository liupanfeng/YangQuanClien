package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.yangquan.R;

/**
 * 完善驾驶信息页面
 */
public class MineDriverActivity extends BaseActivity {


    @Override
    protected int initRootView() {
        return R.layout.activity_mine_driver;
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
        mTvTitle.setText("驾驶证上传");
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