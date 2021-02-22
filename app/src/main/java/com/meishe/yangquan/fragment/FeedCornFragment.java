package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 饲料-玉米
 * @date 2020/12/21 14:36
 */
public class FeedCornFragment extends BaseRecyclerFragment{

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_corn, container, false);
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
        List<FeedShoppingInfo>  datas=new ArrayList<>();
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());
        datas.add(new FeedShoppingInfo());

        mAdapter.addAll(datas);
    }
}
