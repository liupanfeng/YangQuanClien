package com.meishe.yangquan.fragment;

import android.content.Context;
import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MineTypeInfo;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends BaseRecyclerFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;

    private String[] mSettingInfo = {"完善资料", "消息中心", "我的商机", "版本更新", "联系我们"};
    private int[] mSettingIcon = {R.mipmap.ic_mine_wanshanziliao, R.mipmap.ic_mine_xiaoxizhongxin, R.mipmap.ic_mine_wodeshangji, R.mipmap.ic_mine_banbengegnxin, R.mipmap.ic_mine_lianxiwomen};

    public MineFragment() {
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mRecyclerView = view.findViewById(R.id.mine_recycler);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        MultiFunctionAdapter adapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        mList.clear();
        for (int i = 0; i < 5; i++) {
            MineTypeInfo info = new MineTypeInfo();
            info.setName(mSettingInfo[i]);
            info.setIcon(mSettingIcon[i]);
            mList.add(info);
        }
        adapter.addAll(mList);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
