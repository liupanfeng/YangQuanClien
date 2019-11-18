package com.meishe.yangquan.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 完善资料
 */
public class PerfectInformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initRootView() {
        return R.layout.activity_perfect_infomation;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("完善资料");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
        mToolbar.setLeftButtonVisible(View.VISIBLE);
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

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }
}
