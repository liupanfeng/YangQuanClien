package com.meishe.yangquan.activity;


import android.view.View;
import android.widget.Button;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.umeng.analytics.MobclickAgent;

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


    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
