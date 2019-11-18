package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.CustomToolbar;

/**
 * 信息发布
 */
public class MessagePublishActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup mRadioGroup;
    @Override
    protected int initRootView() {
        return R.layout.activity_message_publish;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRadioGroup=findViewById(R.id.rg_sheep_type);
    }

    @Override
    public void initData() {

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
        ToastUtil.showToast(mContext,""+text);
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
            ToastUtil.showToast(mContext,"发布消息");
        }
    }
}
