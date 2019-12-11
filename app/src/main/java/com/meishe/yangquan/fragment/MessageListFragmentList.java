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
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  message list
 */
public class MessageListFragmentList extends BaseRecyclerFragment implements OnResponseListener {

    private static final String TYPE = "type";

    private int type;
    private RecyclerView mRecyclerView;
    private View mNoDate;
    private MultiFunctionAdapter mAdapter;
    private MaterialProgress mp_loading;

    public MessageListFragmentList() {
    }

    public static MessageListFragmentList newInstance(int type) {
        MessageListFragmentList fragment = new MessageListFragmentList();
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
        mp_loading=view.findViewById(R.id.mp_loading);
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
        mAdapter.setMaterialProgress(mp_loading);
        getMessageListFromServer(type);
    }


    /**
     * 信息列表
     */
    public void getMessageListFromServer(int userType){

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userType",userType);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_MESSAGE_LIST, new BaseCallBack<MessageResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, MessageResult result) {
                if (response != null&&response.code()==200) {
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
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                setNoDataVisible(View.VISIBLE);
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {

    }

    @Override
    public void onError(Object obj) {
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
