package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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
        List<MineOrderInfo> lists=new ArrayList<>();
        MineOrderInfo mineOrderInfo = new MineOrderInfo();
        mineOrderInfo.setType(mType);
        lists.add(mineOrderInfo);

        mineOrderInfo = new MineOrderInfo();
        mineOrderInfo.setType(mType);
        lists.add(mineOrderInfo);

        mineOrderInfo = new MineOrderInfo();
        mineOrderInfo.setType(mType);
        lists.add(mineOrderInfo);

        mineOrderInfo = new MineOrderInfo();
        mineOrderInfo.setType(mType);
        lists.add(mineOrderInfo);

        mineOrderInfo = new MineOrderInfo();
        mineOrderInfo.setType(mType);
        lists.add(mineOrderInfo);

        mAdapter.addAll(lists);

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

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

    }
}