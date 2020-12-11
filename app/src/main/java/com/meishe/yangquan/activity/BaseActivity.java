package com.meishe.yangquan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.meishe.yangquan.wiget.TitleBar;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener , OnResponseListener {

    protected Context mContext;

    protected TitleBar mTitleBar;

    protected CustomToolbar mToolbar;
    protected MultiFunctionAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //把当前初始化的activity加入栈中
        AppManager.getInstance().addActivity(this);
        //设置视图
        setContentView(initRootView());
        mContext=this;
        HttpRequestUtil.getInstance(mContext).setListener(this);
        initView();
        initData();
        initTitle();
        initListener();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    protected abstract int initRootView();

    public abstract void initView();

    public abstract void initData();

    public abstract void initTitle();

    public abstract void initListener();

    public abstract void release();

    protected void initRecyclerView(){
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
