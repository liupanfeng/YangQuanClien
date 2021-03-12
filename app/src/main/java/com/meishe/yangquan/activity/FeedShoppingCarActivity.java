package com.meishe.yangquan.activity;


import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedGoodsInfoListResult;
import com.meishe.yangquan.bean.FeedShoppingCarInfo;
import com.meishe.yangquan.bean.FeedShoppingCarInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 饲料-购物车页面
 */
public class FeedShoppingCarActivity extends BaseActivity {

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_shopping_car;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView=findViewById(R.id.recycler);
        initRecyclerView();
    }

    @Override
    public void initData() {
        getShoppingCarData();
    }

    /**
     * 获取购物车数据
     */
    private void getShoppingCarData() {
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();
        showLoading();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_SHOPPING_CAR_LIST, new BaseCallBack<FeedShoppingCarInfoResult>() {
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
            protected void onSuccess(Call call, Response response, FeedShoppingCarInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<FeedShoppingCarInfo> data = result.getData();
                mAdapter.addAll(data);
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
        mTvTitle.setText("购物车");
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
