package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.EndInfo;
import com.meishe.yangquan.bean.ServiceNotifyInfo;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.view.AutoPollRecyclerView;

public class ServiceFragment extends BaseRecyclerFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private String mParam1;
    private String mParam2;
    private AutoPollRecyclerView mRecyclerView;
    private RecyclerView mServiceTypeRecycler;
    private RecyclerView mServiceNewsRecycler;

    public ServiceFragment() {
    }

    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        mRecyclerView=view.findViewById(R.id.recycler);
        mServiceTypeRecycler=view.findViewById(R.id.service_type_recycler);
        mServiceNewsRecycler=view.findViewById(R.id.service_news_recycler);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        HttpRequestUtil.getInstance().getServiceMessageFromServer();
        initTopNotifyRecyclerView();
        initServiceTypeRecyclerView();
        initServiceNewsRecyclerView();
    }

    private void initServiceNewsRecyclerView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        mServiceNewsRecycler.setLayoutManager(layoutManager);
        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mServiceNewsRecycler);
        mServiceNewsRecycler.setAdapter(adapter);
        int index=12;
        mList.clear();
        for (int i = 0; i <index ; i++) {
            SheepNews sheepNews=new SheepNews();
            sheepNews.setSheepName("东北羊");
            sheepNews.setSheepPrice("30.5");
            mList.add(sheepNews);
        }
//        mList.add(new EndInfo());
        adapter.addAll(mList);
    }


    /**
     * 头部系统通知
     */
    private void initTopNotifyRecyclerView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        int index=6;
        mList.clear();
        for (int i = 0; i < index; i++) {
            ServiceNotifyInfo notifyInfo=new ServiceNotifyInfo();
            notifyInfo.setContent("*羊肉大涨价快来买啊，快来买快来买快来买……" +i);
            mList.add(notifyInfo);
        }
        adapter.setNeedAutoScroll(true);
        adapter.addAll(mList);
        mRecyclerView.start();
    }

    /**
     * 服务类型
     */
    private void initServiceTypeRecyclerView() {
        GridLayoutManager layoutManager=new GridLayoutManager(mContext, 5);
        mServiceTypeRecycler.setLayoutManager(layoutManager);
        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mServiceTypeRecycler);
        mServiceTypeRecycler.setAdapter(adapter);
        adapter.setFragment(this);
        mList.clear();
        for (int i = 0; i < UserType.getServiceTypeName().size(); i++) {
            ServiceTypeInfo typeInfo=new ServiceTypeInfo();
            typeInfo.setName(UserType.getServiceTypeName().get(i));
            typeInfo.setIcon(UserType.getUserTypeIcon().get(i));
            mList.add(typeInfo);
        }
        adapter.addAll(mList);
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRecyclerView.stop();
        mRecyclerView=null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView!=null){
            mRecyclerView.start();
        }
    }
}
