package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.fragment.FeedGoodsListFragment;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 饲料-店铺详情页面
 *
 * @author 86188
 */
public class FeedShoppingDetailActivity extends BaseActivity {

    protected List<Fragment> mFragmentList = new ArrayList<>();
    protected List<String> mTitleList = new ArrayList<>();

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int mShoppingId;
    private View ll_feed_goods_shopping_car;
    /*店铺名称*/
    private TextView tv_feed_shopping_name;
    /*粉丝*/
    private TextView tv_feed_shopping_fans;
    /*店铺签名*/
    private TextView tv_feed_shopping_sign_name;
    private ImageView iv_shopping_cover;
    private RequestOptions options;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_shopping_detail;
    }

    @Override
    public void initView() {
        mSlidingTabLayout =  findViewById(R.id.slidingTabLayout);
        mViewPager =  findViewById(R.id.vp_pager);
        ll_feed_goods_shopping_car =  findViewById(R.id.ll_feed_goods_shopping_car);
        tv_feed_shopping_name =  findViewById(R.id.tv_feed_shopping_name);
        tv_feed_shopping_fans =  findViewById(R.id.tv_feed_shopping_fans);
        tv_feed_shopping_sign_name =  findViewById(R.id.tv_feed_shopping_sign_name);
        iv_shopping_cover =  findViewById(R.id.iv_shopping_cover);
    }

    @Override
    public void initData() {
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.ic_message_list_photo_default);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                FeedShoppingInfo feedInfo = (FeedShoppingInfo) extras.getSerializable(Constants.FEED_SHOPPING_INFO);
                if (feedInfo != null) {
                    mShoppingId=feedInfo.getId();
                    updateUI(feedInfo);
                }
            }
        }


        mFragmentList.clear();
        mTitleList.clear();

        FeedGoodsListFragment feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_RECOMMEND);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_MULTIPLE);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_SALES);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_PRICE);
        mFragmentList.add(feedFoodsListFragment);

        mTitleList.add("推荐");
        mTitleList.add("综合");
        mTitleList.add("销量");
        mTitleList.add("价格");

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    /**
     * 更新UI
     */
    private void updateUI(FeedShoppingInfo feedInfo) {
        tv_feed_shopping_name.setText(feedInfo.getName());
//        tv_feed_shopping_fans.setText(feedInfo.get);
        tv_feed_shopping_sign_name.setText((feedInfo.getSign()==null||feedInfo.getSign().equals("null"))?"暂无签名":feedInfo.getSign());
        List<String> shopInSideImageUrls = feedInfo.getShopInSideImageUrls();
        String coverUrl=null;
        if (!CommonUtils.isEmpty(shopInSideImageUrls)){
             coverUrl = shopInSideImageUrls.get(0);
        }

        if (coverUrl != null ) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(coverUrl)
                    .apply(options)
                    .into(iv_shopping_cover);
        } else {
            Glide.with(mContext)
                    .asBitmap()
                    .load("")
                    .apply(options)
                    .into(iv_shopping_cover);
        }


    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        ll_feed_goods_shopping_car.setOnClickListener(this);
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_feed_goods_shopping_car){
            AppManager.getInstance().jumpActivity(FeedShoppingDetailActivity.this,FeedShoppingCarActivity.class);
        }
    }
}