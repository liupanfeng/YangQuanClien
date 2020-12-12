package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.Button;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-申请服务
 */
public class SheepApplyServiceActivity extends BaseActivity {


    private Button mBtnCommit;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_apply_service;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnCommit = findViewById(R.id.btn_commit);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("申请服务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepApplyServiceActivity.this,SheepApplyServiceResultActivity.class);
            }
        });
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
