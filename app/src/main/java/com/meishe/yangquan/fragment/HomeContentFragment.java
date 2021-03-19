package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.utils.Constants;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/26
 * @Description : 市场页面
 */

public class HomeContentFragment extends BaseRecyclerFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int mType;
    private int mTabType;

    public static HomeContentFragment newInstance(int type, int tabType) {
        HomeContentFragment sheepFrgment = new HomeContentFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.MARKET_TYPE, type);
        arguments.putInt(Constants.TAB_TYPE, tabType);
        sheepFrgment.setArguments(arguments);
        return sheepFrgment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.MARKET_TYPE);
            mTabType = arguments.getInt(Constants.TAB_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_market_sheep_content, container, false);
        mSlidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        mViewPager = view.findViewById(R.id.vp_pager);
        return view;
    }


    @Override
    protected void initData() {
        initTabLayout();
    }

    private void initTabLayout() {
        mFragmentList.clear();
        mTitleList.clear();
        HomeListFragment homeMarketNewestFragment= HomeListFragment.newInstance(mType,Constants.TYPE_LIST_TYPE_NEWEST,mTabType);
        mFragmentList.add(homeMarketNewestFragment);

        HomeListFragment homeMarketComment= HomeListFragment.newInstance(mType,Constants.TYPE_LIST_TYPE_RECOMMEND,mTabType);
        mFragmentList.add(homeMarketComment);

        mTitleList.add("最新");
        mTitleList.add("推荐");

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(),mFragmentList,mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

}
