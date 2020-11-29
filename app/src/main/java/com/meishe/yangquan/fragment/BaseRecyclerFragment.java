package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerFragment extends Fragment {

    protected Context mContext;
    protected List<BaseInfo> mList=new ArrayList<>();
    protected MaterialProgress mLoading;
    protected RecyclerView mRecyclerView;

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

}
