package com.meishe.yangquan.activity;

import android.content.Intent;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.fragment.ShareBottomSheetDialogFragment;
import com.meishe.yangquan.utils.ShareUtil;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import static com.meishe.yangquan.fragment.ShareBottomSheetDialogFragment.SHARE_TO_FRIENDS;
import static com.meishe.yangquan.fragment.ShareBottomSheetDialogFragment.SHARE_TO_SINA;
import static com.meishe.yangquan.fragment.ShareBottomSheetDialogFragment.SHARE_TO_WECHAT;

/**
 * 关于页面
 */
public class AboutActivity extends BaseActivity {


    private ShareBottomSheetDialogFragment mShareBottomSheetDialog;

    @Override
    protected int initRootView() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("关于");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setRightButtonVisible(View.VISIBLE);
        mToolbar.setRightButton(R.mipmap.ic_go_next);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
        mToolbar.setOnRightButtonClickListener(new OnRightButtonListener());
    }

    @Override
    public void initListener() {

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

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {

        @Override
        public void onClick() {
            finish();
        }
    }

    private class OnRightButtonListener implements CustomToolbar.OnRightButtonClickListener {
        @Override
        public void onClick() {
            new ShareAction(AboutActivity.this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {

                        }
                    }).open();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
