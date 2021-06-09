package com.meishe.yangquan.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * date   : 2020/10/20
 * desc   :共用的FragmentPagerAdapter
 * Common FragmentPagerAdapter
 */
public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTabTitles;

    public CommonFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentList = list;
    }

    public CommonFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> tabTitles) {
        super(fm);
        mFragmentList = list;
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTabTitles == null){
            return null;
        }
        return mTabTitles.get(position);
    }
}
