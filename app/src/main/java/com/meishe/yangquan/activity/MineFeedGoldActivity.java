package com.meishe.yangquan.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.meishe.libbase.SlidingTabLayout;
import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.CommonFragmentAdapter;
import com.meishe.yangquan.bean.MineFeedGoldResult;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.MineMyFocusInfoResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.fragment.BUHomeGoodsRefundFragment;
import com.meishe.yangquan.fragment.CommonListFragment;
import com.meishe.yangquan.fragment.MineFeedGoldFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 饲料金页面
 */
public class MineFeedGoldActivity extends BaseActivity {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private TextView tv_gold_count;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    @Override
    protected int initRootView() {
        return R.layout.activity_feed_gold;
    }

    @Override
    public void initView() {
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        tv_gold_count = findViewById(R.id.tv_gold_count);


        mSlidingTabLayout = findViewById(R.id.slidingTabLayout);
        mViewPager = findViewById(R.id.vp_pager);

    }

    @Override
    public void initData() {
        getFeedGoldData();

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("收入");
        mTitleList.add("支出");
//        mFragmentList.add(MineFeedGoldFragment.onInstance(Constants.TYPE_FEED_GOLD_IN_TYPE));
//        mFragmentList.add(MineFeedGoldFragment.onInstance(Constants.TYPE_FEED_GOLD_OUT_TYPE));
        mFragmentList.add(CommonListFragment.
                newInstance(true,Constants.TYPE_COMMON_FEED_GOLD_TYPE,Constants.TYPE_FEED_GOLD_IN_TYPE));
        mFragmentList.add(CommonListFragment.
                newInstance(true,Constants.TYPE_COMMON_FEED_GOLD_TYPE,Constants.TYPE_FEED_GOLD_OUT_TYPE));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new CommonFragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        mSlidingTabLayout.setViewPager(mViewPager);

    }


    @Override
    public void initTitle() {
        mTvTitle.setText("饲料金");
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

    /**
     * 获取饲料金
     */
    private void getFeedGoldData() {

        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FEED_GOLD, new BaseCallBack<MineFeedGoldResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, MineFeedGoldResult result) {
                if (result == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (result.getCode() != 1) {
                    ToastUtil.showToast(result.getMsg());
                    return;
                }
                tv_gold_count.setText(result.getData()+"");

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

    @Override
    public void release() {

    }

    @Override
    public void onClick(View view) {

    }

}
