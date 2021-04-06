package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.MineMyFocusInfoResult;
import com.meishe.yangquan.bean.SheepBarInfoResult;
import com.meishe.yangquan.bean.SheepBarMessageInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liupanfeng
 * @desc 我的-我的关注-我的关注
 * @date 2020/12/11 11:28
 *
 * 这个🈶️CommonListFragment替代
 */
@Deprecated
public class MineMyFocusFragment extends BaseRecyclerFragment {


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.mine_my_focus_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLoading = view.findViewById(R.id.loading);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        initRecyclerView();
        return view;
    }


    @Override
    protected void initListener() {
        /*下拉刷新*/
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                mIsLoadFinish = true;
                mPageNum = 1;
                getFocusData();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mIsLoadFinish = true;
                getFocusData();
            }
        });

    }

    @Override
    protected void initData() {
        getFocusData();
    }

    /**
     * 获取关注列表
     */
    private void getFocusData() {

        if (!mIsLoadFinish) {
            return;
        }
        mIsLoadFinish = false;

        String token = getToken();
        if (Util.checkNull(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", mPageNum);
        param.put("pageSize", mPageSize);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FOCUS_LIST, new BaseCallBack<MineMyFocusInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, MineMyFocusInfoResult result) {
                hideUIState();
                if (result == null) {
                    ToastUtil.showToast(response.message());
                    return;
                }
                if (result.getCode() != 1) {
                    ToastUtil.showToast(result.getMsg());
                    return;
                }

                List<MineMyFocusInfo> data = result.getData();
                if (CommonUtils.isEmpty(data)) {
                    return;
                }

                /*页码增加*/
                mPageNum++;
                mList.addAll(mList.size(), data);
                mAdapter.addAll(mList);
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
