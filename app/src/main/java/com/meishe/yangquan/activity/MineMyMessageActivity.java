package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.MineMyMessageInfo;
import com.meishe.yangquan.bean.MineMyMessageInfoResult;
import com.meishe.yangquan.bean.MinePointsInfo;
import com.meishe.yangquan.bean.MinePointsInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

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
//        getMyMessageData();
    }

    /**
     * 获取我的消息列表
     */
    private void getMyMessageData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_MY_POINTS, new BaseCallBack<MineMyMessageInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MineMyMessageInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineMyMessageInfo> data = result.getData();
                    if (data == null) {
                        return;
                    }
                    mAdapter.addAll(data);
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
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

}
