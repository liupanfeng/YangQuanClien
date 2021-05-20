package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import com.bumptech.glide.request.RequestOptions;
import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.BannerLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private RequestOptions options;
    private BannerLayout banner;
    private View iv_back;
    private View ll_feed_goods_order;
    /*收藏*/
    private View ll_feed_shopping_collection;
    /*联系商家*/
    private View ll_feed_goods_phone_call;

    private ImageView iv_select_shopping;
    private TextView tv_colect_shopping;

    private FeedShoppingInfo mFeedInfo;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_shopping_detail;
    }

    @Override
    public void initView() {
        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);
        tv_feed_shopping_name = findViewById(R.id.tv_feed_shopping_name);
        tv_feed_shopping_fans = findViewById(R.id.tv_feed_shopping_fans);
        tv_feed_shopping_sign_name = findViewById(R.id.tv_feed_shopping_sign_name);
        iv_back = findViewById(R.id.iv_back);
        /*底部按钮菜单*/
        ll_feed_goods_order = findViewById(R.id.ll_feed_goods_order);
        ll_feed_shopping_collection = findViewById(R.id.ll_feed_shopping_collection);
        ll_feed_goods_phone_call = findViewById(R.id.ll_feed_goods_phone_call);
        ll_feed_goods_shopping_car = findViewById(R.id.ll_feed_goods_shopping_car);

        /*收藏*/
        iv_select_shopping = findViewById(R.id.iv_select_shopping);
        tv_colect_shopping = findViewById(R.id.tv_colect_shopping);


        banner = findViewById(R.id.banner);
        banner.setIndicatorPosition(RelativeLayout.CENTER_HORIZONTAL);
    }

    @Override
    public void initData() {

        mFeedInfo =  UserManager.getInstance(mContext).getFeedShoppingInfo();
        if (mFeedInfo != null) {
            mShoppingId = mFeedInfo.getId();
            updateUI(mFeedInfo);
        }

        mFragmentList.clear();
        mTitleList.clear();

//        FeedGoodsListFragment feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_RECOMMEND);
//        mFragmentList.add(feedFoodsListFragment);

//        FeedGoodsListFragment feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_MULTIPLE);
//        mFragmentList.add(feedFoodsListFragment);
//
//        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_SALES);
//        mFragmentList.add(feedFoodsListFragment);
//
//        feedFoodsListFragment = FeedGoodsListFragment.newInstance(mShoppingId,Constants.TYPE_FEED_FOODS_PRICE);
//        mFragmentList.add(feedFoodsListFragment);

        CommonListFragment feedFoodsListFragment = CommonListFragment.newInstance(true, Constants.TYPE_COMMON_SHOPPING,
                Constants.TYPE_FEED_FOODS_MULTIPLE, mShoppingId);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = CommonListFragment.newInstance(true, Constants.TYPE_COMMON_SHOPPING,
                Constants.TYPE_FEED_FOODS_SALES, mShoppingId);
        mFragmentList.add(feedFoodsListFragment);

        feedFoodsListFragment = CommonListFragment.newInstance(true, Constants.TYPE_COMMON_SHOPPING,
                Constants.TYPE_FEED_FOODS_PRICE, mShoppingId);
        mFragmentList.add(feedFoodsListFragment);

//        mTitleList.add("推荐");
        mTitleList.add("综合");
        mTitleList.add("销量");
        mTitleList.add("价格");

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    /**
     * 更新UI
     */
    private void updateUI(FeedShoppingInfo feedInfo) {
        tv_feed_shopping_name.setText(feedInfo.getName());
        tv_feed_shopping_fans.setText("粉丝："+feedInfo.getFansCount());

        if (feedInfo.isHasCollected()) {
            iv_select_shopping.setBackgroundResource(R.mipmap.ic_feed_goods_collection_selected);
        } else {
            iv_select_shopping.setBackgroundResource(R.mipmap.ic_feed_goods_collection);
        }


        tv_feed_shopping_sign_name.setText((feedInfo.getSign() == null || feedInfo.getSign().equals("null")) ? "暂无签名" : feedInfo.getSign());
        final List<String> shopInSideImageUrls = feedInfo.getShopInSideImageUrls();
        if (!CommonUtils.isEmpty(shopInSideImageUrls)) {
            banner.setViewUrls(mContext, shopInSideImageUrls, null);
            banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String imagePath = shopInSideImageUrls.get(position);
                    Util.showBigPicture(mContext, imagePath);
                }
            });
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initListener() {
        ll_feed_goods_shopping_car.setOnClickListener(this);
        ll_feed_goods_order.setOnClickListener(this);
        ll_feed_shopping_collection.setOnClickListener(this);
        ll_feed_goods_phone_call.setOnClickListener(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().finishActivity();
            }
        });

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_feed_goods_shopping_car) {
            AppManager.getInstance().jumpActivity(FeedShoppingDetailActivity.this, FeedShoppingCarActivity.class);
        } else if (v.getId() == R.id.ll_feed_goods_order) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.KEY_ORDER_STATE_TYPE, Constants.TYPE_COMMON_MY_ORDER_TYPE);
            bundle.putInt(Constants.KEY_TAB_SELECT_INDEX, 0);
            AppManager.getInstance().jumpActivity(mContext, MineOrderActivity.class, bundle);
        } else if (v.getId() == R.id.ll_feed_shopping_collection) {
            doCollectShoppingOrGoods(mShoppingId);
        } else if (v.getId() == R.id.ll_feed_goods_phone_call) {
            String shopPhone = mFeedInfo.getShopPhone();
            if (TextUtils.isEmpty(shopPhone)){
                return;
            }
            Util.callPhone(mContext,shopPhone);
        }
    }

    /**
     * 收藏店铺
     */
    public void doCollectShoppingOrGoods(int shoppingId) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("objectId", shoppingId);
        param.put("objectType", 1);  //1是店铺 2是商品
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_COLLECTION, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    mFeedInfo.setHasCollected(!mFeedInfo.isHasCollected());
                    if (mFeedInfo.isHasCollected()) {
                        iv_select_shopping.setBackgroundResource(R.mipmap.ic_feed_goods_collection_selected);
                    } else {
                        iv_select_shopping.setBackgroundResource(R.mipmap.ic_feed_goods_collection);
                    }
//                    int type = mFeedInfo.getType();
//                    if (type == 1) {
//                        EventBus.getDefault().post(new MessageEvent().setEventType(Constants.TYPE_COMMON_FEED_FEED).setListType(type));
//                    } else if (type == 2) {
//                        EventBus.getDefault().post(new MessageEvent().setEventType(Constants.TYPE_COMMON_FEED_CORN).setListType(type));
//                    } else if (type == 3) {
//                        EventBus.getDefault().post(new MessageEvent().setEventType(Constants.TYPE_COMMON_FEED_TOOLS).setListType(type));
//                    }

                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


}