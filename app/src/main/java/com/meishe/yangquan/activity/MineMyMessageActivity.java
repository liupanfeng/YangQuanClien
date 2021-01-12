package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;

/**
 * 我的-我的消息
 */
public class MineMyMessageActivity extends BaseActivity {


    private TextView mTvTitle;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private MultiFunctionAdapter mAdapter;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_my_message;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mRecyclerView = findViewById(R.id.recycler);
        mIvBack = findViewById(R.id.iv_back);
        initRecyclerView();
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的消息");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

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
}
