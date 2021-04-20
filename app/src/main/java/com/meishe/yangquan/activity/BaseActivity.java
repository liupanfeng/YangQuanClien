package com.meishe.yangquan.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.BarUtils;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.meishe.yangquan.wiget.MaterialProgress;
import com.meishe.yangquan.wiget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected Context mContext;

    protected TitleBar mTitleBar;

    protected CustomToolbar mToolbar;
    protected MultiFunctionAdapter mAdapter;
    protected RecyclerView mRecyclerView;

    protected TextView mTvTitle;
    protected ImageView mIvBack;

    protected MaterialProgress mLoading;

    protected List<Fragment> mFragmentList = new ArrayList<>();
    protected List<String> mTitleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //把当前初始化的activity加入栈中
        AppManager.getInstance().addActivity(this);
//        BarUtils.transparentStatusBar(this);
        //设置视图
        setContentView(initRootView());
        EventBus.getDefault().register(this);
        mContext = this;
//        HttpRequestUtil.getInstance(mContext).setListener(this);
        initView();
        initData();
        initTitle();
        initListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        release();
    }


    protected abstract int initRootView();

    public abstract void initView();

    public abstract void initData();

    public abstract void initTitle();

    public abstract void initListener();

    protected void eventBusUpdateUI(MessageEvent event){

    }

    public abstract void release();

    protected void showLoading() {
        if (mLoading==null){
            return;
        }
        mLoading.show();
    }

    protected void hideLoading() {
        if (mLoading==null){
            return;
        }
        mLoading.hide();
    }


    protected void initRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected String getToken() {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            AppManager.getInstance().jumpActivity(mContext, LoginActivity.class);
            return null;
        }
        return token;
    }


    /**
     * On message event.
     * 消息事件
     *
     * @param event the event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        eventBusUpdateUI(event);
    }



}
