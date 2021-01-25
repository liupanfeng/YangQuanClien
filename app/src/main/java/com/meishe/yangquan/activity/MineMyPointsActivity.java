package com.meishe.yangquan.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MinePointsInfo;
import com.meishe.yangquan.bean.MinePointsInfoResult;
import com.meishe.yangquan.bean.QuotationHistoryInfo;
import com.meishe.yangquan.bean.QuotationHistoryInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的积分页面
 * @author 86188
 */
public class MineMyPointsActivity extends BaseActivity{

    private TextView mTvTitle;
    private ImageView mIvBack;
    /*总积分*/
    private TextView tv_my_points;


    @Override
    protected int initRootView() {
        return R.layout.activity_mine_my_points;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        tv_my_points = findViewById(R.id.tv_my_points);
        mRecyclerView = findViewById(R.id.recycler);
        initRecyclerView();
    }

    @Override
    public void initData() {
        getMyPointsData();
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的积分");
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

    /**
     * 获取积分方法
     */
    public void getMyPointsData(){
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_MY_POINTS, new BaseCallBack<MinePointsInfoResult>() {
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
            protected void onSuccess(Call call, Response response, MinePointsInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    MinePointsInfo data = result.getData();
                    if (data == null) {
                        return;
                    }
                    updateUi(data);
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

    private void updateUi(MinePointsInfo data) {
        if (data==null){
            return;
        }
        tv_my_points.setText(data.getWealth());
        mAdapter.addAll(data.getRecords());
    }

}
