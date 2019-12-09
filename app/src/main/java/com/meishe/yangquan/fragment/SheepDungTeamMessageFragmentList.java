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
import com.meishe.yangquan.bean.MessageResult;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpRequestUtil;

import java.util.List;

/**
 * 羊粪队伍
 */
public class SheepDungTeamMessageFragmentList extends BaseRecyclerFragment implements OnResponseListener {

    private static final String TYPE = "type";

    private int type;
    private RecyclerView mRecyclerView;
    private View mNoDate;
    private MultiFunctionAdapter mAdapter;

    public SheepDungTeamMessageFragmentList() {
    }

    public static SheepDungTeamMessageFragmentList newInstance(int type) {
        SheepDungTeamMessageFragmentList fragment = new SheepDungTeamMessageFragmentList();
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
        mNoDate=view.findViewById(R.id.view_no_data);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        mAdapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setFragment(this);
        HttpRequestUtil.getInstance(getContext()).getMessageListFromServer(type);
        HttpRequestUtil.getInstance(getContext()).setListener(this);
    }

    @Override
    public void onSuccess(Object object) {
        MessageResult result= (MessageResult) object;
        if (result==null&&result.getStatus()!=200){
            setNoDataVisible(View.VISIBLE);
            return;
        }
        List<Message> list=result.getData();
        if (list!=null&&list.size()>0){
            mAdapter.addAll(list);
            setNoDataVisible(View.GONE);
        }else{
            setNoDataVisible(View.VISIBLE);
        }

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {
        setNoDataVisible(View.VISIBLE);
    }

    @Override
    public void onError(int type, Object obj) {

    }


    public void setNoDataVisible(int visible){
        if (mNoDate!=null){
            mNoDate.setVisibility(visible);
        }
    }
}
