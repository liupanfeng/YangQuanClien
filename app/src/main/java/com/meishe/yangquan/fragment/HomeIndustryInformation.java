package com.meishe.yangquan.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BannerInfo;
import com.meishe.yangquan.bean.BannerResult;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.IndustryResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
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
public class HomeIndustryInformation extends BaseRecyclerFragment{

    private BannerLayout mBanner;
    private MultiFunctionAdapter mAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_home_industry_information,container,false);
        mBanner = view.findViewById(R.id.banner);
        mRecyclerView = view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initRecyclerView();
//        getBannerDataFromServer();
        initTopBanner(null);
        getIndustryInfoFromServer();
    }

    private void getIndustryInfoFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        String token = UserManager.getInstance(mContext).getToken();
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
//                initTopBanner(data);
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

    private void initTopBanner(List<BannerInfo> data) {
        List mUrlList = new ArrayList<>();
        List<String> titleas = new ArrayList<>();
//        for (int i = 0; i < data.size(); i++) {
//            BannerInfo bannerInfo = data.get(i);
//            if (bannerInfo==null){
//                continue;
//            }
//
//            mUrlList.add(bannerInfo.getPicUrl());
//            titleas.add(bannerInfo.getTitle());
//        }

        mUrlList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1423774393,3301272101&fm=26&gp=0.jpg");
        mUrlList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1764774024,3138989852&fm=26&gp=0.jpg");
        mUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606555763664&di=6a180735dfa87a6be39a67ddbb47f4a6&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20140408%2F20140408130734-1854992764.jpg");
        mUrlList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3811056552,313222880&fm=26&gp=0.jpg");

        titleas.add("标题一");
        titleas.add("标题二");
        titleas.add("标题三");
        titleas.add("标题四");

        if (mBanner != null) {
            mBanner.setViewUrls(mContext, mUrlList, titleas);
            mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
