package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.Button;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-我的业务
 */
public class SheepMyBusinessActivity extends BaseActivity {

    private Button mBtnAply;
    private Button mBtnBack;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_my_business;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnAply = findViewById(R.id.btn_apply);
        mBtnBack = findViewById(R.id.btn_back);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的业务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnAply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepMyBusinessActivity.this,SheepApplyIntroduceActivity.class);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
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
