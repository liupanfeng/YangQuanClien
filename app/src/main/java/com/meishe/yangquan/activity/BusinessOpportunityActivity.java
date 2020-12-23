package com.meishe.yangquan.activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BusinessOpportunity;
import com.meishe.yangquan.bean.BusinessOpportunityListResult;
import com.meishe.yangquan.bean.BusinessOpportunityResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.CustomToolbar;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的商机
 */
public class BusinessOpportunityActivity extends BaseActivity {

    private User mUser;
    private RecyclerView mRecyclerView;
    private MultiFunctionAdapter mAdapter;
    private View mNoDate;

    @Override
    protected int initRootView() {
        return R.layout.activity_business_opportunity;
    }

    @Override
    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler);
        mNoDate=findViewById(R.id.view_no_data);
    }

    @Override
    public void initData() {
        if (mUser == null) {
            return;
        }
        long userId = mUser.getUserId();
        LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        getMessageListFromServer(userId);
    }


    private void getMessageListFromServer(long userId) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userId", userId);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_MINE_BUSIJNESS, new BaseCallBack<BusinessOpportunityListResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, BusinessOpportunityListResult result) {
                if (response != null && response.code() == 200) {
                    if (result == null && result.getStatus() != 200) {
                        setNoDataVisible(View.VISIBLE);
                        return;
                    }
                    List<BusinessOpportunity> list = result.getData();
                    if (list != null && list.size() > 0) {
                        mAdapter.addAll(list);
                        setNoDataVisible(View.GONE);
                    } else {
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
    public void initTitle() {
        mToolbar.setMyTitle("我的商机");
        mToolbar.setMyTitleVisible(View.VISIBLE);
        mToolbar.setLeftButtonVisible(View.VISIBLE);
        mToolbar.setOnLeftButtonClickListener(new OnLeftButtonListener());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {

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

    private class OnLeftButtonListener implements CustomToolbar.OnLeftButtonClickListener {
        @Override
        public void onClick() {
            finish();
        }
    }

    public void setNoDataVisible(int visible){
        if (mNoDate!=null){
            mNoDate.setVisibility(visible);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
