package com.meishe.yangquan.activity;

import android.view.View;

import com.meishe.yangquan.R;

/**
 *羊管家-养殖助手
 */
public class SheepBreedHelperActivity extends BaseActivity {


    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_breed_helper;
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
        mTvTitle.setText("养殖助手");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

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
