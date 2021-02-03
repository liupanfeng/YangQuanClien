package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfoResult;
import com.meishe.yangquan.bean.MineMyMessageInfo;
import com.meishe.yangquan.bean.MineMyMessageInfoResult;
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
 * @author 86188
 * 我的-养殖档案页面
 */
public class MineBreedingArchivesActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected int initRootView() {
        return R.layout.activity_mine_breeding_archives;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recycler);
        initRecyclerView();
    }

    @Override
    public void initData() {
        getBreedingArchivesData();
    }

    private void getBreedingArchivesData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_BREEDING_ARCHIVE, new BaseCallBack<MineBreedingArchivesInfoResult>() {
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
            protected void onSuccess(Call call, Response response, MineBreedingArchivesInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineBreedingArchivesInfo> data = result.getData();
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
        mTvTitle.setText("养殖档案");
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
    public void onClick(View v) {

    }
}