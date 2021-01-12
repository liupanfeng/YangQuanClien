package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.ServerCustomer;
import com.meishe.yangquan.bean.ServerCustomerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.wiget.MaterialProgress;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceTypeListFragment extends BaseRecyclerFragment {

    private static final String USER_TYPE = "user_type";

    private RecyclerView mRecyclerView;
    private MaterialProgress mMpProgress;

    private int mUserType;

    private View mNoDate;
    private MultiFunctionAdapter mAdapter;
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

        mRecyclerView=view.findViewById(R.id.service_type_list_recycler);

        mMpProgress=view.findViewById(R.id.mp_progress);
        mNoDate=view.findViewById(R.id.view_no_data);

        if (mUserType==2){
        }
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new MultiFunctionAdapter(mContext,mRecyclerView);
        mAdapter.setFragment(this);
        mAdapter.setMaterialProgress(mMpProgress);
        mRecyclerView.setAdapter(mAdapter);

       getServiceListFromServer(mUserType);
//        mList.clear();
//        int index=12;
//        for (int i = 0; i <index ; i++) {
//            ServerCustomer serverCustomer=new ServerCustomer();
//            serverCustomer.setAutograph("服务信息"+i);
//            mList.add(serverCustomer);
//        }
//        adapter.addAll(mList);
    }


    /**
     * 服务列表消息
     */
    public  void  getServiceListFromServer(int userType){

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userType",userType);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_SERVICE_LIST, new BaseCallBack<ServerCustomerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                setNoDataVisible(View.VISIBLE);
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerCustomerResult result) {
                if (response != null&&response.code()==200) {
                    if (result==null&&result.getStatus()!=200){
                        setNoDataVisible(View.VISIBLE);
                        return;
                    }
                    List<ServerCustomer> list=result.getData();
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

    public void setNoDataVisible(int visible){
        if (mNoDate!=null){
            mNoDate.setVisibility(visible);
        }
    }


}
