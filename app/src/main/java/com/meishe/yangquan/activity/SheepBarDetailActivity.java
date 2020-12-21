package com.meishe.yangquan.activity;

import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.wiget.CustomTextView;

/**
 * 羊吧-详情页面
 *
 * @author 86188
 */
public class SheepBarDetailActivity extends BaseActivity {

    /**
     * 最新回复
     */
    public static final int TYPE_SHEEP_BAR_NEWEST = 1;
    /**
     * 只看楼主
     */
    public static final int TYPE_SHEEP_BAR_COMMEND = 2;

    private CustomTextView mTvSheepBarNewest;
    private CustomTextView mTvSheepBarCommand;
    private int mListType;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_bar_detail;
    }

    @Override
    public void initView() {
        mTvSheepBarNewest = findViewById(R.id.tv_sheep_bar_newest);
        mTvSheepBarCommand = findViewById(R.id.tv_sheep_bar_command);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        mTvSheepBarNewest.setOnClickListener(this);
        mTvSheepBarCommand.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*最新回复*/
            case R.id.tv_sheep_bar_newest:
                mListType = TYPE_SHEEP_BAR_NEWEST;
                mTvSheepBarNewest.setSelected(true);
                mTvSheepBarCommand.setSelected(false);
                getServiceDataFromServer(mListType);
                break;
            /*只看楼主*/
            case R.id.tv_sheep_bar_command:
                mListType = TYPE_SHEEP_BAR_COMMEND;
                mTvSheepBarCommand.setSelected(true);
                mTvSheepBarNewest.setSelected(false);
                getServiceDataFromServer(mListType);
                break;
            default:
                break;
        }
    }

    private void getServiceDataFromServer(int mListType) {

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
}