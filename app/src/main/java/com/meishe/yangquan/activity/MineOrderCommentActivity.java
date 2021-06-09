package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineOrderCommentInfoChildParam;
import com.meishe.yangquan.bean.MineOrderCommentParamInfo;
import com.meishe.yangquan.bean.MineOrderCommentViewInfo;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.OrderContentsInfo;
import com.meishe.yangquan.bean.OrdersInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.EventBusUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的-订单-评论页面
 */
public class MineOrderCommentActivity extends BaseActivity {

    private int mOrderId;

    private List<MineOrderCommentViewInfo> mineOrderCommentViewInfos = new ArrayList<>();
    private Button btn_right;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_order_comment;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        btn_right = findViewById(R.id.btn_right);
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("发布");

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
                if (ordersInfo == null) {
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

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOrderEvaluate();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 发布订单评价
     */
    public void doOrderEvaluate() {
        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        List<BaseInfo> data = mAdapter.getData();
        if (CommonUtils.isEmpty(data)){
            return;
        }
        List<String> imageIds=new ArrayList<>();
        List<MineOrderCommentInfoChildParam> list=new ArrayList<>();

        for (int i = 0; i <data.size() ; i++) {
            BaseInfo info = data.get(i);
            if (info instanceof MineOrderCommentViewInfo){
                MineOrderCommentInfoChildParam mineOrderCommentInfoChildParam=new MineOrderCommentInfoChildParam();
                int goodId = ((MineOrderCommentViewInfo) info).getGoodId();
                if (goodId<=0){
                    return;
                }
                String description = ((MineOrderCommentViewInfo) info).getDescription();
                if (TextUtils.isEmpty(description)){
                    ToastUtil.showToast("请输入评论内容！");
                    return;
                }

            }
        }


        for (int i = 0; i <data.size() ; i++) {
            BaseInfo info = data.get(i);
            if (info instanceof MineOrderCommentViewInfo){
                MineOrderCommentInfoChildParam mineOrderCommentInfoChildParam=new MineOrderCommentInfoChildParam();
                mineOrderCommentInfoChildParam.setGoodsId(((MineOrderCommentViewInfo) info).getGoodId());
                mineOrderCommentInfoChildParam.setDescription(((MineOrderCommentViewInfo) info).getDescription());
                mineOrderCommentInfoChildParam.setScore((int) ((MineOrderCommentViewInfo) info).getScore());
                mineOrderCommentInfoChildParam.setFileIds(imageIds);
                list.add(mineOrderCommentInfoChildParam);
            }
        }

        MineOrderCommentParamInfo mineOrderCommentParamInfo=new MineOrderCommentParamInfo();
        mineOrderCommentParamInfo.setOrderId(mOrderId);
        mineOrderCommentParamInfo.setAnonymity(false);
        mineOrderCommentParamInfo.setList(list);

        Gson gson=new Gson();
        String json =  gson.toJson(mineOrderCommentParamInfo);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_EVALUATE, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast("评论成功！");
                    finish();
                    EventBusUtil.sendEvent(MessageEvent.MESSAGE_TYPE_COMMON_LIST,0,0);
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
        }, param, token,json);
    }


}
