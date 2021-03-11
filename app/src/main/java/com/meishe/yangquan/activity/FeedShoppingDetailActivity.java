package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.fragment.FeedGoodsListFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 饲料-店铺详情页面
 *
 * @author 86188
 */
public class FeedShoppingDetailActivity extends BaseActivity {

    protected List<Fragment> mFragmentList = new ArrayList<>();
    protected List<String> mTitleList = new ArrayList<>();

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int mShoppingId;
    private View ll_feed_goods_shopping_car;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_shopping_detail;
    }

    @Override
    public void initView() {
        mSlidingTabLayout =  findViewById(R.id.slidingTabLayout);
        mViewPager =  findViewById(R.id.vp_pager);
        ll_feed_goods_shopping_car =  findViewById(R.id.ll_feed_goods_shopping_car);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                FeedShoppingInfo feedInfo = (FeedShoppingInfo) extras.getSerializable(Constants.FEED_SHOPPING_INFO);
                if (feedInfo != null) {
                    mShoppingId=feedInfo.getId();
                }
            }
        }


        mFragmentList.clear();
        mTitleList.clear();

        FeedGoodsListFragment feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_RECOMMEND);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_MULTIPLE);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_SALES);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_PRICE);
        mFragmentList.add(feedFoodsListFragment);

        mTitleList.add("推荐");
        mTitleList.add("综合");
        mTitleList.add("销量");
        mTitleList.add("价格");

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        ll_feed_goods_shopping_car.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_feed_goods_shopping_car){
            AppManager.getInstance().jumpActivity(FeedShoppingDetailActivity.this,FeedShoppingCarActivity.class);
        }
    }
}