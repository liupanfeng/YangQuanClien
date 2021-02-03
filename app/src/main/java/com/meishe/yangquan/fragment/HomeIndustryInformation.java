package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.HomeIndustryInformationDetailActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BannerInfo;
import com.meishe.yangquan.bean.BannerResult;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.IndustryResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.BannerLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 主页-行业资讯页面
 * @date 2020/11/26 10:44
 */
public class HomeIndustryInformation extends BaseRecyclerFragment {

    private BannerLayout mBanner;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_industry_information, container, false);
        mBanner = view.findViewById(R.id.banner);
        mRecyclerView = view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof IndustryInfo) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("newsId", ((IndustryInfo) baseInfo).getId());
                    AppManager.getInstance().jumpActivity(getContext(), HomeIndustryInformationDetailActivity.class, bundle);
                }

            }
        });
    }

    @Override
    protected void initData() {
        initRecyclerView();
        getBannerDataFromServer();
        getIndustryInfoFromServer();
    }

    /**
     * 获取资讯列表
     */
    private void getIndustryInfoFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        String token = UserManager.getInstance(mContext).getToken();
        param.put("pageNum", 1);
        param.put("pageSize", 30);

        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_NEWS_LIST, new BaseCallBack<IndustryResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, IndustryResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<IndustryInfo> data = result.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                mAdapter.addAll(data);
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

//    protected void initRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    private void getBannerDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_BANNER, new BaseCallBack<BannerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, BannerResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<BannerInfo> data = result.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                initTopBanner(data);
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

    private void initTopBanner(final List<BannerInfo> data) {
        List mUrlList = new ArrayList<>();
        List<String> titleas = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            BannerInfo bannerInfo = data.get(i);
            if (bannerInfo == null) {
                continue;
            }

            mUrlList.add(bannerInfo.getPicUrl());
            titleas.add(bannerInfo.getTitle());
        }

        if (mBanner != null) {
            mBanner.setViewUrls(mContext, mUrlList, titleas);
            mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    BannerInfo bannerInfo = data.get(position);
                    if (bannerInfo != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("newsId", bannerInfo.getNewsId());
                        AppManager.getInstance().jumpActivity(getContext(), HomeIndustryInformationDetailActivity.class, bundle);
                    }
                }
            });
        }
    }
}
