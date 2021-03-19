package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.MineOrderInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 21:08
 * @Description : 通用的列表fragment
 */
public class CommonListFragment extends BaseRecyclerFragment {

    private int mType;

    public static CommonListFragment newInstance(int type) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.COMMON_TYPE, type);
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.FEED_TYPE);
        }
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_common_list_view,container,false);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        getOrderData();
    }

    @Override
    protected void initListener() {

    }


    /**
     * 获取订单数据
     */
    private void getOrderData() {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("listType",mType);
        requestParam.put("pageNum",1);
        requestParam.put("pageSize",30);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_FEED_ORDER_LIST, new BaseCallBack<MineOrderInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, MineOrderInfoResult result) {
                if (result != null && result.getCode() == 1) {

                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "接口异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);

    }

}
