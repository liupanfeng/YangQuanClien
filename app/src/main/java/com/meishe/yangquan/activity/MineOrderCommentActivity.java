package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineOrderCommentViewInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.OrderContentsInfo;
import com.meishe.yangquan.bean.OrdersInfo;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的-订单-评论页面
 */
public class MineOrderCommentActivity extends BaseActivity {

    private int mOrderId;

    private List<MineOrderCommentViewInfo> mineOrderCommentViewInfos=new ArrayList<>();

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_order_comment;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
    }

    @Override
    public void initData() {
        MineOrderInfo mineOrderInfo = UserManager.getInstance(mContext).getMineOrderInfo();
        if (mineOrderInfo == null) {
            return;
        }
        mineOrderCommentViewInfos.clear();

        List<OrdersInfo> orders = mineOrderInfo.getOrders();
        mOrderId = mineOrderInfo.getOrderId();
        if (!CommonUtils.isEmpty(orders)) {
            for (int i = 0; i < orders.size(); i++) {
                OrdersInfo ordersInfo = orders.get(i);
                if (orders == null) {
                    continue;
                }
                List<OrderContentsInfo> orderContents = ordersInfo.getOrderContents();
                if (!CommonUtils.isEmpty(orderContents)) {
                    for (int j = 0; j < orderContents.size(); j++) {
                        OrderContentsInfo orderContentsInfo = orderContents.get(j);
                        if (orderContentsInfo == null) {
                            continue;
                        }
                        MineOrderCommentViewInfo mineOrderCommentViewInfo = MineOrderCommentViewInfo.parseInfo(orderContentsInfo);
                        mineOrderCommentViewInfos.add(mineOrderCommentViewInfo);

                    }
                }
            }
        }

        mAdapter.addAll(mineOrderCommentViewInfos);


    }

    @Override
    public void initTitle() {
        mTvTitle.setText("发表评价");
    }

    @Override
    public void initListener() {
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
    public void onClick(View view) {

    }
}
