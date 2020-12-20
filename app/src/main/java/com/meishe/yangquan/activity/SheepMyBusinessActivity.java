package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meishe.yangquan.R;
import com.meishe.yangquan.utils.AppManager;

/**
 * 羊管家-我的业务
 */
public class SheepMyBusinessActivity extends BaseActivity {

    private Button mBtnAply;
    private Button mBtnBack;
    /**
     * 没有服务申请信息
     */
    private LinearLayout mLlNoMessage;
    /**
     * 复审
     */
    private RelativeLayout mRlMyBusinessReview;
    /**
     * 申请
     */
    private RelativeLayout mRlMyBusinessApply;
    /**
     * 初审
     */
    private RelativeLayout mRlMyBusinessFirst;
    /**
     * 完成
     */
    private RelativeLayout mRlMyBusinessFinish;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_my_business;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnAply = findViewById(R.id.btn_apply);
        mBtnBack = findViewById(R.id.btn_back);
        mLlNoMessage = findViewById(R.id.ll_no_message);
        mRlMyBusinessReview = findViewById(R.id.rl_my_business_review);
        mRlMyBusinessApply = findViewById(R.id.rl_my_business_apply);
        mRlMyBusinessFirst = findViewById(R.id.rl_my_business_first);
        mRlMyBusinessFinish = findViewById(R.id.rl_my_business_finish);
    }

    @Override
    public void initData() {
        showNoMessage();
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的业务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnAply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepMyBusinessActivity.this,SheepApplyIntroduceActivity.class);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
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

    public void showNoMessage(){
        mLlNoMessage.setVisibility(View.VISIBLE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }

    /**
     * 展示申请
     */
    public void showApplyMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.VISIBLE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }


    /**
     * 展示初审
     */
    public void showFirstMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.VISIBLE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }


    /**
     * 展示复审
     */
    public void showReviewMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.VISIBLE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }

    /**
     * 展示完成
     */
    public void showFinishMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.VISIBLE);
    }
}
