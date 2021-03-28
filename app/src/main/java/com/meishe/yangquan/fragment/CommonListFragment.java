package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.helper.DataHelper;
import com.meishe.yangquan.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 21:08
 * @Description : 通用的列表fragment
 */
public class CommonListFragment extends BaseRecyclerFragment implements DataHelper.OnCallBackListener {

    protected List<BaseInfo> mList = new ArrayList<>();

    /*这个是请求那一类接口*/
    private int mType;
    /*这个是请求数据的类型*/
    private int mSubType;
    /*是否懒加载，解决ViewPage的预加载问题*/
    private boolean mLazLoad;
    private View mNoDataView;

    public static CommonListFragment newInstance(boolean isNeedLazLoad,int type,int subType) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.COMMON_TYPE, type);
        bundle.putInt(Constants.COMMON_SUB_TYPE, subType);
        bundle.putBoolean(Constants.COMMON_LAZ_LOAD_TYPE, isNeedLazLoad);
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.COMMON_TYPE);
            mSubType = arguments.getInt(Constants.COMMON_SUB_TYPE);
            mLazLoad = arguments.getBoolean(Constants.COMMON_LAZ_LOAD_TYPE);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_common_list_view,container,false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mNoDataView = view.findViewById(R.id.ll_no_data);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        if (!mLazLoad){
            getDataFromServer();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (mLazLoad){
            getDataFromServer();
        }
    }

    @Override
    protected void initListener() {
        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                mPageNum = 1;
                mIsLoadMore = false;
                getDataFromServer();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadMore = true;
                getDataFromServer();
            }
        });
    }

    private void getDataFromServer() {
        showLoading();
        //全部订单
        DataHelper.getInstance().setOnCallBackListener(this);
        switch (mType){
            case Constants.TYPE_COMMON_MY_ORDER_TYPE:
                DataHelper.getInstance().getOrderData(mList,mSubType,mPageNum,mPageSize,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_MY_MESSAGE:
                DataHelper.getInstance().getMyMessageData(mList,mSubType,mPageNum,mPageSize,
                        mIsLoadFinish,mIsLoadMore);
                break;
            case Constants.TYPE_COMMON_FEED_GOLD_TYPE:
                DataHelper.getInstance().getFeedGoldDataFromServer(mList,mSubType,mPageNum,mPageSize,
                        mIsLoadFinish,mIsLoadMore);
                break;

        }
    }


    @Override
    public void onShowNoData() {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        changeNoDataViewVisible(View.GONE);
        hideUIState();
    }

    @Override
    public void onSuccess(List<? extends BaseInfo> baseInfos,int pageSize,int pageNum) {
        changeNoDataViewVisible(View.GONE);
        hideUIState();
        mPageNum=pageNum+1;
        mList.addAll(mList.size(), baseInfos);
        mAdapter.addAll(mList);
    }


    @Override
    public void onFailure(Exception e) {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }

    @Override
    public void onError(Exception e) {
        hideUIState();
        changeNoDataViewVisible(View.VISIBLE);
    }


    private void changeNoDataViewVisible(int visible){
        mNoDataView.setVisibility(visible);
    }
}
