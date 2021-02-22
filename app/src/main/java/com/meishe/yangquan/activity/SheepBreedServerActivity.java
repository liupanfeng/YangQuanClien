package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MyBusinessInfo;
import com.meishe.yangquan.bean.MyBusinessInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 羊管家-养殖服务
 */
public class SheepBreedServerActivity extends BaseActivity {


    private LinearLayout mLlApplyService;
    private LinearLayout mLlMyBusiness;

    private boolean mIsApplying;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_breed_server;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mLlApplyService = findViewById(R.id.ll_apply_service);
        mLlMyBusiness = findViewById(R.id.ll_my_business);
    }

    @Override
    public void initData() {
        getMyBusinessFromServer();
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("养殖服务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //申请服务
        mLlApplyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsApplying) {
                    ToastUtil.showToast("您有金融服务申请中，请等待……");
                    return;
                }
                AppManager.getInstance().jumpActivity(SheepBreedServerActivity.this, SheepApplyIntroduceActivity.class);
            }
        });
        //我的业务
        mLlMyBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepBreedServerActivity.this, SheepMyBusinessActivity.class);
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }


    private void getMyBusinessFromServer() {
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_APPLY_MY_BUSINESS, new BaseCallBack<MyBusinessInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mIsApplying = false;
            }

            @Override
            protected void onSuccess(Call call, Response response, MyBusinessInfoResult result) {
                if (result != null) {
                    int status = result.getStatus();
                    if (status == 1) {
                        MyBusinessInfo businessInfo = result.getData();
                        if (businessInfo == null) {
                            mIsApplying = false;
                        } else {
                            int authState = businessInfo.getAuthState();
                            if (authState == 0) {
                                mIsApplying = true;
                            } else if (authState == 1) {
                                mIsApplying = true;
                            } else if (authState == 2) {
                                mIsApplying = true;
                            } else {
                                mIsApplying = false;
                            }
                        }
                    } else {
                        ToastUtil.showToast(mContext, result.getMsg());
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                mIsApplying = false;
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, null, token);
    }


}
