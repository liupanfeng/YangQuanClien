package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.helper.BackHandlerHelper;
import com.meishe.yangquan.inter.FragmentBackHandler;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerFragment extends Fragment implements FragmentBackHandler {

    protected Context mContext;
    protected List<BaseInfo> mList=new ArrayList<>();
    protected MaterialProgress mLoading;
    protected RecyclerView mRecyclerView;
    protected MultiFunctionAdapter mAdapter;
    /*分页加载 下拉刷新*/
    protected SmartRefreshLayout mRefreshLayout;
    /*是否加载成功*/
    protected boolean mIsLoadFinish = true;

    /*页码*/
    protected int mPageNum=1;
    /*每页的数量*/
    protected int mPageSize=3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }


    protected abstract View initView(LayoutInflater inflater, ViewGroup container);
    protected abstract void initListener();
    protected abstract void initData();

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }


    protected void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void showLoading(){
        mLoading.show();
    }

    protected void hideLoading(){
        mLoading.hide();
    }


    protected void hideUIState(){
        mIsLoadFinish = true;
        mLoading.hide();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
//        mNoDataTip.setVisibility(View.GONE);
//        mNoNetworkTipLayout.setVisibility(View.GONE);
    }

    protected String getToken(){
        return UserManager.getInstance(mContext).getToken();
    }

}
