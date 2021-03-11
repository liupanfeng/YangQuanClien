package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.manager.CacheDataManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.UserManager;

public class SettingActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private TextView tv_cache_total;

    @Override
    protected int initRootView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_login_out).setOnClickListener(this);
        findViewById(R.id.rl_about_sheep).setOnClickListener(this);
        findViewById(R.id.rl_clear_cache).setOnClickListener(this);


        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        tv_cache_total = findViewById(R.id.tv_cache_total);
        mLoading = findViewById(R.id.loading);
        try {
            tv_cache_total.setText(CacheDataManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                showLoading();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UserManager.getInstance(mContext).setToken("");
                        UserManager.getInstance(mContext).setUser(null);
                        AppManager.getInstance().finishAllActivity();
                        AppManager.getInstance().jumpActivity(SettingActivity.this,LoginActivity.class);
                    }
                },1800);

                break;

            case R.id.rl_about_sheep:
                AppManager.getInstance().jumpActivity(this,AboutActivity.class);
                break;
            case R.id.rl_clear_cache:
                showLoading();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                        CacheDataManager.clearAllCache(mContext);
                        try {
                            tv_cache_total.setText(CacheDataManager.getTotalCacheSize(mContext));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },2000);

                break;

            default:
                break;
        }
    }

}
