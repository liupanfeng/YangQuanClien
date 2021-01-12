package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewPagerAdapter;
import com.meishe.yangquan.fragment.MineMyFansFragment;
import com.meishe.yangquan.fragment.MineMyFocusFragment;
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
    private MViewPager mViewPager;
    private TabLayout mTabLayout;
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
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    public void initData() {
        initTabLayout();

    }

    private void initTabLayout() {
        mFragmentLists=new ArrayList<>();
        mTitleList=new ArrayList<>();

        TabLayout.Tab tabFocus=mTabLayout.newTab();
        tabFocus.setText("我的关注");
        mTabLayout.addTab(tabFocus);

        TabLayout.Tab tabFans=mTabLayout.newTab();
        tabFans.setText("粉丝");
        mTabLayout.addTab(tabFans);

        mTitleList.add("我的关注");
        mTitleList.add("粉丝");

        MineMyFocusFragment mineMyFocusFragment=new MineMyFocusFragment();
        MineMyFansFragment mineMyFansFragment=new MineMyFansFragment();
        mFragmentLists.add(mineMyFocusFragment);
        mFragmentLists.add(mineMyFansFragment);

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),0,mContext,mFragmentLists,mTitleList));
        mTabLayout.setupWithViewPager(mViewPager);

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

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {

    }

    @Override
    public void onError(int type, Object obj) {

    }
}
