package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUMessageDataInfo;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/21
 * @Description : 商版-消息-列表
 */
public class BUMessageListFragment extends BaseRecyclerFragment {

    /*消息类型*/
    private int mType;

    private List<BUMessageDataInfo> mData;

    /**
     * @param type 消息类型
     * @return
     */
    public static BUMessageListFragment newInstance(int type) {
        BUMessageListFragment fragment = new BUMessageListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.BU_TYPE_MESSAGE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate方法中获取参数
        if (getArguments() != null) {
            mType = getArguments().getInt(Constants.BU_TYPE_MESSAGE);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_messsage_list, container, false);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mAdapter,
                ScreenUtils.dip2px(mContext,0.6f),mContext,LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        BUMessageDataInfo buMeesageDataInfo=null;
        switch (mType){
            case Constants.BU_TYPE_MESSAGE_ORDER:
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                break;
            case Constants.BU_TYPE_MESSAGE_COMMENT:
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                break;
            case Constants.BU_TYPE_MESSAGE_REFUND:
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                break;
            case Constants.BU_TYPE_MESSAGE_OTHER:
                buMeesageDataInfo=new BUMessageDataInfo();
                mData.add(buMeesageDataInfo);
                break;

        }
        mAdapter.addAll(mData);
    }

    @Override
    protected void initListener() {

    }
}
