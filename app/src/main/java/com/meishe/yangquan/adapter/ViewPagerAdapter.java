package com.meishe.yangquan.adapter;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;
    private List<String> list_Title;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context, List<Fragment> fragmentList, List<String> list_Title) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.isEmpty() ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.isEmpty() ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.isEmpty()?"error":list_Title.get(position);
    }
}
