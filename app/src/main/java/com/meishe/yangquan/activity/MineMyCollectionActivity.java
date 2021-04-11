package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.fragment.MineCollectionFragment;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.MViewPager;

import java.util.ArrayList;

/**
 * @author 86188
 * 我的-我的收藏
 */
public class MineMyCollectionActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;


    private SlidingTabLayout mSlidingTabLayout;
    private MViewPager mViewPager;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_my_clollection;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);

    }

    @Override
    public void initData() {

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("店铺");
        mTitleList.add("商品");
//        mFragmentList.add(MineCollectionFragment.onInstance(Constants.TYPE_MINE_COLLECT_SHOPPING));
//        mFragmentList.add(MineCollectionFragment.onInstance(Constants.TYPE_MINE_COLLECT_GOODS));
        mFragmentList.add(CommonListFragment.newInstance(true,Constants.TYPE_COMMON_MINE_COLLECT_SHOPPING,1));
        mFragmentList.add(CommonListFragment.newInstance(true,Constants.TYPE_COMMON_MINE_COLLECT_GOODS,2));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setScroll(false);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的收藏");
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
    public void onClick(View v) {

    }

}