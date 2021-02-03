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
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 羊管家-养殖助手
 * @date 2020/12/15 14:13
 */
public class SheepBreedHelperFragment extends BaseRecyclerFragment {


    /*批次id*/
    private int mBatchId;
    /*剩余出栏羊*/
    private int mCurrentCulturalQuantity;
    private TextView mTvTitle;
    private TabLayout mTabLayout;
    private MViewPager mViewPager;

    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private long mInitTime;


    public static SheepBreedHelperFragment newInstance(int id, int currentCulturalQuantity,long initTime) {
        SheepBreedHelperFragment fragment = new SheepBreedHelperFragment();
        Bundle args = new Bundle();
        //使用bundle 进行数据传递
        args.putInt(Constants.TYPE_KEY_BATCH_ID, id);
        args.putInt(Constants.TYPE_KEY_SHEEP_SURPLUS, currentCulturalQuantity);
        args.putLong(Constants.TYPE_KEY_SHEEP_INIT_TIME, initTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate方法中获取参数
        if (getArguments() != null) {
            mBatchId = getArguments().getInt(Constants.TYPE_KEY_BATCH_ID);
            mCurrentCulturalQuantity = getArguments().getInt(Constants.TYPE_KEY_SHEEP_SURPLUS);
            mInitTime = getArguments().getLong(Constants.TYPE_KEY_SHEEP_INIT_TIME);
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

        mFragmentList.add(SheepBreedHelperBaseMessage.newInstance(mBatchId, mCurrentCulturalQuantity,mInitTime));
        mFragmentList.add(SheepBreedHelperProcessFragment.newInstance(mBatchId, mCurrentCulturalQuantity,mInitTime));
        mFragmentList.add(new SheepBreedHelperBenefitAnalysisFragment());

        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), 0, mContext, mFragmentList, mTitleList));
        mTabLayout.setupWithViewPager(mViewPager);


    }
}
