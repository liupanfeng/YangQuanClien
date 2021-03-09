package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.FeedShoppingInfoResult;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ScreenUtils;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 饲料-玉米
 * @date 2020/12/21 14:36
 */
public class FeedCornFragment extends BaseRecyclerFragment{

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_feed_corn, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mAdapter = new MultiFunctionAdapter(mContext, mRecyclerView);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mAdapter, ScreenUtils.dip2px(mContext,7),mContext,LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        List<FeedShoppingInfo>  datas=new ArrayList<>();
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//        datas.add(new FeedShoppingInfo());
//
//        mAdapter.addAll(datas);



    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getShoppingData();
    }

    /**
     * 获取店铺列表信息
     */
    public void getShoppingData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mainCategory","玉米");
        param.put("pageNum",1);
        param.put("pageSize",30);
        String token = getToken();
        showLoading();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_SHOP_LIST, new BaseCallBack<FeedShoppingInfoResult>() {
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
            protected void onSuccess(Call call, Response response, FeedShoppingInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<FeedShoppingInfo> data = result.getData();
                mAdapter.addAll(data);
            }


            @Override
            protected void onResponse(Response response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

}
