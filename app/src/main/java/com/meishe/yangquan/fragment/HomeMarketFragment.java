package com.meishe.yangquan.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishMarketActivity;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.view.HorizontalExpandMenu;
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
    private TextView tv_market_content;
    private HorizontalExpandMenu expand_menu;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_market, container, false);
        mSellLittleSheep = view.findViewById(R.id.btn_sell_little_sheep);
        mBuyLittleSheep = view.findViewById(R.id.btn_buy_little_sheep);
        mSellBigSheep = view.findViewById(R.id.btn_sell_big_sheep);
        mBuyBigSheep = view.findViewById(R.id.btn_buy_big_sheep);
        mRecyclerView = view.findViewById(R.id.recycler);
        mIvPublishMarket = view.findViewById(R.id.iv_common_publish);
        tv_market_content = view.findViewById(R.id.tv_market_content);
        expand_menu = view.findViewById(R.id.expand_menu);
        tv_market_content.setSelected(true);
        tv_market_content.setAlpha(0);
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
                            tv_market_content.setAlpha(animatedValue);
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
                            tv_market_content.setAlpha(animatedValue);
                        }
                    });
                    objectAnimator.start();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        HomeContentFragment contentFragment = HomeContentFragment.newInstance(mMarketType, TAB_TYPE_MARKET);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, contentFragment).commit();
        if (mMarketType == Constants.TYPE_MARKET_SELL_LITTLE_SHEEP) {
            selectSellLittleSheep();
        } else if (mMarketType == Constants.TYPE_MARKET_BUY_LITTLE_SHEEP) {
            selectBuyLittleSheep();
        } else if (mMarketType == Constants.TYPE_MARKET_SELL_BIG_SHEEP) {
            selectSellBigSheep();
        } else if (mMarketType == Constants.TYPE_MARKET_BUY_BIG_SHEEP) {
            selectBuyBigSheep();
        }
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
        mHomeContentFragment = HomeContentFragment.newInstance(mMarketType, TAB_TYPE_MARKET);
        fragmentTransaction.replace(R.id.container, mHomeContentFragment).commit();
    }

    private void selectSellLittleSheep() {
        mSellLittleSheep.setSelected(true);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(false);
        tv_market_content.setText(mSellLittleSheep.getText());
    }

    private void selectBuyLittleSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(true);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(false);
        tv_market_content.setText(mBuyLittleSheep.getText());
    }

    private void selectSellBigSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(true);
        mBuyBigSheep.setSelected(false);
        tv_market_content.setText(mSellBigSheep.getText());
    }

    private void selectBuyBigSheep() {
        mSellLittleSheep.setSelected(false);
        mBuyLittleSheep.setSelected(false);
        mSellBigSheep.setSelected(false);
        mBuyBigSheep.setSelected(true);
        tv_market_content.setText(mBuyBigSheep.getText());
    }

}
