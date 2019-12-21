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
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceMessageResult;
import com.meishe.yangquan.bean.ServiceTypeInfo;
import com.meishe.yangquan.bean.SheepNews;
import com.meishe.yangquan.bean.SheepNewsResult;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.UserType;
import com.meishe.yangquan.view.AutoPollRecyclerView;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.util.List;

public class ServiceFragment extends BaseRecyclerFragment implements OnResponseListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private String mParam1;
    private String mParam2;
    private AutoPollRecyclerView mRecyclerView;
    private RecyclerView mServiceTypeRecycler;
    private RecyclerView mServiceNewsRecycler;
    private static final int SERVICE_FLOW_MESSAGE=1;
    private static final int SERVICE_SHEEP_NEWS =2;
    private MultiFunctionAdapter mServiceMessageAdapter;
    private MultiFunctionAdapter mSheepNewsAdapter;
    private MaterialProgress mp_loading;

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
        mp_loading=view.findViewById(R.id.mp_loading);

        mServiceTypeRecycler=view.findViewById(R.id.service_type_recycler);
        mServiceNewsRecycler=view.findViewById(R.id.service_news_recycler);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        initTopNotifyRecyclerView();
        initServiceTypeRecyclerView();
        initServiceNewsRecyclerView();
        mp_loading.show();
        HttpRequestUtil.getInstance(mContext).getServiceMessageFromServer(SERVICE_FLOW_MESSAGE);
        HttpRequestUtil.getInstance(mContext).getServiceNewsFromServer(SERVICE_SHEEP_NEWS);
        HttpRequestUtil.getInstance(mContext).setListener(this);
    }


    /**
     * 头部系统通知
     */
    private void initTopNotifyRecyclerView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mServiceMessageAdapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        mRecyclerView.setAdapter(mServiceMessageAdapter);

    }

    private void initServiceNewsRecyclerView() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        mServiceNewsRecycler.setLayoutManager(layoutManager);
        mSheepNewsAdapter=new MultiFunctionAdapter(mContext,mServiceNewsRecycler);
        mServiceNewsRecycler.setAdapter(mSheepNewsAdapter);
//        int index=12;
//        mList.clear();
//        for (int i = 0; i <index ; i++) {
//            SheepNews sheepNews=new SheepNews();
//            sheepNews.setSheepName("东北羊");
//            sheepNews.setSheepPrice("30.5");
//            mList.add(sheepNews);
//        }
//        mList.add(new EndInfo());

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
            typeInfo.setType(UserType.getUserType().get(i));
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

    @Override
    public void onSuccess(Object object) {
        if (mp_loading!=null){
            mp_loading.hide();
        }
    }

    @Override
    public void onSuccess(int type, Object object) {
        if (mp_loading!=null){
            mp_loading.hide();
        }

        switch (type){
            case SERVICE_FLOW_MESSAGE:
                if (object instanceof ServiceMessageResult){
                    List<ServiceMessage> messageList = ((ServiceMessageResult) object).getData();
                    if (messageList!=null&&messageList.size()>0){
                        mServiceMessageAdapter.setNeedAutoScroll(true);
                        mServiceMessageAdapter.addAll(messageList);
                        mRecyclerView.start();
                    }
                }
                break;
            case SERVICE_SHEEP_NEWS:
                if (object instanceof SheepNewsResult){
                    List<SheepNews> sheepNews = ((SheepNewsResult) object).getData();
                    if (sheepNews!=null&&sheepNews.size()>0){
                        sheepNews.get(0).setNeedShowLabel(true);
                        mSheepNewsAdapter.addAll(sheepNews);
                    }
                }

                break;
        }
    }

    @Override
    public void onError(Object obj) {
        if (mp_loading!=null){
            mp_loading.hide();
        }
    }

    @Override
    public void onError(int type, Object obj) {
        if (mp_loading!=null){
            mp_loading.hide();
        }
    }
}
