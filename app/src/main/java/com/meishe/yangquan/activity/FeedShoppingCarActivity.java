package com.meishe.yangquan.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedShoppingCarGoodsInfo;
import com.meishe.yangquan.bean.FeedShoppingCarInfo;
import com.meishe.yangquan.bean.FeedShoppingCarInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.FeedGoodsManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
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
 * 饲料-购物车页面
 */
public class FeedShoppingCarActivity extends BaseActivity {

    /*默认*/
    private View rl_feed_normal;
    /*编辑*/
    private View rl_feed_operation;

    private Button btn_right;
    /*状态 1是下单 2 是删除 */
    private int isEditorState = 1;

    private ImageView iv_feed_shopping_car;
    /*删除*/
    private Button btn_feed_delete;
    private ImageView iv_feed_shopping_car_normal;
    /*下单*/
    private Button btn_feed_order;

    private boolean isSelectAll;

    @Override
    protected int initRootView() {
        return R.layout.activity_feed_shopping_car;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        btn_right = findViewById(R.id.btn_right);
        btn_right.setText("编辑");
        btn_right.setVisibility(View.VISIBLE);
        mRecyclerView = findViewById(R.id.recycler);

        rl_feed_normal = findViewById(R.id.rl_feed_normal);
        rl_feed_operation = findViewById(R.id.rl_feed_operation);
        /*编辑状态*/
        iv_feed_shopping_car = findViewById(R.id.iv_feed_shopping_car);
        btn_feed_delete = findViewById(R.id.btn_feed_delete);
        /*默认状态*/
        iv_feed_shopping_car_normal = findViewById(R.id.iv_feed_shopping_car_normal);
        btn_feed_order = findViewById(R.id.btn_feed_commit_order);


        initRecyclerView();
    }

    @Override
    public void initData() {
        isEditorState = 1;
        rl_feed_operation.setVisibility(View.GONE);
        rl_feed_normal.setVisibility(View.GONE);

        getShoppingCarData();
    }


    @Override
    public void initTitle() {
        mTvTitle.setText("购物车");
    }

