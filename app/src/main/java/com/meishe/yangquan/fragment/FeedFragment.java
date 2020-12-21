package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 饲料界面
 * @date 2020/12/21 14:08
 */
public class FeedFragment extends BaseRecyclerFragment {

    private TabLayout mTabLayout;
    private MViewPager mViewPager;

    private List<String> mTabContents = new ArrayList<>();
    //    mTopTabContent={"饲料","玉米","工具"};
    private List<Fragment> mFragmentList = new ArrayList<>();

    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mFragmentList.clear();
        mTabContents.clear();

        FeedFeedFragment feedFeedFragment = new FeedFeedFragment();
        mFragmentList.add(feedFeedFragment);
        mTabContents.add("饲料");

        FeedCornFragment feedCornFragment = new FeedCornFragment();
        mFragmentList.add(feedCornFragment);
        mTabContents.add("玉米");

        FeedToolsFragment feedToolsFragment = new FeedToolsFragment();
        mFragmentList.add(feedToolsFragment);
        mTabContents.add("工具");

        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), 0, mContext, mFragmentList, mTabContents));
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
