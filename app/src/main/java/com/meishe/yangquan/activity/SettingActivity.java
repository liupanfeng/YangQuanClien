package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.UserManager;

public class SettingActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected int initRootView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_login_out).setOnClickListener(this);
        findViewById(R.id.rl_about_sheep).setOnClickListener(this);

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("设置");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
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
        switch (view.getId()) {
            case R.id.btn_login_out:
                AppManager.getInstance().finishActivity();
                AppManager.getInstance().jumpActivity(SettingActivity.this,LoginActivity.class);
                UserManager.getInstance(mContext).setToken("");
                break;

            case R.id.rl_about_sheep:
                AppManager.getInstance().jumpActivity(this,AboutActivity.class);
                break;

            default:
                break;
        }
    }

}
