package com.meishe.yangquan.activity;


import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 版本更新
 */
public class VersionUpdateActivity extends BaseActivity {

    private Button mBtnVersionUpdate;

    @Override
    protected int initRootView() {
        return R.layout.activity_version_update;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mBtnVersionUpdate = findViewById(R.id.btn_update);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("版本更新");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
        mToolbar.setLeftButtonVisible(View.VISIBLE);
    }

    @Override
    public void initListener() {
        mBtnVersionUpdate.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        ToastUtil.showToast(mContext, "当前已经是最新版本");
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

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }
}
