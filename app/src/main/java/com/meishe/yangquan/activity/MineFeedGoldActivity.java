package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.TextView;

import com.meishe.yangquan.R;

/**
 * 饲料金页面
 */
public class MineFeedGoldActivity extends BaseActivity {

    private TextView mTvTitle;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_gold;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
    }

    @Override
    public void initData() {
        mTvTitle.setText("饲料金");
    }

    @Override
    public void initTitle() {

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
