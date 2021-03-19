package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedGoodsInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.view.BannerLayout;

import java.io.IOException;
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
    private RequestOptions options;

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
        banner = findViewById(R.id.banner);
        banner.setIndicatorPosition(RelativeLayout.CENTER_HORIZONTAL);
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
                FeedGoodsInfo feedGoodsInfo = (FeedGoodsInfo) extras.getSerializable(Constants.FEED_GOODS_INFO);
                if (feedGoodsInfo != null) {
                    mGoodsId = feedGoodsInfo.getId();
                    updateUI(feedGoodsInfo);
                }
            }
        }

        if (mGoodsId > 0) {
            getGoodsInfoFromServer();
        }
    }

    /**
     * 更新View
     * @param feedGoodsInfo
     */
    private void updateUI(FeedGoodsInfo feedGoodsInfo) {
        List<String> goodsImageUrls = feedGoodsInfo.getGoodsImageUrls();
        List<String> descriptionImageUrls = feedGoodsInfo.getDescriptionImageUrls();
        if (!CommonUtils.isEmpty(goodsImageUrls)){
            banner.setViewUrls(mContext, goodsImageUrls, null);
        }

        if (!CommonUtils.isEmpty(descriptionImageUrls)){
            String descUrl = descriptionImageUrls.get(0);
            Glide.with(mContext)
                    .asBitmap()
                    .load(descUrl)
                    .apply(options)
                    .into(iv_feed_goods_desc);
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
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_feed_add_shopping_cart){
            //加入购物车
            addShoppingCar();
        }
    }

    public void addShoppingCar(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("goodsId",mGoodsId);

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
                }else{
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
}