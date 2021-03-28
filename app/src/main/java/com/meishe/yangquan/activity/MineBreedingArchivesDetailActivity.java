package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.fragment.SheepBreedHelperBaseMessage;
import com.meishe.yangquan.fragment.SheepBreedHelperBenefitAnalysisFragment;
import com.meishe.yangquan.fragment.SheepBreedHelperProcessFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 养殖档案详情页面
 */
public class MineBreedingArchivesDetailActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private MViewPager mViewPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    /*批次id*/
    private int mBatchId;
    /*剩余出栏羊*/
    private int mCurrentCulturalQuantity;

    private long mInitTime;
    @Override
    protected int initRootView() {
        return R.layout.activity_mine_breeding_archives_detail;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                mBatchId= extras.getInt(Constants.TYPE_KEY_BATCH_ID);
                mCurrentCulturalQuantity = extras.getInt(Constants.TYPE_KEY_SHEEP_SURPLUS);
                mInitTime = extras.getLong(Constants.TYPE_KEY_SHEEP_INIT_TIME);
            }
        }
        mTitleList.clear();
        mFragmentList.clear();

        mTitleList.add("基本信息");
        mTitleList.add("养殖过程");
        mTitleList.add("效益分析");

        mFragmentList.add(SheepBreedHelperBaseMessage.newInstance(mBatchId, mCurrentCulturalQuantity,mInitTime,2));
        mFragmentList.add(SheepBreedHelperProcessFragment.newInstance(mBatchId, mCurrentCulturalQuantity,mInitTime));
        mFragmentList.add(SheepBreedHelperBenefitAnalysisFragment.newInstance(mBatchId));

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), 0, mContext, mFragmentList, mTitleList));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("养殖档案详情");
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
}
