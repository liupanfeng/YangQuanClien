package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedGoodsInfoListResult;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 饲料-饲料-商品列表
 * @date 2020/12/21 14:36
 */
@Deprecated
public class FeedGoodsListFragment extends BaseRecyclerFragment {

    /* 商品类别 ：推荐  综合 销量 价格 */
    private int mFeedType;

    private int mShoppingId;
    private String orderByType;

    public static FeedGoodsListFragment newInstance(int shoppingId, int type) {
        FeedGoodsListFragment feedContentListFragment = new FeedGoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FEED_TYPE, type);
        bundle.putInt(Constants.FEED_SHOPPING_IDTYPE, shoppingId);
        feedContentListFragment.setArguments(bundle);
        return feedContentListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mFeedType = arguments.getInt(Constants.FEED_TYPE);
            mShoppingId = arguments.getInt(Constants.FEED_SHOPPING_IDTYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_foods_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mAdapter, ScreenUtils.dip2px(mContext, 7), mContext, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        switch (mFeedType) {
//            case Constants.TYPE_FEED_FOODS_RECOMMEND:
//                List<FeedGoodsInfo> datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);
//                break;
//            case Constants.TYPE_FEED_FOODS_MULTIPLE:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);
//                break;
//            case Constants.TYPE_FEED_FOODS_SALES:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);
//                break;
//            case Constants.TYPE_FEED_FOODS_PRICE:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);
//                break;
//        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        switch (mFeedType) {
            case Constants.TYPE_FEED_FOODS_RECOMMEND:
//                List<FeedGoodsInfo> datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                mAdapter.addAll(datas);
                orderByType="sell_amount";
                break;
            case Constants.TYPE_FEED_FOODS_MULTIPLE:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);

                orderByType="mod_date";
                break;
            case Constants.TYPE_FEED_FOODS_SALES:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);

                orderByType="sell_amount";
                break;
            case Constants.TYPE_FEED_FOODS_PRICE:
//                datas = new ArrayList<>();
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//                datas.add(new FeedGoodsInfo());
//
//                mAdapter.addAll(datas);

                orderByType="price";
                break;
        }

        getShoppingData();
    }


    /**
     * 获取饲料商品-列表信息
     */
    public void getShoppingData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("shopId",mShoppingId);
        param.put("orderBy",orderByType);
        param.put("order","desc");
        param.put("pageSize",300);
        param.put("pageNum",1);

        String token = getToken();
        showLoading();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_GOODS_LIST, new BaseCallBack<FeedGoodsInfoListResult>() {
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
            protected void onSuccess(Call call, Response response, FeedGoodsInfoListResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<FeedGoodsInfo> data = result.getData();
                mAdapter.addAll(data);
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

}
