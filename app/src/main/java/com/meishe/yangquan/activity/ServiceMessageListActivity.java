package com.meishe.yangquan.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.ServiceMessage;
import com.meishe.yangquan.bean.ServiceMessageResult;
import com.meishe.yangquan.inter.OnResponseListener;
import com.meishe.yangquan.utils.HttpRequestUtil;
import com.meishe.yangquan.wiget.CustomToolbar;

import java.util.List;

/**
 * 无线循环消息详情页
 */
public class ServiceMessageListActivity extends AppCompatActivity implements OnResponseListener {

    private RecyclerView mRecyclerView;
    private MultiFunctionAdapter mAdapter;
    private View mNoDate;
    protected CustomToolbar mToolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_message_list);
        mContext=this;
        initView();
        initData();
        initTitle();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.service_type_list_recycler);
        mNoDate=findViewById(R.id.view_no_data);
    }

    private void initTitle() {
        mToolbar.setMyTitle("系统通知详情页");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setPageType(2);
        HttpRequestUtil.getInstance(mContext).getServiceMessageFromServer(1);
        HttpRequestUtil.getInstance(mContext).setListener(this);
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onSuccess(int type, Object object) {
        if (object instanceof ServiceMessageResult){
            List<ServiceMessage> messageList = ((ServiceMessageResult) object).getData();
            if (messageList!=null&&messageList.size()>0){
                mAdapter.addAll(messageList);
            }
        }
    }

    @Override
    public void onError(Object obj) {

    }

    @Override
    public void onError(int type, Object obj) {

    }

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {

        @Override
        public void onClick() {
            finish();
        }
    }
}
