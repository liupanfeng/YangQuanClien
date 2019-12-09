package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.wiget.MaterialProgress;

public class ServiceTypeListFragment extends BaseRecyclerFragment {

    private static final String USER_TYPE = "user_type";

    private RecyclerView mRecyclerView;
    private MaterialProgress mMpProgress;

    private int mUserType;

    public ServiceTypeListFragment() {

    }

    public static ServiceTypeListFragment newInstance( int userType) {
        ServiceTypeListFragment fragment = new ServiceTypeListFragment();
        Bundle args = new Bundle();
        args.putInt(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserType = getArguments().getInt(USER_TYPE);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_service_type_list,container,false);
        mRecyclerView=view.findViewById(R.id.service_type_list_recycler);
        mMpProgress=view.findViewById(R.id.mp_progress);

        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        adapter.setFragment(this);
        adapter.setMaterialProgress(mMpProgress);
        mRecyclerView.setAdapter(adapter);

        HttpRequestUtil.getInstance(getContext()).getServiceListFromServer(mUserType);
//        mList.clear();
//        int index=12;
//        for (int i = 0; i <index ; i++) {
//            ServerCustomer serverCustomer=new ServerCustomer();
//            serverCustomer.setAutograph("服务信息"+i);
//            mList.add(serverCustomer);
//        }
//        adapter.addAll(mList);
    }


}
