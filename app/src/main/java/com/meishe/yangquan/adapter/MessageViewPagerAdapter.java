package com.meishe.yangquan.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MessageViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;

    public MessageViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context, List<Fragment> fragmentList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
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

}
