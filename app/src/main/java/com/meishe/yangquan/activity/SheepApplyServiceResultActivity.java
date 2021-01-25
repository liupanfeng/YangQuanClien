package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.Button;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-申请服务-结果页面
 */
public class SheepApplyServiceResultActivity extends BaseActivity {

    private Button mBtnBack;
    private Button mBtnMyBusiness;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_apply_service_result;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnMyBusiness = findViewById(R.id.btn_my_business);
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

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnMyBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepApplyServiceResultActivity.this,SheepMyBusinessActivity.class);
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

}
