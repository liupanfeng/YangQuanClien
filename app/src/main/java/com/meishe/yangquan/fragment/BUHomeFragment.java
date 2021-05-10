package com.meishe.yangquan.fragment;

import android.app.Activity;
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
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.BUApplyShoppingActivity;
import com.meishe.yangquan.activity.BUHomeCommentManagerActivity;
import com.meishe.yangquan.activity.BUHomeGoodsManagerActivity;
import com.meishe.yangquan.activity.BUHomeOrderManagerActivity;
import com.meishe.yangquan.activity.BUHomeRefundManagerActivity;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUHomeShoppingDataInfo;
import com.meishe.yangquan.bean.BUHomeShoppingDataInfoResult;
import com.meishe.yangquan.bean.BUShopDataInfo;
import com.meishe.yangquan.bean.BUShoppingInfo;
import com.meishe.yangquan.bean.BUShoppingInfoResult;
import com.meishe.yangquan.bean.BUShoppingUserInfo;
import com.meishe.yangquan.bean.BUShoppingUserInfoResult;
import com.meishe.yangquan.bean.UserInfo;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.divider.CustomGridItemDecoration;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.manager.ShoppingInfoManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.GlideUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.IosDialog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.event.MessageEvent.MESSAGE_TYPE_UPDATE_SHOPPING_INFO;


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
    private TextView tv_bu_good_comment;
    private TextView tv_bu_middle_comment;
    private TextView tv_bu_low_comment;
    private TextView tv_bu_focus_count;
    private TextView tv_bu_wait_pay;
    private TextView tv_bu_wait_send_out_goods;
    private TextView tv_bu_comment_count;
    private Map<String, Integer> mShoppingDataMap = new HashMap<>();

    public static BUHomeFragment newInstance() {
        return new BUHomeFragment();
    }

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

        /*好评*/
        tv_bu_good_comment = view.findViewById(R.id.tv_bu_good_comment);
        /*中评*/
        tv_bu_middle_comment = view.findViewById(R.id.tv_bu_middle_comment);
        /*差评*/
        tv_bu_low_comment = view.findViewById(R.id.tv_bu_low_comment);
        /*关注我的数量*/
        tv_bu_focus_count = view.findViewById(R.id.tv_bu_focus_count);
        /*待付*/
        tv_bu_wait_pay = view.findViewById(R.id.tv_bu_wait_pay);
        /*待发货*/
        tv_bu_wait_send_out_goods = view.findViewById(R.id.tv_bu_wait_send_out_goods);
        /*评论数量*/
        tv_bu_comment_count = view.findViewById(R.id.tv_bu_comment_count);


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
        UserInfo user = UserManager.getInstance(App.getContext()).getUser();
        if (user == null) {
            getUserInfo();
        }
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
            buShopDataInfo.setAmount(mShoppingDataMap.get(shopData) == null ? 0 : mShoppingDataMap.get(shopData));
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
                UserManager.getInstance(mContext).setBuShoppingInfo(data);
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
     * 申请开店成功后，获取店铺首页数据(UI需要显示的数据)
     */
    public void getHomeShoppingData() {
        HashMap<String, Object> param = new HashMap<>();
        String token = getToken();
        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_SHOPPING_DATA, new BaseCallBack<BUHomeShoppingDataInfoResult>() {
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
            protected void onSuccess(Call call, Response response, BUHomeShoppingDataInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                BUHomeShoppingDataInfo data = result.getData();
//                updateUI(data);
                updateShoppingData(data);
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
//                    updateShoppingData(data);
                    getHomeShoppingData();
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
    private void updateShoppingData(BUHomeShoppingDataInfo data) {
        /*隐藏未开店铺的View*/
        mViewNoShop.setVisibility(View.GONE);
        /*显示开店铺的数据*/
        mViewOpenShop.setVisibility(View.VISIBLE);
//        "出售中", "待付", "待发货", "待评价", "退货中",
//                "今日总访客", "今日订单数量", "今日成交额", "收藏我的", "累计订单数量", "累计成交额"
        //更新店铺相关的一些信息
        if (data != null) {
            //更细顶部的View
            tv_bu_shop_name.setText(data.getShopName());
            GlideUtil.getInstance().loadPhotoUrl(data.getImageUrl(), bu_photo);


            tv_bu_good_comment.setText("好评 " + data.getGoodEvaluationCount());
            tv_bu_middle_comment.setText("中评 " + data.getNormalEvaluationCount());
            tv_bu_low_comment.setText("差评 " + data.getBadEvaluationCount());

            tv_bu_focus_count.setText("关注我的 " + data.getShopCollectionCount());

            tv_bu_wait_pay.setText("" + data.getGoodsCount());
            tv_bu_wait_send_out_goods.setText("" + data.getReceivedOrderCount());
            //评论数量
            tv_bu_comment_count.setText("" + data.getReceivedOrderCount());


            //初始化底部的数据，用于更新底部的view
            mShoppingDataMap.clear();
            mShoppingDataMap.put("出售中", data.getGoodsCount());
            mShoppingDataMap.put("待付", data.getCommittedOrderCount());
            mShoppingDataMap.put("待发货", data.getPayedOrderCount());
            mShoppingDataMap.put("待评价", data.getReceivedOrderCount());
            mShoppingDataMap.put("退货中", data.getApplyBackGoodsOrderCount());

            mShoppingDataMap.put("今日总访客", data.getVisitorAmount());

            mShoppingDataMap.put("今日订单数量", data.getTodayOrderCount());
            mShoppingDataMap.put("今日成交额", data.getTodayPrice());
            mShoppingDataMap.put("收藏我的", data.getGoodsCollectionCount());
            mShoppingDataMap.put("累计订单数量", data.getTotalOrderCount());
            mShoppingDataMap.put("累计成交额", data.getTotalPrice());

        }

        initShopData();
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        showLoading();
        String token = getToken();
        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_GET_USER_INFO, new BaseCallBack<UserResult>() {
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
            protected void onSuccess(Call call, Response response, UserResult result) {
                hideLoading();
                if (result != null) {
                    UserInfo user = result.getData();
                    if (user != null) {
                        UserManager.getInstance(App.getContext()).setUser(user);
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (e instanceof com.google.gson.JsonParseException) {
                    ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this); //解除注册
    }


    /**
     * On message event.
     * 消息事件
     *
     * @param event the event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int eventType = event.getEventType();
        switch (eventType){
            case MessageEvent.MESSAGE_TYPE_BU_APPLY_SHOPPING_SUCCESS:
                //申请开店成功
                BUShoppingInfo buShoppingInfo = UserManager.getInstance(mContext).getBuShoppingInfo();
                updateUI(buShoppingInfo);
                break;
        }
    }




}
