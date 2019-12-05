package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 信息发布
 */
public class MessagePublishActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup mRadioGroup;
    private int mCurrentPosition;
    private TextView mTvType;

    @Override
    protected int initRootView() {
        return R.layout.activity_message_publish;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRadioGroup = findViewById(R.id.rg_sheep_type);
        mTvType = findViewById(R.id.tv_type);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            mCurrentPosition = bundle.getInt("currentPosition");
        }

        if (mCurrentPosition == 0 || mCurrentPosition == 1) {
            mTvType.setVisibility(View.VISIBLE);
            mRadioGroup.setVisibility(View.VISIBLE);
        } else {
            mTvType.setVisibility(View.GONE);
            mRadioGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void initTitle() {
        mToolbar.setMyTitle("信息发布");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());

        mToolbar.setRightButtonVisible(View.VISIBLE);
        mToolbar.setOnRightButtonClickListener(new OnRightButtonListener());
        mToolbar.setRightButtonText("发布");
        mToolbar.setRightButtonBackground(getResources().getColor(R.color.mainColor));
    }

    @Override
    public void initListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = findViewById(checkedId);
        CharSequence text = rb.getText();
        ToastUtil.showToast(mContext, "" + text);
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
            ToastUtil.showToast(mContext, "发布消息");
        }
    }
}
