package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.ServerCustomer;

public class MessageTypeListFragment extends BaseRecyclerFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;

    public MessageTypeListFragment() {
    }

    public static MessageTypeListFragment newInstance(String param1, String param2) {
        MessageTypeListFragment fragment = new MessageTypeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_message_type_list,container,false);
        mRecyclerView=view.findViewById(R.id.message_type_list_recycler);
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
        mRecyclerView.setAdapter(adapter);
        mList.clear();
        int index=12;
        for (int i = 0; i <index ; i++) {
            Message message=new Message();
            message.setNickname("服务信息"+i);
            mList.add(message);
        }
        adapter.addAll(mList);
    }


}
