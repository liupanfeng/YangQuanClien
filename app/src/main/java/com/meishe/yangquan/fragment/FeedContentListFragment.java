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
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 饲料-饲料-内容页面   精料 草料  豆粕 麻申
 * @date 2020/12/21 14:36
 */
public class FeedContentListFragment extends BaseRecyclerFragment {

    private int mFeedType;

    public static FeedContentListFragment newInstance(int type) {
        FeedContentListFragment feedContentListFragment = new FeedContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.FEED_TYPE, type);
        feedContentListFragment.setArguments(bundle);
        return feedContentListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mFeedType = arguments.getInt(Constants.FEED_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_content_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mAdapter, ScreenUtils.dip2px(mContext,7),mContext,LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        switch (mFeedType){
            case Constants.TYPE_FEED_CONCENTRATE:
                List<FeedShoppingInfo> datas=new ArrayList<>();
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());

                mAdapter.addAll(datas);
                break;
            case Constants.TYPE_FEED_FORAGE:
                datas=new ArrayList<>();
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());

                mAdapter.addAll(datas);
                break;
            case Constants.TYPE_FEED_MEAL:
                datas=new ArrayList<>();
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());

                mAdapter.addAll(datas);
                break;
            case Constants.TYPE_FEED_MASHEN:
                datas=new ArrayList<>();
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());
                datas.add(new FeedShoppingInfo());

                mAdapter.addAll(datas);
                break;
        }


    }
}
