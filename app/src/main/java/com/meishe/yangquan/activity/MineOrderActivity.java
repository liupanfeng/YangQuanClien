package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.MineOrderInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的-订单页面
 */
public class MineOrderActivity extends BaseActivity {

    /**
     * 订单类型
     */
    private int mType;


    @Override
    protected int initRootView() {
        return R.layout.activity_mine_order;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recycler);
        initRecyclerView();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if (extras!=null){
                mType = extras.getInt(Constants.KEY_ORDER_STATE_TYPE);
            }
        }

        getOrderData();
    }

    /**
     * 获取订单数据
     */
    private void getOrderData() {
//        List<MineOrderInfo> lists=new ArrayList<>();
//        MineOrderInfo mineOrderInfo = new MineOrderInfo();
//        mineOrderInfo.setType(mType);
//        lists.add(mineOrderInfo);
//
//        mineOrderInfo = new MineOrderInfo();
//        mineOrderInfo.setType(mType);
//        lists.add(mineOrderInfo);
//
//        mineOrderInfo = new MineOrderInfo();
//        mineOrderInfo.setType(mType);
//        lists.add(mineOrderInfo);
//
//        mineOrderInfo = new MineOrderInfo();
//        mineOrderInfo.setType(mType);
//        lists.add(mineOrderInfo);
//
//        mineOrderInfo = new MineOrderInfo();
//        mineOrderInfo.setType(mType);
//        lists.add(mineOrderInfo);
//
//        mAdapter.addAll(lists);

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("listType",0);
        requestParam.put("pageNum",1);
        requestParam.put("pageSize",30);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_FEED_ORDER_LIST, new BaseCallBack<MineOrderInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MineOrderInfoResult result) {
                if (result != null && result.getCode() == 1) {

                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);

    }


    @Override
    public void initTitle() {
        switch (mType){
            case Constants.TYPE_ORDER_WAIT_PAY_TYPE:
                mTvTitle.setText("待付款");
                break;
            case Constants.TYPE_ORDER_WAIT_SEND_TYPE:
                mTvTitle.setText("待发货");
                break;
            case Constants.TYPE_ORDER_ALREADY_SEND_TYPE:
                mTvTitle.setText("待评价");
                break;
            case Constants.TYPE_ORDER_FINISH_TYPE:
                mTvTitle.setText("退款");
                break;

        }
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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