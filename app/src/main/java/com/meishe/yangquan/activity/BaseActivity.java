package com.meishe.yangquan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.wiget.TitleBar;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected TitleBar mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //把当前初始化的activity加入栈中
        AppManager.getInstance().addActivity(this);
        //设置视图
        setContentView(initRootView());
        mContext=this;
        initView();
        initData();
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

    public abstract void initListener();

    public abstract void release();


}
