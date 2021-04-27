package com.meishe.yangquan.activity;

import android.view.View;


import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.BUHomeGoodsCommentFragment;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;
import java.util.ArrayList;

/**
 * 评论管理
 */
public class BUHomeCommentManagerActivity extends BaseActivity {

    private SlidingTabLayout mSlidingTabLayout;
    private MViewPager mViewPager;



    @Override
    protected int initRootView() {
        return R.layout.activity_bu_home_comment_manager;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);
        mViewPager.setScroll(true);
    }

    @Override
    public void initData() {

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("好评");
        mTitleList.add("中评");
        mTitleList.add("差评");
        mFragmentList.add(CommonListFragment.newInstance(true,Constants.TYPE_COMMON_BU_COMMENT_MANAGER,Constants.TYPE_COMMENT_GOOD_TYPE,0));
        mFragmentList.add(CommonListFragment.newInstance(true,Constants.TYPE_COMMON_BU_COMMENT_MANAGER,Constants.TYPE_COMMENT_MIDDLE_TYPE,0));
        mFragmentList.add(CommonListFragment.newInstance(true,Constants.TYPE_COMMON_BU_COMMENT_MANAGER,Constants.TYPE_COMMENT_BAD_TYPE,0));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("评论管理");
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