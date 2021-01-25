package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手
 * @date 2020/12/15 14:13
 */
public class SheepBreedHelperFragment extends BaseRecyclerFragment {

    private static final String TYPE_KEY_BATCH_ID = "key_title";
    private int mId;
    private TextView mTvTitle;
    private TabLayout mTabLayout;
    private MViewPager mViewPager;

    private List<String> mTitleList=new ArrayList<>();
    private List<Fragment> mFragmentList=new ArrayList<>();


    public static SheepBreedHelperFragment newInstance(int id) {
        SheepBreedHelperFragment fragment = new SheepBreedHelperFragment();
        Bundle args = new Bundle();
        //使用bundle 进行数据传递
        args.putInt(TYPE_KEY_BATCH_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate方法中获取参数
        if (getArguments() != null) {
            mId = getArguments().getInt(TYPE_KEY_BATCH_ID);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.sheep_breed_helper_fragment, container, false);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initTabLayout();
    }

    private void initTabLayout() {
        mTitleList.clear();
        mFragmentList.clear();

        mTitleList.add("基本信息");
        mTitleList.add("养殖过程");
        mTitleList.add("效益分析");

        mFragmentList.add(SheepBreedHelperBaseMessage.newInstance(mId));
        mFragmentList.add(SheepBreedHelperProcessFragment.newInstance(mId));
        mFragmentList.add(new SheepBreedHelperBenefitAnalysisFragment());

        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),0,mContext,mFragmentList,mTitleList));
        mTabLayout.setupWithViewPager(mViewPager);


    }
}
