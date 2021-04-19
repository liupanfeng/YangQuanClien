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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedGoodsInfoResult;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.FeedGoodsManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.BannerLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商品详情页面
 *
 * @author 86188
 */
public class FeedGoodsDetailActivity extends BaseActivity {

    private int mGoodsId;
    private View btn_feed_add_shopping_cart;
    /*商品封面图片*/
    private BannerLayout banner;
    /*商品描述图片*/
    private ImageView iv_feed_goods_desc;
    /*联系商家*/
    private View ll_feed_goods_phone_call;
    /*收藏*/
    private View ll_feed_goods_collection;
    /*收藏商品*/
    private ImageView iv_select_goods;

    private TextView tv_colect_goods;
    private View btn_feed_good_buy_now;

    private FeedGoodsInfo feedGoodsInfo;


    @Override
    protected int initRootView() {
        return R.layout.activity_feed_goods_detail;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        btn_feed_add_shopping_cart = findViewById(R.id.btn_feed_add_shopping_cart);
        iv_feed_goods_desc = findViewById(R.id.iv_feed_goods_desc);
        ll_feed_goods_phone_call = findViewById(R.id.ll_feed_goods_phone_call);
        ll_feed_goods_collection = findViewById(R.id.ll_feed_shopping_collection);
        iv_select_goods = findViewById(R.id.iv_select_goods);
        tv_colect_goods = findViewById(R.id.tv_colect_goods);
        /*立即购买*/
        btn_feed_good_buy_now = findViewById(R.id.btn_feed_good_buy_now);


        banner = findViewById(R.id.banner);
        banner.setIndicatorPosition(RelativeLayout.CENTER_HORIZONTAL);
    }

    @Override
    public void initData() {
//        Intent intent = getIntent();
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            if (extras != null) {
//            }
//        }

        feedGoodsInfo = UserManager.getInstance(mContext).getFeedGoodsInfo();
        if (feedGoodsInfo != null) {
            mGoodsId = feedGoodsInfo.getId();
            updateUI(feedGoodsInfo);
        }

        if (mGoodsId > 0) {
            getGoodsInfoFromServer();
        }
    }

    /**
     * 更新View
     *
     * @param feedGoodsInfo
     */
    private void updateUI(FeedGoodsInfo feedGoodsInfo) {
        List<String> goodsImageUrls = feedGoodsInfo.getGoodsImageUrls();
        if (feedGoodsInfo.isHasCollected()) {
            iv_select_goods.setBackgroundResource(R.mipmap.ic_feed_goods_collection_selected);
        } else {
            iv_select_goods.setBackgroundResource(R.mipmap.ic_feed_goods_collection);
        }


        final List<String> descriptionImageUrls = feedGoodsInfo.getDescriptionImageUrls();
        if (!CommonUtils.isEmpty(goodsImageUrls)) {
            banner.setViewUrls(mContext, goodsImageUrls, null);
            banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String imagePath = descriptionImageUrls.get(position);
                    Util.showBigPicture(mContext, imagePath);
                }
            });
        }

        if (!CommonUtils.isEmpty(descriptionImageUrls)) {
            String descUrl = descriptionImageUrls.get(0);
            GlideUtil.getInstance().loadUrl(descUrl, iv_feed_goods_desc);
        }

    }

    /**
     * 获取商品信息
     * 商品信息可以通过上个接口传递过来  所以这个接口这里可以不调用，但是为了测试接口这里先这样写
     */
    private void getGoodsInfoFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", mGoodsId);
        String token = getToken();
        showLoading();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_GOODS_INFO, new BaseCallBack<FeedGoodsInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, FeedGoodsInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                FeedGoodsInfo data = result.getData();
//                mAdapter.addAll(data);
//                 updateUI();
            }


            @Override
            protected void onResponse(Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("商品详情");
    }

    @Override
    public void initListener() {

        btn_feed_add_shopping_cart.setOnClickListener(this);
        ll_feed_goods_phone_call.setOnClickListener(this);
        ll_feed_goods_collection.setOnClickListener(this);
        btn_feed_good_buy_now.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().finishActivity();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_feed_add_shopping_cart) {
            //加入购物车
            addShoppingCar();
        } else if (v.getId() == R.id.ll_feed_goods_phone_call) {
            String shopPhone = feedGoodsInfo.getShopPhone();
            if (TextUtils.isEmpty(shopPhone)){
                return;
            }
            Util.callPhone(mContext, shopPhone);
        } else if (v.getId() == R.id.ll_feed_shopping_collection) {
            doCollectShoppingOrGoods(mGoodsId);
        } else if (v.getId() == R.id.btn_feed_good_buy_now) {
            List<BaseInfo> list = new ArrayList<>();
            FeedShoppingCarGoodsInfo feedShoppingCarGoodsInfo=FeedShoppingCarGoodsInfo.parseGoodsInfo(feedGoodsInfo);
            list.add(feedShoppingCarGoodsInfo);
            FeedGoodsManager.getInstance().setList(list);
            Bundle bundle=new Bundle();
            bundle.putString(Constants.TYPE_BUY_TYPE,"goods");
            AppManager.getInstance().jumpActivity(this, FeedOrderActivity.class,bundle);
        }
    }

    public void addShoppingCar() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("goodsId", mGoodsId);

        String token = getToken();
        showLoading();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_SHOPPING_CAR_ADD, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                } else {
                    ToastUtil.showToast(mContext, "已经添加到购物车请查看！");
                }
            }


            @Override
            protected void onResponse(Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }


    /**
     * 收藏商品
     */
    public void doCollectShoppingOrGoods(int goodsId) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("objectId", goodsId);
        param.put("objectType", 2);  //1是店铺 2是商品
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

                    feedGoodsInfo.setHasCollected(!feedGoodsInfo.isHasCollected());
                    boolean hasCollected = feedGoodsInfo.isHasCollected();
                    if (hasCollected) {
                        iv_select_goods.setBackgroundResource(R.mipmap.ic_feed_goods_collection_selected);
                    } else {
                        iv_select_goods.setBackgroundResource(R.mipmap.ic_feed_goods_collection);
                    }
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