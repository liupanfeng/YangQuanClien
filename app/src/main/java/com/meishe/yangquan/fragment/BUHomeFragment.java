package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUApplyShoppingActivity;
import com.meishe.yangquan.activity.BUHomeCommentManagerActivity;
import com.meishe.yangquan.activity.BUHomeGoodsManagerActivity;
import com.meishe.yangquan.activity.BUHomeOrderManagerActivity;
import com.meishe.yangquan.activity.BUHomeRefundManagerActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.bean.BUShoppingUserInfo;
import com.meishe.yangquan.bean.BUShoppingUserInfoResult;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.wiget.IosDialog;


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

    private int mShoppingStatue = -2;
    private IosDialog mIosDialog;

    /*店铺名称*/
    private TextView tv_bu_shop_name;
    /*店铺头像*/
    private ImageView bu_photo;
    private View ll_bu_goods_manager;
    private View ll_bu_order_manager;
    private View ll_bu_comment_manager;
    private View ll_bu_refund_manager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home, container, false);
        mViewNoShop = view.findViewById(R.id.view_no_shop);
        mViewOpenShop = view.findViewById(R.id.view_open_shop);
        mLoading = view.findViewById(R.id.loading);

        mRecyclerView = view.findViewById(R.id.recycler);
        mShopDataRecycler = view.findViewById(R.id.bu_shop_data_recycler);
        mTvTips = view.findViewById(R.id.tv_bu_tips);
        mBtnApplyOpenShop = view.findViewById(R.id.btn_bu_apply_open_shop);

        //已经开店
        tv_bu_shop_name = view.findViewById(R.id.tv_bu_shop_name);
        bu_photo = view.findViewById(R.id.bu_photo);
        ll_bu_goods_manager = view.findViewById(R.id.ll_bu_goods_manager);
        ll_bu_order_manager = view.findViewById(R.id.ll_bu_order_manager);
        ll_bu_comment_manager = view.findViewById(R.id.ll_bu_comment_manager);
        ll_bu_refund_manager = view.findViewById(R.id.ll_bu_refund_manager);


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

    }

    @Override
    protected void initListener() {
        mBtnApplyOpenShop.setOnClickListener(this);
        ll_bu_goods_manager.setOnClickListener(this);
        ll_bu_order_manager.setOnClickListener(this);
        ll_bu_comment_manager.setOnClickListener(this);
        ll_bu_refund_manager.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bu_apply_open_shop:
                switch (mShoppingStatue) {
                    case -2:
                        //未申请开店
                    case -1:
                        //申请被拒绝，需要重新申请
                        AppManager.getInstance().jumpActivity(getActivity(), BUApplyShoppingActivity.class);
                        break;
                    case 0:
                        //审核中，请稍后
                        showWaitDialog();
                        break;

                    case 1:
                        //审核通过

                        break;
                }

                break;

            case R.id.ll_bu_goods_manager:
                AppManager.getInstance().jumpActivity(getActivity(), BUHomeGoodsManagerActivity.class);
                break;
            case R.id.ll_bu_order_manager:
                AppManager.getInstance().jumpActivity(getActivity(), BUHomeOrderManagerActivity.class);
                break;
            case R.id.ll_bu_comment_manager:
                AppManager.getInstance().jumpActivity(getActivity(), BUHomeCommentManagerActivity.class);
                break;
            case R.id.ll_bu_refund_manager:
                AppManager.getInstance().jumpActivity(getActivity(), BUHomeRefundManagerActivity.class);
                break;
        }
    }

    /**
     * 展示等待的弹窗
     */
    private void showWaitDialog() {
        final IosDialog.DialogBuilder builder = new IosDialog.DialogBuilder(mContext);
//        builder.setTitle("创建养殖档案");
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.setText("审核中，请等待…");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                mIosDialog.hide();

            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
            }
        });
        mIosDialog = builder.create();
    }


    /**
     * 获取已经入住的用户
     */
    public void getShoppingUserData() {
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
    public void getShoppingData() {
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();
        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_APPLY_SHOPPING_INFO, new BaseCallBack<BUShoppingInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, BUShoppingInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                BUShoppingInfo data = result.getData();
                updateUI(data);
            }


            @Override
            protected void onResponse(Response response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
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
     * 根据请求到的店铺信息跟新UI
     *
     * @param data
     */
    private void updateUI(BUShoppingInfo data) {
        if (data == null) {
            //未申请店铺
            showNoShoppingView();
            return;
        } else {
            ShoppingInfoManager.getInstance().setBuShoppingInfo(data);
            mShoppingStatue = data.getAuthState();
            switch (mShoppingStatue) {
                case 0:
                    //审核中 暂时注释了
                    showNoShoppingView();
                    //TODO 临时存在
//                     updateShoppingData(data);

//                    showNoShoppingView();
                    break;
                case 1:
                    //审核通过
                    updateShoppingData(data);
                    break;
                case -1:
                    //审核未通过
                    showNoShoppingView();
                    break;
            }
        }

    }

    private void showNoShoppingView() {
        mViewNoShop.setVisibility(View.VISIBLE);
        mViewOpenShop.setVisibility(View.GONE);
        getShoppingUserData();
    }

    /**
     * 更新店铺UI
     *
     * @param data
     */
    private void updateShoppingData(BUShoppingInfo data) {
        mViewNoShop.setVisibility(View.GONE);
        mViewOpenShop.setVisibility(View.VISIBLE);
        //更新店铺相关的一些信息
        if (data != null) {
            tv_bu_shop_name.setText(data.getName());
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.mipmap.ic_message_list_photo_default);
            Glide.with(mContext)
                    .asBitmap()
                    .load(data.getShopInSideImageUrls().get(0))
                    .apply(options)
                    .into(bu_photo);
        }

        initShopData();
    }


}
