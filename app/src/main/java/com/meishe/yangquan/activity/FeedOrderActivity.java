package com.meishe.yangquan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedAddressParamInfo;
import com.meishe.yangquan.bean.FeedGoodsParamInfo;
import com.meishe.yangquan.bean.FeedOrderParamInfo;
import com.meishe.yangquan.bean.FeedReceiverAddressInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.bean.IndustryNewsInfo;
import com.meishe.yangquan.bean.IndustryNewsResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.FeedGoodsManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 饲料-订单页面
 */
public class FeedOrderActivity extends BaseActivity {

    /*总价*/
    private TextView tv_feed_order_price;
    private TextView tv_feed_order_real_name;
    private View iv_feed_address_edit;

    private TextView tv_feed_order_address;
    /*详细地址*/
    private TextView tv_feed_order_detail_address;
    /*电话*/
    private TextView tv_feed_order_phone_number;

    private View btn_feed_commit_order;
    private ArrayList<BaseInfo> list;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_order;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recyclerView);
        btn_feed_commit_order = findViewById(R.id.btn_feed_commit_order);
        tv_feed_order_price = findViewById(R.id.tv_feed_order_price);
        iv_feed_address_edit = findViewById(R.id.iv_feed_address_edit);

        tv_feed_order_real_name = findViewById(R.id.tv_feed_order_real_name);
        tv_feed_order_address = findViewById(R.id.tv_feed_order_address);
        tv_feed_order_detail_address = findViewById(R.id.tv_feed_order_detail_address);
        tv_feed_order_phone_number = findViewById(R.id.tv_feed_order_phone_number);

        initRecyclerView();
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        list.addAll(FeedGoodsManager.getInstance().getList());
        if (!CommonUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                BaseInfo info = list.get(i);
                if (info == null) {
                    continue;
                }
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) info).setNeedHideSelect(true);
                }
            }
        }

        mAdapter.addAll(list);
        updateTotalPrice();
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("添加订单");
    }

    @Override
    public void initListener() {
        iv_feed_address_edit.setOnClickListener(this);
        btn_feed_commit_order.setOnClickListener(this);
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
    public void onClick(View v) {
        if (v.getId() == R.id.iv_feed_address_edit) {
            Bundle bundle = new Bundle();
            AppManager.getInstance().jumpActivityForResult(this, FeedReceiveAddressActivity.class, bundle, 200);
        }else if (v.getId()==R.id.btn_feed_commit_order){
            commitGoodsOrder();
        }
    }

    /**
     * 提交商品订单
     */
    private void commitGoodsOrder() {
        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        FeedOrderParamInfo feedOrderParamInfo=new FeedOrderParamInfo();
        feedOrderParamInfo.setComeFrom("car");

        FeedAddressParamInfo feedAddressParamInfo=new FeedAddressParamInfo();


        String name = tv_feed_order_real_name.getText().toString();
        String area = tv_feed_order_address.getText().toString();
        String address = tv_feed_order_detail_address.getText().toString();
        String phone = tv_feed_order_phone_number.getText().toString();

        feedAddressParamInfo.setAddress(address);
        feedAddressParamInfo.setArea(area);
        feedAddressParamInfo.setName(name);
        feedAddressParamInfo.setPhone(phone);

        feedOrderParamInfo.setReceiverInfo(feedAddressParamInfo);

        List<FeedGoodsParamInfo> goodsParamInfos=new ArrayList<>();
        if (!CommonUtils.isEmpty(list)){
            for (int i=0;i<list.size();i++){
                BaseInfo info = list.get(i);
                if (info instanceof FeedShoppingCarGoodsInfo){
                    FeedGoodsParamInfo feedGoodsParamInfo=new FeedGoodsParamInfo();
                    feedGoodsParamInfo.setGoodsId(((FeedShoppingCarGoodsInfo) info).getId());
                    feedGoodsParamInfo.setAmount(((FeedShoppingCarGoodsInfo) info).getSelectAmount());
                    goodsParamInfos.add(feedGoodsParamInfo);
                }
            }
        }

        feedOrderParamInfo.setGoods(goodsParamInfos);
        Gson gson=new Gson();
        String json = gson.toJson(feedOrderParamInfo);

        HashMap<String, Object> param = new HashMap<>();
        param.put("info", json);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_FEED_ORDER_COMMIT, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }

                ToastUtil.showToast(mContext, "订单提交成功!");
                finish();
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
        }, param, token,json);
    }

    @Override
    protected void eventBusUpdateUI(MessageEvent event) {
        super.eventBusUpdateUI(event);
        if (event.getEventType() == MessageEvent.MESSAGE_TYPE_FEED_GOODS_AMOUNT) {
            updateTotalPrice();
        }
    }

    /**
     * 更新总价
     */
    private void updateTotalPrice() {
        float totalPrice = 0;
        List<BaseInfo> data = mAdapter.getData();
        if (!CommonUtils.isEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    float price = ((FeedShoppingCarGoodsInfo) info).getPrice();
                    int selectAmount = ((FeedShoppingCarGoodsInfo) info).getSelectAmount();
                    totalPrice += price * selectAmount;
                }
            }
        }

        tv_feed_order_price.setText("￥" + totalPrice);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (data != null) {
                Serializable result = data.getSerializableExtra("result");
                if (result instanceof FeedReceiverAddressInfo) {
                    updateReceiveUI((FeedReceiverAddressInfo) result);
                }
            }
        }
    }

    /**
     * 更新收货UI
     */
    private void updateReceiveUI(FeedReceiverAddressInfo feedReceiverAddressInfo) {

        tv_feed_order_real_name.setText(feedReceiverAddressInfo.getReceiverName());
        tv_feed_order_address.setText(feedReceiverAddressInfo.getArea());
        tv_feed_order_detail_address.setText(feedReceiverAddressInfo.getDetailAddress());
        tv_feed_order_phone_number.setText(Util.formatNumber(feedReceiverAddressInfo.getReceiverPhone()));

    }
}