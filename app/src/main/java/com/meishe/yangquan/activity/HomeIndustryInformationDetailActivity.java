package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.adapter.QuotationNewsAdapter;
import com.meishe.yangquan.bean.IndustryNewsClip;
import com.meishe.yangquan.bean.IndustryNewsInfo;
import com.meishe.yangquan.bean.IndustryNewsResult;
import com.meishe.yangquan.bean.section.QuotationNewsSection;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 行业资讯详情页面
 */
public class HomeIndustryInformationDetailActivity extends BaseActivity {

    /*新闻标题*/
    private TextView mTvIndustryTitle;
    /*作者和时间*/
    private TextView mTvAuthorAndTime;
    /*新闻来源*/
    private TextView mTvFrom;

    private QuotationNewsAdapter mQuotationNewsAdapter;

    @Override
    protected int initRootView() {
        return R.layout.activity_home_industry_information;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mRecyclerView = findViewById(R.id.recycler);
//        mTvIndustryTitle = findViewById(R.id.tv_industry_title);
//        mTvAuthorAndTime = findViewById(R.id.tv_author_and_time);
//        mTvFrom = findViewById(R.id.tv_from);

//        initRecyclerView();

         mQuotationNewsAdapter=new QuotationNewsAdapter(mContext);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mQuotationNewsAdapter);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int newsId = 0;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                newsId = extras.getInt("newsId");
            }
        }
        getIndustryInformationInfo(newsId);
    }


    @Override
    public void initTitle() {
        mTvTitle.setText("资讯详情");
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
     * 获取资讯详情页
     */
    private void getIndustryInformationInfo(int newsId) {

        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("newsId", newsId);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_NEWS_INFO, new BaseCallBack<IndustryNewsResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, IndustryNewsResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                IndustryNewsInfo data = result.getData();
                if (data == null) {
                    return;
                }
                setData(data);
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

    /**
     * 设置新闻数据
     *
     * @param industryNewsInfo
     */
    private void setData(IndustryNewsInfo industryNewsInfo) {
        if (industryNewsInfo == null) {
            return;
        }
//        mTvIndustryTitle.setText(industryNewsInfo.getTitle());
//        mTvAuthorAndTime.setText(industryNewsInfo.getAuthor() + " | " + FormatDateUtil.
//                longToString(industryNewsInfo.getPublishDate(),
//                        FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY));
//        mTvFrom.setText("来源于：" + industryNewsInfo.getNewsSource());
//
//        mAdapter.addAll(industryNewsInfo.getClips());

        List<QuotationNewsSection> list = new ArrayList<>();

        QuotationNewsSection quotationNewsSection = new QuotationNewsSection(null);
        quotationNewsSection.isHeader = true;
        quotationNewsSection.setIndustryNewsInfo(industryNewsInfo);
        list.add(quotationNewsSection);

        List<IndustryNewsClip> clips = industryNewsInfo.getClips();
        if (!CommonUtils.isEmpty(clips)) {
            for (IndustryNewsClip clip : clips) {
                quotationNewsSection = new QuotationNewsSection(clip);
                list.add(quotationNewsSection);
            }
        }

        mQuotationNewsAdapter.addData(list);
    }
}
