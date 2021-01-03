package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishMarketActivity;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.MarketResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.CustomButton;
import com.meishe.yangquan.wiget.CustomTextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 主页-市场页面
 * @date 2020/11/26 10:42
 */
public class HomeMarketFragment extends BaseRecyclerFragment implements View.OnClickListener {

    /*出售羊苗*/
    private static final int TYPE_MARKET_SELL_LITTLE_SHEEP = 9;
    /*购买羊苗*/
    private static final int TYPE_MARKET_BUY_LITTLE_SHEEP = 10;
    /*出售成品羊*/
    private static final int TYPE_MARKET_SELL_BIG_SHEEP = 11;
    /*收购成品羊*/
    private static final int TYPE_MARKET_BUY_BIG_SHEEP = 12;

    /*最新*/
    private static final int TYPE_MARKET_LIST_TYPE_NEWEST = 1;
    /*推荐*/
    private static final int TYPE_MARKET_LIST_TYPE_RECOMMEND = 2;


    private CustomButton mSellLittleSheep;
    private CustomButton mBuyLittleSheep;
    private CustomButton mSellBigSheep;
    private CustomButton mBuyBigSheep;
    private CustomTextView mTvMarketNewest;
    private CustomTextView mTvMarketCommand;

    private int mListType = TYPE_MARKET_LIST_TYPE_NEWEST;
    private int mMarketType = TYPE_MARKET_SELL_LITTLE_SHEEP;
    private ImageView mIvPublishMarket;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_market, container, false);
        mSellLittleSheep = view.findViewById(R.id.btn_sell_little_sheep);
        mBuyLittleSheep = view.findViewById(R.id.btn_buy_little_sheep);
        mSellBigSheep = view.findViewById(R.id.btn_sell_big_sheep);
        mBuyBigSheep = view.findViewById(R.id.btn_buy_big_sheep);
        mRecyclerView = view.findViewById(R.id.recycler);
        mTvMarketNewest = view.findViewById(R.id.tv_market_newest);
        mTvMarketCommand = view.findViewById(R.id.tv_market_command);
        mIvPublishMarket = view.findViewById(R.id.iv_publish_market);
        return view;
    }

    @Override
    protected void initListener() {
        mSellLittleSheep.setOnClickListener(this);
        mBuyLittleSheep.setOnClickListener(this);
        mSellBigSheep.setOnClickListener(this);
        mBuyBigSheep.setOnClickListener(this);
        mTvMarketNewest.setOnClickListener(this);
        mTvMarketCommand.setOnClickListener(this);
        mIvPublishMarket.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTvMarketNewest.setSelected(true);
        initRecyclerView();
        selectSellLittleSheep();
        getMarketDataFromServer(TYPE_MARKET_SELL_LITTLE_SHEEP, TYPE_MARKET_LIST_TYPE_NEWEST);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sell_little_sheep:
                mMarketType = TYPE_MARKET_SELL_LITTLE_SHEEP;
                selectSellLittleSheep();
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.btn_buy_little_sheep:
                mMarketType = TYPE_MARKET_BUY_LITTLE_SHEEP;
                selectBuyLittleSheep();
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.btn_sell_big_sheep:
                mMarketType = TYPE_MARKET_SELL_BIG_SHEEP;
                selectSellBigSheep();
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.btn_buy_big_sheep:
                mMarketType = TYPE_MARKET_BUY_BIG_SHEEP;
                selectBuyBigSheep();
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.tv_market_newest:
                mListType = TYPE_MARKET_LIST_TYPE_NEWEST;
                mTvMarketNewest.setSelected(true);
                mTvMarketCommand.setSelected(false);
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.tv_market_command:
                mListType = TYPE_MARKET_LIST_TYPE_RECOMMEND;
                mTvMarketCommand.setSelected(true);
                mTvMarketNewest.setSelected(false);
                getMarketDataFromServer(mMarketType, mListType);
                break;
            case R.id.iv_publish_market:
                Bundle bundle = new Bundle();
                bundle.putInt("market_type", mMarketType);
                AppManager.getInstance().jumpActivity(getActivity(), PublishMarketActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    private void selectSellLittleSheep() {
        mSellLittleSheep.setSelected(true);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(false);
    }

    private void selectBuyLittleSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(true);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(false);
    }

    private void selectSellBigSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(true);
        mBuyBigSheep.setSelected(false);
    }

    private void selectBuyBigSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(true);
    }


    /**
     * 获取市场数据
     */
    private void getMarketDataFromServer(int typeId, int listType) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageNum", 1);
        param.put("pageSize", 30);
        String token = UserManager.getInstance(mContext).getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_MARKET, new BaseCallBack<MarketResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
//                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, MarketResult result) {
//                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<MarketInfo> data = result.getData();
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

}
