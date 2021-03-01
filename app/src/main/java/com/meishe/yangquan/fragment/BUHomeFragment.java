package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUApplyShoppingActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.bean.BUShoppingUserInfo;
import com.meishe.yangquan.bean.BUShoppingUserInfoResult;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
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
 * @Author : lpf
 * @CreateDate : 2021/1/28
 * @Description : 商版工作台主页面
 */
public class BUHomeFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private TextView mTvTips;
    /*申请开店*/
    private Button mBtnApplyOpenShop;
    /*未开店容器*/
    private View mViewNoShop;
    /*已开店容器*/
    private View mViewOpenShop;

    /*店铺数据*/
    private RecyclerView mShopDataRecycler;
    /*羊管家*/
    private MultiFunctionAdapter mGridAdapter;

    private String[] mShopData = {"出售中", "待付", "待发货", "待评价", "退货中",
            "今日总访客", "今日订单数量", "今日成交额", "收藏我的", "累计订单数量", "累计成交额"};



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home, container, false);
        mViewNoShop = view.findViewById(R.id.view_no_shop);
        mViewOpenShop = view.findViewById(R.id.view_open_shop);

        mViewNoShop.setVisibility(View.VISIBLE);
        mViewOpenShop.setVisibility(View.GONE);

        mRecyclerView = view.findViewById(R.id.recycler);
        mShopDataRecycler = view.findViewById(R.id.bu_shop_data_recycler);
        mTvTips = view.findViewById(R.id.tv_bu_tips);
        mBtnApplyOpenShop = view.findViewById(R.id.btn_bu_apply_open_shop);

        initShoppingUserRecycler();
        initShopDataRecycler();

        return view;
    }

    /**
     * 初始化 已经入住的用户的信息
     */
    private void initShoppingUserRecycler() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 5);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(5);
        mRecyclerView.addItemDecoration(customGridItemDecoration);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化 店铺数据recycler
     */
    private void initShopDataRecycler() {
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(mContext, 3);
        mGridAdapter = new MultiFunctionAdapter(mContext, mShopDataRecycler);
        CustomGridItemDecoration customGridItemDecoration = new CustomGridItemDecoration(5);
        mShopDataRecycler.addItemDecoration(customGridItemDecoration);
        mShopDataRecycler.setLayoutManager(gridLayoutManager);
        mShopDataRecycler.setAdapter(mGridAdapter);

    }

    @Override
    protected void initData() {
        getShoppingData();
    }

    /**
     * 初始化店铺数据
     */
    private void initShopData() {
        List<BUShopDataInfo> buShopDataInfoArrayList = new ArrayList<>();

        for (int i = 0; i < mShopData.length; i++) {
            String shopData = mShopData[i];
            BUShopDataInfo buShopDataInfo = new BUShopDataInfo();
            buShopDataInfo.setName(shopData);
            buShopDataInfo.setAmount(i);
            buShopDataInfoArrayList.add(buShopDataInfo);
        }
        mGridAdapter.addAll(buShopDataInfoArrayList);

        getShoppingUserData();

    }

    @Override
    protected void initListener() {
        mBtnApplyOpenShop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bu_apply_open_shop:
                AppManager.getInstance().jumpActivity(getActivity(), BUApplyShoppingActivity.class);
                break;
        }
    }



    /**
     * 获取已经入住的用户
     */
    public void getShoppingUserData(){
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();
        param.put("pageNum", 1);
        param.put("pageSize", 30);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_SHOPPING_USER_LIST, new BaseCallBack<BUShoppingUserInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, BUShoppingUserInfoResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<BUShoppingUserInfo> data = result.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                mAdapter.addAll(data);
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


    /**
     * 获取店铺信息
     */
    public void getShoppingData(){
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_APPLY_SHOPPING_INFO, new BaseCallBack<BUShoppingInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, BUShoppingInfoResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                BUShoppingInfo data = result.getData();
                if (data == null ) {
                    initShopData();
                    return;
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
