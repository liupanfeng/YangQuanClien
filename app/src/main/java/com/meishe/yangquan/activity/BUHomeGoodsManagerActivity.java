package com.meishe.yangquan.activity;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.fragment.BUHomeGoodsFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;

/**
 * 商品管理
 */
public class BUHomeGoodsManagerActivity extends BaseActivity {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private View btn_bu_goods_publish;

    @Override
    protected int initRootView() {
        return R.layout.activity_bu_home_goods_manager;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);
        btn_bu_goods_publish = findViewById(R.id.btn_bu_goods_publish);

    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("已发布");
        mTitleList.add("待发布");
        mFragmentList.add(BUHomeGoodsFragment.onInstance(Constants.TYPE_ALREADY_PUBLISH_GOODS_TYPE));
        mFragmentList.add(BUHomeGoodsFragment.onInstance(Constants.TYPE_KEY_WAIT_PUBLISH_GOODS_TYPE));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("商品管理");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_bu_goods_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishGoods();
            }
        });

    }

    /**
     * 发布商品
     */
    private void publishGoods() {
        AppManager.getInstance().jumpActivity(mContext,BUPublishGoodsActivity.class);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }


}