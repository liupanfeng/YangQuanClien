package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.MineRefundProgressInfo;
import com.meishe.yangquan.bean.MineRefundProgressInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的-退货进度
 */
public class MineRefundProgressActivity extends BaseActivity {


    private TextView mTvOrderId;
    private TextView mRefundProgress;

    private int mOrderId;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_refund_progress;
    }

    @Override
    public void initView() {
        mTvOrderId = findViewById(R.id.tv_mine_order_id);
        mRefundProgress = findViewById(R.id.tv_mine_refund_progress);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mOrderId =extras.getInt(Constants.TYPE_ORDER_ID);
                if (mOrderId<=0){
                    return;
                }
            }
        }
        getRefundProgressData();
    }

    /**
     * 获取退货进度数据
     * <p>
     * 1 取消订单的进度
     * 2 退货的进度
     */
    private void getRefundProgressData() {
        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId", mOrderId);
        param.put("type", 2);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_PROGRESS, new BaseCallBack<MineRefundProgressInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, MineRefundProgressInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineRefundProgressInfo> data = result.getData();
                    mAdapter.addAll(data);
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

    @Override
    public void initTitle() {

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