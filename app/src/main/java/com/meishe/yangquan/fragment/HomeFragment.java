package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author 86188
 */
public class HomeFragment extends BaseRecyclerFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MultiFunctionAdapter mServiceMessageAdapter;
    private MultiFunctionAdapter mSheepNewsAdapter;
    private MaterialProgress mp_loading;

    private SlidingTabLayout mSlidingTabLayout;
    private MViewPager mViewPager;

    private int[] mTopTabContent = {R.string.top_tab_quotation, R.string.top_tab_market, R.string.top_tab_service,
            R.string.top_tab_industry_information};

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTopTabTitleList = new ArrayList<>();

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSlidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        mViewPager = view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    protected void initListener() {
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    @Override
    protected void initData() {

        initTabTitle();
        initTabFragment();
        //这里注意 如果是嵌套在 fragment里边 一定要使用getChildFragmentManager 这个方法获取FragmentManager 否则会出错
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(), mFragmentList, mTopTabTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setScroll(false);
        getVersionData();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (mViewPager != null) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem());
        }
    }

    private void initTabTitle() {
        mTopTabTitleList.clear();
        for (int title : mTopTabContent) {
            mTopTabTitleList.add(mContext.getString(title));
        }
    }

    private void initTabFragment() {
        mFragmentList.clear();
        HomeQuotationFragment homeQuotationFragment = new HomeQuotationFragment();
        mFragmentList.add(homeQuotationFragment);
        HomeMarketFragment homeMarketFragment = new HomeMarketFragment();
        mFragmentList.add(homeMarketFragment);
        HomeServiceFragment homeServiceFragment = new HomeServiceFragment();
        mFragmentList.add(homeServiceFragment);
        HomeIndustryInformation homeIndustryInformation = new HomeIndustryInformation();
        mFragmentList.add(homeIndustryInformation);
    }


    /**
     * 获取版本信息
     */
    private void getVersionData() {
//        UpdateVersionUtil.checkVersion(App.getContext(), new UpdateVersionUtil.UpdateListener() {
//            @Override
//            public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
//                if(updateStatus != UpdateStatus.NO){
//                    if(mContext == null){
//                        return;
//                    }
//                    UpdateVersionUtil.showDialog(mContext, versionInfo);
//                }
//            }
//        });
    }

}
