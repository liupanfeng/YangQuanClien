package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceMessageResult;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.SheepNewsResult;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.view.AutoPollRecyclerView;
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

    private TabLayout mTabLayout;
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
        mTabLayout = view.findViewById(R.id.tab_layout);
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
         ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),0,mContext,mFragmentList,mTopTabTitleList);
         mViewPager.setAdapter(viewPagerAdapter);
         mTabLayout.setupWithViewPager(mViewPager);

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


    /**
     * 头部系统通知
     */
    private void initTopNotifyRecyclerView() {
//        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mServiceMessageAdapter=new MultiFunctionAdapter(mContext,mRecyclerView);
//        mServiceMessageAdapter.setPageType(1);
//        mRecyclerView.setAdapter(mServiceMessageAdapter);
    }

    private void initServiceNewsRecyclerView() {
//        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
//        mServiceNewsRecycler.setLayoutManager(layoutManager);
//        mSheepNewsAdapter=new MultiFunctionAdapter(mContext,mServiceNewsRecycler);
//        mServiceNewsRecycler.setAdapter(mSheepNewsAdapter);
//        int index=12;
//        mList.clear();
//        for (int i = 0; i <index ; i++) {
//            SheepNews sheepNews=new SheepNews();
//            sheepNews.setSheepName("东北羊");
//            sheepNews.setSheepPrice("30.5");
//            mList.add(sheepNews);
//        }
//        mList.add(new EndInfo());

    }


    /**
     * 服务类型
     */
    private void initServiceTypeRecyclerView() {
//        GridLayoutManager layoutManager=new GridLayoutManager(mContext, 5);
//        mServiceTypeRecycler.setLayoutManager(layoutManager);
//        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mServiceTypeRecycler);
//        mServiceTypeRecycler.setAdapter(adapter);
//        adapter.setFragment(this);
//        mList.clear();
//        for (int i = 0; i < UserType.getServiceTypeName().size(); i++) {
//            ServiceTypeInfo typeInfo=new ServiceTypeInfo();
//            typeInfo.setName(UserType.getServiceTypeName().get(i));
//            typeInfo.setIcon(UserType.getUserTypeIcon().get(i));
//            typeInfo.setType(UserType.getUserType().get(i));
//            mList.add(typeInfo);
//        }
//        adapter.addAll(mList);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mRecyclerView.stop();
//        mRecyclerView=null;
    }

    @Override
    public void onPause() {
        super.onPause();
//        mRecyclerView.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mRecyclerView!=null){
//            mRecyclerView.start();
//        }
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
