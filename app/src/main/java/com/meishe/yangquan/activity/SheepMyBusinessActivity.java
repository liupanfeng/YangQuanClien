package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
 * 羊管家-我的业务
 */
public class SheepMyBusinessActivity extends BaseActivity {

    private Button mBtnAply;
    private Button mBtnBack;
    /**
     * 没有服务申请信息
     */
    private LinearLayout mLlNoMessage;
    /**
     * 复审
     */
    private RelativeLayout mRlMyBusinessReview;
    /**
     * 申请
     */
    private RelativeLayout mRlMyBusinessApply;
    /**
     * 初审
     */
    private RelativeLayout mRlMyBusinessFirst;
    /**
     * 完成
     */
    private RelativeLayout mRlMyBusinessFinish;

    @Override
    protected int initRootView() {
        return R.layout.activity_sheep_my_business;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mBtnAply = findViewById(R.id.btn_apply);
        mBtnBack = findViewById(R.id.btn_back);
        mLlNoMessage = findViewById(R.id.ll_no_message);
        mRlMyBusinessReview = findViewById(R.id.rl_my_business_review);
        mRlMyBusinessApply = findViewById(R.id.rl_my_business_apply);
        mRlMyBusinessFirst = findViewById(R.id.rl_my_business_first);
        mRlMyBusinessFinish = findViewById(R.id.rl_my_business_finish);
    }

    @Override
    public void initData() {
        getMyBusinessFromServer();
    }

    private void getMyBusinessFromServer() {
        String token= UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_HOLDER_APPLY_MY_BUSINESS, new BaseCallBack<MyBusinessInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                showNoMessage();
            }

            @Override
            protected void onSuccess(Call call, Response response, MyBusinessInfoResult result) {
                if (result!=null){
                    int status = result.getStatus();
                    if (status==1){
                        MyBusinessInfo businessInfo = result.getData();
                        if (businessInfo==null){
                            showNoMessage();
                        }else{
                            int authState = businessInfo.getAuthState();
                            if (authState==0){
                                showApplyMessage();
                            }else if (authState==1){
                                showFirstMessage();
                            }else if (authState==2){
                                showReviewMessage();
                            }
                        }
                    }else{
                        ToastUtil.showToast(mContext,result.getMsg());
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                showNoMessage();
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        },null,token);
    }

    @Override
    public void initTitle() {
        mTvTitle.setText("我的业务");
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnAply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().jumpActivity(SheepMyBusinessActivity.this,SheepApplyIntroduceActivity.class);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
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


    public void showNoMessage(){
        mLlNoMessage.setVisibility(View.VISIBLE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }

    /**
     * 展示申请
     */
    public void showApplyMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.VISIBLE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }


    /**
     * 展示初审
     */
    public void showFirstMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.VISIBLE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }


    /**
     * 展示复审
     */
    public void showReviewMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.VISIBLE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.GONE);
    }

    /**
     * 展示完成
     */
    public void showFinishMessage(){
        mLlNoMessage.setVisibility(View.GONE);
        mRlMyBusinessApply.setVisibility(View.GONE);
        mRlMyBusinessFirst.setVisibility(View.GONE);
        mRlMyBusinessReview.setVisibility(View.GONE);
        mRlMyBusinessFinish.setVisibility(View.VISIBLE);
    }
}
