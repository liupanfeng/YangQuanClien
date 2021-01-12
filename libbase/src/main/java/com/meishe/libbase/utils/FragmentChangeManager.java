package com.meishe.libbase.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * The type Fragment change manager.
 *  fragment改变管理器
 */
public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /*
    * Fragment切换数组
    * Fragment toggle array
    * */
    private List<Fragment> mFragments;
    /*
    * 当前选中的Tab
    * The currently selected Tab
    * */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, List<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments();
    }

    /*
    * 初始化fragments
    * init fragments
    * */
    private void initFragments() {
        for (Fragment fragment : mFragments) {
            mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commitAllowingStateLoss();
        }

        setFragments(0);
    }

    /**
     * 界面切换控制  @param index the index
     * Interface switching control
     */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
        mCurrentTab = index;
    }

    /**
     * Gets current tab.
     * 得到当前标签
     * @return the current tab 当前标签
     */
    public int getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * Gets current fragment.
     * 得到当前fragment
     * @return the current fragment 当前fragment
     */
    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }
}