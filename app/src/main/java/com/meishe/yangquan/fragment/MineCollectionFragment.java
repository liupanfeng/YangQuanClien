package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineCollectionInfo;
import com.meishe.yangquan.bean.MineCollectionInfoResult;
import com.meishe.yangquan.bean.MineFeedGoldInfo;
import com.meishe.yangquan.bean.MineFeedGoldInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 15:32
 * @Description : 我的-收藏
 */
@Deprecated
public class MineCollectionFragment extends BaseRecyclerFragment {

    /*收藏状态 1 店铺  2 商品  */
    private int mType;

    public static MineCollectionFragment onInstance(int type) {
        MineCollectionFragment buHomeGoodsFragment = new MineCollectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_MINE_COLLECTION_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_MINE_COLLECTION_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine_collection, container, false);
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
        List<MineCollectionInfo> data=new ArrayList<>();

        MineCollectionInfo collectionInfo=new MineCollectionInfo();
        collectionInfo.setType(mType);
        data.add(collectionInfo);

        collectionInfo=new MineCollectionInfo();
        collectionInfo.setType(mType);
        data.add(collectionInfo);

        collectionInfo=new MineCollectionInfo();
        collectionInfo.setType(mType);
        data.add(collectionInfo);

        collectionInfo=new MineCollectionInfo();
        collectionInfo.setType(mType);
        data.add(collectionInfo);

        mAdapter.addAll(data);
//        getCollectionDataFromServer();
    }


    @Override
    protected void initListener() {

    }


    /**
     * 获取收藏数据列表
     */
    private void getCollectionDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
//        param.put("pageNum", 1);
//        param.put("pageSize", 5000);
//        if (mType==Constants.TYPE_FEED_GOLD_IN_TYPE){
//            param.put("type", "in");
//        }else if (mType==Constants.TYPE_FEED_GOLD_OUT_TYPE){
//            param.put("type", "out");
//        }
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FEED_GOLD_LIST, new BaseCallBack<MineCollectionInfoResult>() {
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
            protected void onSuccess(Call call, Response response, MineCollectionInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<MineCollectionInfo> data = result.getData();
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
