package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.utils.ToastUtil;

public class MessageFragmentList extends BaseRecyclerFragment implements OnResponseListener {

    private static final String TYPE = "type";

    private int type;
    private RecyclerView mRecyclerView;

    public MessageFragmentList() {
    }

    public static MessageFragmentList newInstance(int type) {
        MessageFragmentList fragment = new MessageFragmentList();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_message_fragment_list_layout,container,false);
        mRecyclerView=view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        MultiFunctionAdapter adapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.setFragment(this);
//        for (int i = 0; i < 10; i++) {
//            Message message=new Message();
//            message.setNickname("牧羊人"+i);
//            message.setContent("羊大又肥"+i);
//            mList.add(message);
//        }
//
//        adapter.addAll(mList);
//
//        HttpRequestUtil.getInstance(getContext()).getMessageListFromServer(type);
//        HttpRequestUtil.getInstance(getContext()).setListener(this);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {
        ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
    }

    @Override
    public void onError(int type, Object obj) {

    }
}
