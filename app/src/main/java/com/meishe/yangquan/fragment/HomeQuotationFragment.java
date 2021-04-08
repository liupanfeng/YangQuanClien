package com.meishe.yangquan.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.meishe.yangquan.view.HorizontalExpandMenu;
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

    private TextView tv_quotation_content;
    private HorizontalExpandMenu expand_menu;
    private CommonListFragment commonListFragment_5;
    private CommonListFragment commonListFragment_6;
    private CommonListFragment commonListFragment_7;
    private CommonListFragment commonListFragment_8;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_quotation, container, false);
        mBanner = view.findViewById(R.id.banner);

        mLittleSheep = view.findViewById(R.id.btn_little_sheep);
        mBigSheep = view.findViewById(R.id.btn_big_sheep);
        mDieSheep = view.findViewById(R.id.btn_die_sheep);
        mForageGrass = view.findViewById(R.id.btn_forage_grass);
        mTvTodayQuotation = view.findViewById(R.id.tv_today_quotation);
        expand_menu = view.findViewById(R.id.expand_menu);
        tv_quotation_content = view.findViewById(R.id.tv_quotation_content);
        tv_quotation_content.setAlpha(0);
        tv_quotation_content.setSelected(true);
        return view;
    }

    @Override
    protected void initListener() {
        mLittleSheep.setOnClickListener(this);
        mBigSheep.setOnClickListener(this);
        mDieSheep.setOnClickListener(this);
        mForageGrass.setOnClickListener(this);

        expand_menu.setOnExpandMenuListener(new HorizontalExpandMenu.OnExpandMenuListener() {
            @Override
            public void onExpand(boolean isExpand, int time) {
                if (isExpand) {
                    ValueAnimator objectAnimator = ObjectAnimator.ofFloat(1f, 0f);
                    objectAnimator.setDuration(time);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            tv_quotation_content.setAlpha(animatedValue);
                        }
                    });
                    objectAnimator.start();
                } else {
                    ValueAnimator objectAnimator = ObjectAnimator.ofFloat(0f, 1f);
                    objectAnimator.setDuration(time);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            tv_quotation_content.setAlpha(animatedValue);
                        }
                    });
                    objectAnimator.start();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mTvTodayQuotation.setText(String.format("今日行情 %s", FormatDateUtil.dateToString(new Date(), FormatDateUtil.FORMAT_TYPE_YEAR_MONTH_DAY)));
        getBannerDataFromServer();
        commonListFragment_5 = CommonListFragment.newInstance(false, Constants.TYPE_COMMON_QUOTATION, 5);
        commonListFragment_6 = CommonListFragment.newInstance(false, Constants.TYPE_COMMON_QUOTATION, 6);
        commonListFragment_7 = CommonListFragment.newInstance(false, Constants.TYPE_COMMON_QUOTATION, 7);
        commonListFragment_8 = CommonListFragment.newInstance(false, Constants.TYPE_COMMON_QUOTATION, 8);
        mLittleSheep.post(new Runnable() {
            @Override
            public void run() {
                mLittleSheep.performClick();
            }
        });
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
            }

            @Override
            protected void onSuccess(Call call, Response response, BannerResult result) {
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
                replaceFragment(commonListFragment_5);
                break;
            case R.id.btn_big_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(true);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(false);
                tv_quotation_content.setText(mBigSheep.getText());
                replaceFragment(commonListFragment_6);
                break;
            case R.id.btn_die_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(true);
                mForageGrass.setSelected(false);
                tv_quotation_content.setText(mDieSheep.getText());
                replaceFragment(commonListFragment_7);
                break;
            case R.id.btn_forage_grass:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(true);
                tv_quotation_content.setText(mForageGrass.getText());
                replaceFragment(commonListFragment_8);
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
        tv_quotation_content.setText(mLittleSheep.getText());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
