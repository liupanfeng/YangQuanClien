package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版消息页面
 */
public class BUMessageFragment extends BaseRecyclerFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_message, container, false);
        mSlidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        mViewPager = view.findViewById(R.id.vp_pager);
        return view;
    }

    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();


        BUMessageListFragment buMessageListFragment = BUMessageListFragment.newInstance(Constants.BU_TYPE_MESSAGE_ORDER);
        mFragmentList.add(buMessageListFragment);

        buMessageListFragment = BUMessageListFragment.newInstance(Constants.BU_TYPE_MESSAGE_COMMENT);
        mFragmentList.add(buMessageListFragment);

        buMessageListFragment = BUMessageListFragment.newInstance(Constants.BU_TYPE_MESSAGE_REFUND);
        mFragmentList.add(buMessageListFragment);

        buMessageListFragment = BUMessageListFragment.newInstance(Constants.BU_TYPE_MESSAGE_OTHER);
        mFragmentList.add(buMessageListFragment);


        mTitleList.add("订单消息");
        mTitleList.add("评价消息");
        mTitleList.add("退款消息");
        mTitleList.add("其他消息");

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void initListener() {

    }
}
