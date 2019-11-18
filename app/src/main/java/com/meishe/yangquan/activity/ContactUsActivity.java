package com.meishe.yangquan.activity;
import android.graphics.Color;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseActivity {

    @Override
    protected int initRootView() {
        return R.layout.activity_contact_us;
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
        mToolbar.setMyTitleColor(Color.BLACK);
        mToolbar.setMyTitle("联系我们");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
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
