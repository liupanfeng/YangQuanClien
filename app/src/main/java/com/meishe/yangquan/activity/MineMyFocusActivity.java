package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.fragment.HomeListFragment;
import com.meishe.yangquan.fragment.MineMyFansFragment;
import com.meishe.yangquan.fragment.MineMyFocusFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-我的关注
 * @author 86188
 */
public class MineMyFocusActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private List<Fragment> mFragmentLists;
    private List<String> mTitleList;
    @Override
    protected int initRootView() {
        return R.layout.activity_mine_my_focus;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager =  findViewById(R.id.vp_pager);
    }

    @Override
    public void initData() {
        initTabLayout();
    }

    private void initTabLayout() {
        mFragmentLists=new ArrayList<>();
        mTitleList=new ArrayList<>();

//        MineMyFocusFragment mineMyFocusFragment=new MineMyFocusFragment();
//        MineMyFansFragment mineMyFansFragment=new MineMyFansFragment();
        CommonListFragment mineMyFocusFragment=CommonListFragment.
                newInstance(true,Constants.TYPE_COMMON_MINE_MY_FOCUS,0);
        CommonListFragment mineMyFansFragment=CommonListFragment.
                newInstance(true,Constants.TYPE_COMMON_MINE_MY_FANS,0);

        mFragmentLists.add(mineMyFocusFragment);
        mFragmentLists.add(mineMyFansFragment);

        mTitleList.add("我的关注");
        mTitleList.add("粉丝");

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(),mFragmentLists,mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的关注");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }

}
