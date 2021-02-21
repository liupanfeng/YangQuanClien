package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.HomeIndustryInformationDetailActivity;
import com.meishe.yangquan.activity.HomeQuotationHistoryActivity;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BannerInfo;
import com.meishe.yangquan.bean.BannerResult;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.IndustryInfo;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.bean.QuotationResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.FormatDateUtil;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.view.BannerLayout;
import com.meishe.yangquan.wiget.CustomButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 主页-行情页面
 * @date 2020/11/26 10:41
 */
public class HomeQuotationFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private BannerLayout mBanner;
    private List<String> mUrlList;
    private CustomButton mLittleSheep;
    private CustomButton mBigSheep;
    private CustomButton mDieSheep;
    private CustomButton mForageGrass;

    private TextView mTvTodayQuotation;

    private int mType;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_quotation, container, false);
        mBanner = view.findViewById(R.id.banner);

        mLittleSheep = view.findViewById(R.id.btn_little_sheep);
        mBigSheep = view.findViewById(R.id.btn_big_sheep);
        mDieSheep = view.findViewById(R.id.btn_die_sheep);
        mForageGrass = view.findViewById(R.id.btn_forage_grass);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        mTvTodayQuotation = view.findViewById(R.id.tv_today_quotation);

        return view;
    }

    @Override
    protected void initListener() {
        mLittleSheep.setOnClickListener(this);
        mBigSheep.setOnClickListener(this);
        mDieSheep.setOnClickListener(this);
        mForageGrass.setOnClickListener(this);

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof QuotationInfo) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.QUOTATION_ID, ((QuotationInfo) baseInfo).getId());
                    bundle.putInt(Constants.TYPE_QUOTATION, mType);
                    AppManager.getInstance().jumpActivity(getActivity(), HomeQuotationHistoryActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mTvTodayQuotation.setText(String.format("今日行情 %s", FormatDateUtil.dateToString(new Date(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY)));
        initRecyclerView();
        selectLittleSheep();
        getBannerDataFromServer();
//        initTopBanner(null);
        getQuotationDataFromServer(5);
        mType = 5;
    }

    private void getBannerDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_BANNER, new BaseCallBack<BannerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, BannerResult result) {
                mLoading.hide();
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

    /**
     * 获取行情数据
     */
    private void getQuotationDataFromServer(int typeId) {
//        mLoading.show();
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("pageNum", 1);
        param.put("pageSize", 30);
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)){
            return;
        }
        mAdapter.addAll(null);
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_QUOTATION, new BaseCallBack<QuotationResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, QuotationResult result) {
                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<QuotationInfo> data = result.getData();
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


    private void initTopBanner(final List<BannerInfo> data) {
        mUrlList = new ArrayList<>();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_little_sheep:
                selectLittleSheep();
                mType = 5;
                getQuotationDataFromServer(5);
                break;
            case R.id.btn_big_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(true);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(false);
                mType = 6;
                getQuotationDataFromServer(6);
                break;
            case R.id.btn_die_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(true);
                mForageGrass.setSelected(false);
                mType = 7;
                getQuotationDataFromServer(7);
                break;
            case R.id.btn_forage_grass:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(true);
                mType = 8;
                getQuotationDataFromServer(8);
                break;
            default:
                break;
        }
    }

    private void selectLittleSheep() {
        mLittleSheep.setSelected(true);
        mBigSheep.setSelected(false);
        mDieSheep.setSelected(false);
        mForageGrass.setSelected(false);
    }
}