    @Override
    public void initListener() {
        btn_right.setOnClickListener(this);

        iv_feed_shopping_car.setOnClickListener(this);
        btn_feed_delete.setOnClickListener(this);

        iv_feed_shopping_car_normal.setOnClickListener(this);
        btn_feed_order.setOnClickListener(this);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) baseInfo).setSelect(!((FeedShoppingCarGoodsInfo) baseInfo).isSelect());

                    List<BaseInfo> data = mAdapter.getData();
                    if (CommonUtils.isEmpty(data)) {
                        return;
                    }
                    List<BaseInfo> list = new ArrayList<>();
                    /*是否有没有被选择的商品*/
                    boolean isHasNoSelect = false;
                    for (int i = 0; i < data.size(); i++) {
                        BaseInfo info = data.get(i);
                        if (info instanceof FeedShoppingCarGoodsInfo && ((FeedShoppingCarGoodsInfo) info).isSelect()) {
                            list.add(info);
                        }else{
                            isHasNoSelect=true;
                        }
                    }

                    if (CommonUtils.isEmpty(list)) {
                        //隐藏View
                        rl_feed_operation.setVisibility(View.GONE);
                        rl_feed_normal.setVisibility(View.GONE);
                    } else {
                        //展示View
                        if (isEditorState == 1) {
                            rl_feed_operation.setVisibility(View.GONE);
                            rl_feed_normal.setVisibility(View.VISIBLE);

                        } else {
                            rl_feed_operation.setVisibility(View.VISIBLE);
                            rl_feed_normal.setVisibility(View.GONE);
                        }

                        if (isHasNoSelect){
                            iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                            iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                        }else{
                            iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
                            iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
                        }
                    }
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<BaseInfo> data = mAdapter.getData();
        if (CommonUtils.isEmpty(data)) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            BaseInfo info = data.get(i);
            if (info instanceof FeedShoppingCarGoodsInfo) {
                ((FeedShoppingCarGoodsInfo) info).setNeedHideSelect(false);
            }
        }
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_right) {

            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            List<BaseInfo> list = new ArrayList<>();
            /*是否有没有被选择的商品*/
            boolean isHasNoSelect = false;
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info instanceof FeedShoppingCarGoodsInfo && ((FeedShoppingCarGoodsInfo) info).isSelect()) {
                    list.add(info);
                }else{
                    isHasNoSelect=true;
                }
            }

            if (CommonUtils.isEmpty(list)) {
                //隐藏View
                rl_feed_operation.setVisibility(View.GONE);
                rl_feed_normal.setVisibility(View.GONE);
            } else {

                if (isHasNoSelect){
                    iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                    iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                }else{
                    iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
                    iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
                }
            }

            if (isEditorState==2) {
                btn_right.setText("编辑");
                isEditorState = 1;
                rl_feed_operation.setVisibility(View.GONE);
                rl_feed_normal.setVisibility(View.VISIBLE);

            } else {
                btn_right.setText("完成");
                rl_feed_operation.setVisibility(View.VISIBLE);
                rl_feed_normal.setVisibility(View.GONE);
                isEditorState = 2;
            }
        } else if (view.getId() == R.id.iv_feed_shopping_car) {
            doSelectButton();
        } else if (view.getId() == R.id.iv_feed_shopping_car_normal) {
            doNormalSelectButton();
        } else if (view.getId() == R.id.btn_feed_delete) {
            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            boolean isFirst = true;
            StringBuilder dataStr = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info instanceof FeedShoppingCarGoodsInfo && ((FeedShoppingCarGoodsInfo) info).isSelect()) {
                    if (isFirst) {
                        dataStr.append(((FeedShoppingCarGoodsInfo) info).getId() + "");
                        isFirst = false;
                    } else {
                        dataStr.append("," + ((FeedShoppingCarGoodsInfo) info).getId());
                    }
                }
            }
            deleteShoppingCarData(dataStr.toString());
        } else if (view.getId() == R.id.btn_feed_commit_order) {
            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            List<BaseInfo> list = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info instanceof FeedShoppingCarGoodsInfo && ((FeedShoppingCarGoodsInfo) info).isSelect()) {
                    list.add(info);
                }
            }

            if (CommonUtils.isEmpty(list)) {
                ToastUtil.showToast("请选择商品再下单!");
                return;
            }
            FeedGoodsManager.getInstance().setList(list);
            Bundle bundle=new Bundle();
            bundle.putString(Constants.TYPE_BUY_TYPE,"car");
            AppManager.getInstance().jumpActivity(this, FeedOrderActivity.class,bundle);
            finish();
        }
    }

    private void doNormalSelectButton() {
        if (isSelectAll) {
            iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle);
            isSelectAll = false;
            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info == null) {
                    continue;
                }
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) info).setSelect(false);
                }
            }
            mAdapter.notifyDataSetChanged();
        } else {
            iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
            isSelectAll = true;

            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info == null) {
                    continue;
                }
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) info).setSelect(true);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private void doSelectButton() {
        if (isSelectAll) {
            iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
            isSelectAll = false;
            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info == null) {
                    continue;
                }
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) info).setSelect(false);
                }
            }
            mAdapter.notifyDataSetChanged();
        } else {
            iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle_select);
            isSelectAll = true;

            List<BaseInfo> data = mAdapter.getData();
            if (CommonUtils.isEmpty(data)) {
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                BaseInfo info = data.get(i);
                if (info == null) {
                    continue;
                }
                if (info instanceof FeedShoppingCarGoodsInfo) {
                    ((FeedShoppingCarGoodsInfo) info).setSelect(true);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 删除购物车数据
     */
    private void deleteShoppingCarData(String goodsIds) {
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();
        showLoading();
        param.put("goodsId", goodsIds);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_SHOPPING_CAR_REMOVE, new BaseCallBack<ServerResult>() {
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
                }
                List<BaseInfo> data = mAdapter.getData();
                if (CommonUtils.isEmpty(data)) {
                    return;
                }
                for (int i = data.size() - 1; i >= 0; i--) {
                    BaseInfo info = data.get(i);
                    if (info instanceof FeedShoppingCarGoodsInfo && ((FeedShoppingCarGoodsInfo) info).isSelect()) {
                        data.remove(info);
                    }
                }

                rl_feed_operation.setVisibility(View.GONE);
                rl_feed_normal.setVisibility(View.GONE);

                mAdapter.notifyDataSetChanged();

                iv_feed_shopping_car_normal.setBackgroundResource(R.mipmap.ic_bu_home_circle);
                iv_feed_shopping_car.setBackgroundResource(R.mipmap.ic_bu_home_circle);
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
                if (CommonUtils.isEmpty(data)) {
                    mAdapter.addAll(data);
                } else {
                    List<FeedShoppingCarGoodsInfo> goodsList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        FeedShoppingCarInfo feedShoppingCarInfo = data.get(i);
                        if (feedShoppingCarInfo == null) {
                            continue;
                        }
                        goodsList.addAll(goodsList.size(), feedShoppingCarInfo.getGoodsList());
                    }
                    mAdapter.addAll(goodsList);
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
