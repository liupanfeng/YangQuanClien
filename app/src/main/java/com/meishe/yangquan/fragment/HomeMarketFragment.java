package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishMarketActivity;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.wiget.CustomButton;

import static com.meishe.yangquan.utils.Constants.TAB_TYPE_MARKET;


/**
 * @author liupanfeng
 * @desc 主页-市场页面
 * @date 2020/11/26 10:42
 */
public class HomeMarketFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private CustomButton mSellLittleSheep;
    private CustomButton mBuyLittleSheep;
    private CustomButton mSellBigSheep;
    private CustomButton mBuyBigSheep;

    private int mMarketType = Constants.TYPE_MARKET_SELL_LITTLE_SHEEP;
    private View mIvPublishMarket;
    private HomeContentFragment mHomeContentFragment;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_market, container, false);
        mSellLittleSheep = view.findViewById(R.id.btn_sell_little_sheep);
        mBuyLittleSheep = view.findViewById(R.id.btn_buy_little_sheep);
        mSellBigSheep = view.findViewById(R.id.btn_sell_big_sheep);
        mBuyBigSheep = view.findViewById(R.id.btn_buy_big_sheep);
        mRecyclerView = view.findViewById(R.id.recycler);
        mIvPublishMarket = view.findViewById(R.id.iv_common_publish);
        return view;
    }

    @Override
    protected void initListener() {
        mSellLittleSheep.setOnClickListener(this);
        mBuyLittleSheep.setOnClickListener(this);
        mSellBigSheep.setOnClickListener(this);
        mBuyBigSheep.setOnClickListener(this);
        mIvPublishMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.MARKET_TYPE, mMarketType);
                AppManager.getInstance().jumpActivity(getActivity(), PublishMarketActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {

        HomeContentFragment contentFragment = HomeContentFragment.newInstance(mMarketType,TAB_TYPE_MARKET);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, contentFragment).commit();
        selectSellLittleSheep();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sell_little_sheep:
                mMarketType = Constants.TYPE_MARKET_SELL_LITTLE_SHEEP;
                selectSellLittleSheep();
                break;
            case R.id.btn_buy_little_sheep:
                mMarketType = Constants.TYPE_MARKET_BUY_LITTLE_SHEEP;
                selectBuyLittleSheep();
                break;
            case R.id.btn_sell_big_sheep:
                mMarketType = Constants.TYPE_MARKET_SELL_BIG_SHEEP;
                selectSellBigSheep();
                break;
            case R.id.btn_buy_big_sheep:
                mMarketType = Constants.TYPE_MARKET_BUY_BIG_SHEEP;
                selectBuyBigSheep();
                break;
            default:
                break;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeContentFragment = HomeContentFragment.newInstance(mMarketType,TAB_TYPE_MARKET);
        fragmentTransaction.replace(R.id.container, mHomeContentFragment).commit();
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

}
