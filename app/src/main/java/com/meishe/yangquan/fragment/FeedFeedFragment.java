package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.utils.Constants;

/**
 * @author liupanfeng
 * @desc 饲料-饲料
 * @date 2020/12/21 14:36
 * 这个页面暂时不用
 */
@Deprecated
public class FeedFeedFragment extends BaseRecyclerFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_feed, container, false);
        mSlidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        mViewPager = view.findViewById(R.id.vp_pager);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mFragmentList.clear();
        mTitleList.clear();

        FeedContentListFragment feedContentListFragment = FeedContentListFragment.newInstance(Constants.TYPE_FEED_CONCENTRATE);
        mFragmentList.add(feedContentListFragment);

        feedContentListFragment = FeedContentListFragment.newInstance(Constants.TYPE_FEED_FORAGE);
        mFragmentList.add(feedContentListFragment);

        feedContentListFragment = FeedContentListFragment.newInstance(Constants.TYPE_FEED_MEAL);
        mFragmentList.add(feedContentListFragment);

        feedContentListFragment = FeedContentListFragment.newInstance(Constants.TYPE_FEED_MASHEN);
        mFragmentList.add(feedContentListFragment);

        mTitleList.add("精料");
        mTitleList.add("草料");
        mTitleList.add("豆粕");
        mTitleList.add("麻申");

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }
}
