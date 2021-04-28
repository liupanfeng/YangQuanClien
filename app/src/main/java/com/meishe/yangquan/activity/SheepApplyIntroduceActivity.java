package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-申请服务介绍
 */
public class SheepApplyIntroduceActivity extends BaseActivity {


    private Button mBtnApply;
    private TextView tv_content;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_apply_introduce;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnApply = findViewById(R.id.btn_apply);
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if (extras!=null){
                String title = extras.getString("title");
                String content = extras.getString("content");
                mTvTitle.setText(title);
                tv_content.setText(content);
            }
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepApplyIntroduceActivity.this,SheepApplyServiceActivity.class);
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
