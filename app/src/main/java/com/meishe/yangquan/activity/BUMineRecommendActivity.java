package com.meishe.yangquan.activity;


import android.view.View;
import com.meishe.yangquan.R;

/**
 * 商版-我的-反馈建议
 */
public class BUMineRecommendActivity extends BaseActivity {


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_mine_recommend;
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
        mTvTitle.setText("反馈建议");
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