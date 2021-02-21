package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.MarketResult;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.bean.ServiceResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/26
 * @Description : 市场具体内容页面
 */
public class HomeListFragment extends BaseRecyclerFragment {

    private int mType;
    private int mListType;
    private int mTabType;
    protected List<BaseInfo> mList = new ArrayList<>();

    public static HomeListFragment newInstance(int type, int listType, int tabType) {
        HomeListFragment homeListFragment = new HomeListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(Constants.MARKET_TYPE, type);
        arguments.putInt(Constants.MARKET_LIST_TYPE, listType);
        arguments.putInt(Constants.TAB_TYPE, tabType);
        homeListFragment.setArguments(arguments);
        return homeListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.MARKET_TYPE);
            mListType = arguments.getInt(Constants.MARKET_LIST_TYPE);
            mTabType = arguments.getInt(Constants.TAB_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_market_sheep_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initListener() {

        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                mIsLoadFinish = true;
                mPageNum = 1;
                if (mTabType == Constants.TAB_TYPE_MARKET) {
                    getMarketDataFromServer(mType, mListType);
                } else if (mTabType == Constants.TAB_TYPE_SERVICE) {
                    getServiceDataFromServer(mType, mListType);
                }
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadFinish = true;
                mIsLoadMore = true;
                if (mTabType == Constants.TAB_TYPE_MARKET) {
                    getMarketDataFromServer(mType, mListType);
                } else if (mTabType == Constants.TAB_TYPE_SERVICE) {
                    getServiceDataFromServer(mType, mListType);
                }
            }
        });

    }

    @Override
    protected void initData() {
        mList.clear();
        mPageNum = 1;
        if (mTabType == Constants.TAB_TYPE_MARKET) {
            getMarketDataFromServer(mType, mListType);
        } else if (mTabType == Constants.TAB_TYPE_SERVICE) {
            getServiceDataFromServer(mType, mListType);
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

    }

    /**
     * 获取市场数据
     */
    private void getMarketDataFromServer(int typeId, int listType) {
        if (!mIsLoadFinish) {
            return;
        }
        mIsLoadFinish = false;

        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageNum", mPageNum);
        param.put("pageSize", mPageSize);

        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_MARKET, new BaseCallBack<MarketResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, MarketResult result) {
                hideUIState();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }

                List<MarketInfo> datas = result.getData();
                if (CommonUtils.isEmpty(datas) && mIsLoadMore) {
                    ToastUtil.showToast("暂无更多内容！");
                    mIsLoadMore = false;
                    return;
                }

                /*页码增加*/
                mPageNum++;
                mList.addAll(mList.size(), datas);
                mAdapter.addAll(mList);

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
     * 获取服务数据
     */
    private void getServiceDataFromServer(final int typeId, int listType) {
        if (!mIsLoadFinish) {
            return;
        }
        mIsLoadFinish = false;
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageNum", mPageNum);
        param.put("pageSize", mPageSize);

        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_SERVICE, new BaseCallBack<ServiceResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, ServiceResult result) {
                hideUIState();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<ServiceInfo> datas = result.getData();
                if (CommonUtils.isEmpty(datas)) {
                    ToastUtil.showToast("暂无更多内容！");
                    mIsLoadMore = false;
                    return;
                }

                for (int i = 0; i < datas.size(); i++) {
                    ServiceInfo serviceInfo = datas.get(i);
                    serviceInfo.setServerType(typeId);
                }

                /*页码增加*/
                mPageNum++;
                mList.addAll(mList.size(), datas);
                mAdapter.addAll(mList);
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
