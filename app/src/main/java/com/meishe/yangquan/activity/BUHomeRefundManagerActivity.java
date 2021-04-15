package com.meishe.yangquan.activity;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.BUHomeGoodsOrderFragment;
import com.meishe.yangquan.fragment.BUHomeGoodsRefundFragment;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;

/**
 * 退货管理
 */
public class BUHomeRefundManagerActivity extends BaseActivity {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_home_refund_manager;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);


        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);
    }

    @Override
    public void initData() {

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("进行中");
        mTitleList.add("已完成");

//        mFragmentList.add(BUHomeGoodsRefundFragment.onInstance(Constants.TYPE_REFUND_ING_TYPE));
//        mFragmentList.add(BUHomeGoodsRefundFragment.onInstance(Constants.TYPE_REFUND_FINISH_TYPE));


        mFragmentList.add(CommonListFragment.newInstance(true, Constants.TYPE_COMMON_BU_REFUND, Constants.TYPE_REFUND_ING_TYPE));
        mFragmentList.add(CommonListFragment.newInstance(true, Constants.TYPE_COMMON_BU_REFUND, Constants.TYPE_REFUND_FINISH_TYPE));


        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("退货管理");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }
}