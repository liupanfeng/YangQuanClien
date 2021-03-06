package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;

/**
 * 我的-订单页面
 */
public class MineOrderActivity extends BaseActivity {

    /**
     * 订单类型
     */
    private int mType;

    private SlidingTabLayout mSlidingTabLayout;
    private MViewPager mViewPager;
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
        mViewPager.setScroll(false);
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

    /**
     * 0 全部
     * 1 待付款
     * 2 待收货
     * 3 完成
     * 4 退货
     */
    private void initTabLayout() {
        mFragmentList.clear();
        mTitleList.clear();

//        CommonListFragment allFragment= CommonListFragment.newInstance(true,mType,Constants.TYPE_LIST_TYPE_ORDER_ALL_TYPE,0);
//        mFragmentList.add(allFragment);

        CommonListFragment waitPayFragment= CommonListFragment.newInstance(true,mType,Constants.TYPE_LIST_TYPE_ORDER_WAIT_PAY_TYPE,0);
        mFragmentList.add(waitPayFragment);

        CommonListFragment waitReceive= CommonListFragment.newInstance(true,mType,Constants.TYPE_LIST_TYPE_ORDER_WAIT_RECEIVE_TYPE,0);
        mFragmentList.add(waitReceive);

        CommonListFragment waitComment= CommonListFragment.newInstance(true,mType,Constants.TYPE_LIST_TYPE_ORDER_WAIT_COMMENT_TYPE,0);
        mFragmentList.add(waitComment);

        CommonListFragment waitRefund= CommonListFragment.newInstance(true,mType,Constants.TYPE_LIST_TYPE_ORDER_REFUND_TYPE,0);
        mFragmentList.add(waitRefund);

//        mTitleList.add("全部");
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