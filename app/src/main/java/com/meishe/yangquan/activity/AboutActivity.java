package com.meishe.yangquan.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 关于页面
 */
public class AboutActivity extends BaseActivity {


    private RelativeLayout rl_user_agreement;
    private RelativeLayout rl_privacy_policy;
    private TextView mTvTitle;
    private ImageView mIvBack;


    @Override
    protected int initRootView() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        rl_user_agreement = findViewById(R.id.rl_user_agreement);
        rl_privacy_policy = findViewById(R.id.rl_privacy_policy);

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("关于羊圈APP");
    }

    @Override
    public void initListener() {
        rl_user_agreement.setOnClickListener(this);
        rl_privacy_policy.setOnClickListener(this);
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
            case R.id.rl_privacy_policy:
                ToastUtil.showToast(this, "已经是最新版本了");
                break;
            case R.id.rl_user_agreement:
                ToastUtil.showToast(this, "反馈");
                break;
        }
    }


    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {

        @Override
        public void onClick() {
            finish();
        }
    }

    //    SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
    private class OnRightImageListener implements CustomToolbar.OnRightButtonClickListener {
        @Override
        public void onClick() {
//            new ShareAction(AboutActivity.this).withText("羊圈App下载地址：" + "http://59.110.142.42:8080/images/yangquan-release.apk").setDisplayList(SHARE_MEDIA.WEIXIN)
//                    .setCallback(new UMShareListener() {
//                        @Override
//                        public void onStart(SHARE_MEDIA share_media) {
//
//                        }
//
//                        @Override
//                        public void onResult(SHARE_MEDIA share_media) {
//
//                        }
//
//                        @Override
//                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                        }
//
//                        @Override
//                        public void onCancel(SHARE_MEDIA share_media) {
//
//                        }
//                    }).open();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
