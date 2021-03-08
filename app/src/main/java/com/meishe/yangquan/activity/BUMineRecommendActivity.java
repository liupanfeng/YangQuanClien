package com.meishe.yangquan.activity;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineCallbackInfo;
import com.meishe.yangquan.bean.MineCallbackInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.view.BackEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商版-我的-反馈建议
 */
public class BUMineRecommendActivity extends BaseActivity {

    private List<MineCallbackInfo> mDatas = new ArrayList<>();

    private BackEditText et_say_your_idea;
    private TextView tv_sheep_bar_publish;


    @Override
    protected int initRootView() {
        return R.layout.activity_bu_mine_recommend;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);

        mRecyclerView = findViewById(R.id.recycler);

        et_say_your_idea = findViewById(R.id.et_say_your_idea);
        tv_sheep_bar_publish = findViewById(R.id.tv_sheep_bar_publish);

        initRecyclerView();

    }

    @Override
    public void initData() {

        getCallBackData();

    }

    @Override
    public void initTitle() {
        mTvTitle.setText("反馈建议");
    }

    @Override
    public void initListener() {
        tv_sheep_bar_publish.setOnClickListener(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监测最底部输入内容
        et_say_your_idea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                if (length == 0) {
                    tv_sheep_bar_publish.setTextColor(getResources().getColor(R.color.text_content_color));
                } else {
                    tv_sheep_bar_publish.setTextColor(getResources().getColor(R.color.app_main_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sheep_bar_publish:
                sendCallBackData();
                break;
        }
    }


    /**
     * 获取反馈建议列表
     */
    private void getCallBackData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_SUGGEST, new BaseCallBack<MineCallbackInfoResult>() {
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
            protected void onSuccess(Call call, Response response, MineCallbackInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineCallbackInfo> data = result.getData();
                    if (data == null) {
                        return;
                    }
                    mDatas.clear();
                    mDatas.addAll(data);
                    mAdapter.addAll(mDatas);
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


    /**
     * 获取反馈建议列表
     */
    private void sendCallBackData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        final String content = et_say_your_idea.getText().toString();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("content", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_SUGGEST_SEND, new BaseCallBack<ServerResult>() {
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
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "反馈成功！");
                    et_say_your_idea.setText("");
                    MineCallbackInfo callbackInfo = new MineCallbackInfo();
                    callbackInfo.setContent(content);
                    callbackInfo.setInitDate(System.currentTimeMillis());
                    callbackInfo.setInitUser(3);
                    mDatas.add(0, callbackInfo);
                    mAdapter.addAll(mDatas);
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


}