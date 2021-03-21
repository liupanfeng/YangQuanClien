package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceMessageResult;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.SheepNewsResult;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.view.MViewPager;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * @author 86188
 */
public class HomeFragment extends BaseRecyclerFragment implements OnResponseListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private String mParam1;
    private String mParam2;
    private static final int SERVICE_FLOW_MESSAGE = 1;
    private static final int SERVICE_SHEEP_NEWS = 2;
    private MultiFunctionAdapter mServiceMessageAdapter;
    private MultiFunctionAdapter mSheepNewsAdapter;
    private MaterialProgress mp_loading;

    private SlidingTabLayout mSlidingTabLayout;
    private MViewPager mViewPager;

    private int[]  mTopTabContent={R.string.top_tab_quotation,R.string.top_tab_market,R.string.top_tab_service,
            R.string.top_tab_industry_information};

    private List<Fragment> mFragmentList=new ArrayList<>();
    private List<String> mTopTabTitleList=new ArrayList<>();

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
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getChildFragmentManager(), mFragmentList, mTopTabTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

         getUserDataFromServer();
    }

    /**
     * 获取用户数据
     */
    private void getUserDataFromServer() {

    }


    private void initTabTitle() {
        mTopTabTitleList.clear();
        for (int title:mTopTabContent){
            mTopTabTitleList.add(mContext.getString(title));
        }
    }

    private void initTabFragment() {
        mFragmentList.clear();
        HomeQuotationFragment homeQuotationFragment=new HomeQuotationFragment();
        mFragmentList.add(homeQuotationFragment);
        HomeMarketFragment homeMarketFragment=new HomeMarketFragment();
        mFragmentList.add(homeMarketFragment);
        HomeServiceFragment homeServiceFragment=new HomeServiceFragment();
        mFragmentList.add(homeServiceFragment);
        HomeIndustryInformation homeIndustryInformation=new HomeIndustryInformation();
        mFragmentList.add(homeIndustryInformation);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSuccess(Object object) {
        if (mp_loading != null) {
            mp_loading.hide();
        }
    }

    @Override
    public void onSuccess(int type, Object object) {
        if (mp_loading != null) {
            mp_loading.hide();
        }

        switch (type) {
            case SERVICE_FLOW_MESSAGE:
                if (object instanceof ServiceMessageResult) {
                    List<ServiceMessage> messageList = ((ServiceMessageResult) object).getData();
                    if (messageList != null && messageList.size() > 0) {
                        mServiceMessageAdapter.setNeedAutoScroll(true);
                        mServiceMessageAdapter.addAll(messageList);
//                        mRecyclerView.start();
                    }
                }
                break;
            case SERVICE_SHEEP_NEWS:
                if (object instanceof SheepNewsResult) {
                    List<SheepNews> sheepNews = ((SheepNewsResult) object).getData();
                    if (sheepNews != null && sheepNews.size() > 0) {
                        sheepNews.get(0).setNeedShowLabel(true);
                        mSheepNewsAdapter.addAll(sheepNews);
                    }
                }

                break;
        }
    }

    @Override
    public void onError(Object obj) {
        if (mp_loading != null) {
            mp_loading.hide();
        }
    }

    @Override
    public void onError(int type, Object obj) {
        if (mp_loading != null) {
            mp_loading.hide();
        }
    }
}
