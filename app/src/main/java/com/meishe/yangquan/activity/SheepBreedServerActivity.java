package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-养殖服务
 */
public class SheepBreedServerActivity extends BaseActivity {


    private LinearLayout mLlApplyService;
    private LinearLayout mLlMyBusiness;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_breed_server;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mLlApplyService = findViewById(R.id.ll_apply_service);
        mLlMyBusiness = findViewById(R.id.ll_my_business);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("养殖服务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //申请服务
        mLlApplyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepBreedServerActivity.this,SheepApplyIntroduceActivity.class);
            }
        });
        //我的业务
        mLlMyBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepBreedServerActivity.this,SheepMyBusinessActivity.class);
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }

}
