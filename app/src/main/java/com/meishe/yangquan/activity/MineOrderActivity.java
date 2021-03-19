package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.utils.Constants;

/**
 * 我的-订单页面
 */
public class MineOrderActivity extends BaseActivity {

    /**
     * 订单类型
     */
    private int mType;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int mSelectIndex;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_order;
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
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if (extras!=null){
                mSelectIndex = extras.getInt(Constants.KEY_TAB_SELECT_INDEX);
                mType = extras.getInt(Constants.KEY_ORDER_STATE_TYPE);
            }
        }

        initTabLayout();
        mSlidingTabLayout.setCurrentTab(mSelectIndex);
    }


    private void initTabLayout() {
        mFragmentList.clear();
        mTitleList.clear();
        CommonListFragment allFragment= CommonListFragment.newInstance(mType);
        mFragmentList.add(allFragment);

        CommonListFragment waitPayFragment= CommonListFragment.newInstance(mType);
        mFragmentList.add(waitPayFragment);

        CommonListFragment waitReceive= CommonListFragment.newInstance(mType);
        mFragmentList.add(waitReceive);

        CommonListFragment waitComment= CommonListFragment.newInstance(mType);
        mFragmentList.add(waitComment);

        CommonListFragment waitRefund= CommonListFragment.newInstance(mType);
        mFragmentList.add(waitRefund);

        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待收货");
        mTitleList.add("待评价");
        mTitleList.add("退款");

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(),mFragmentList,mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }






    @Override
    public void initTitle() {
        mTvTitle.setText("我的订单");
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