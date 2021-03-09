package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineFeedGoldInfo;
import com.meishe.yangquan.bean.MineFeedGoldInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 15:32
 * @Description : 我的-饲料金
 */
public class MineFeedGoldFragment extends BaseRecyclerFragment {

    /*饲料金状态 1 收入 2 支出  */
    private int mType;

    public static MineFeedGoldFragment onInstance(int type) {
        MineFeedGoldFragment buHomeGoodsFragment = new MineFeedGoldFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_FEED_GOLD_STATE_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_FEED_GOLD_STATE_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine_feed_gold, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getGoodsRefundDataFromServer();
    }


    @Override
    protected void initListener() {

    }


    /**
     * 获取饲料金数据列表
     */
    private void getGoodsRefundDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", 1);
        param.put("pageSize", 5000);
        if (mType==Constants.TYPE_FEED_GOLD_IN_TYPE){
            param.put("type", "in");
        }else if (mType==Constants.TYPE_FEED_GOLD_OUT_TYPE){
            param.put("type", "out");
        }
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FEED_GOLD_LIST, new BaseCallBack<MineFeedGoldInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MineFeedGoldInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<MineFeedGoldInfo> data = result.getData();
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
