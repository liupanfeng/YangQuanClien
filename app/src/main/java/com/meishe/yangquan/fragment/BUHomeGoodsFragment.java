package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.MultiFunctionAdapter;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUGoodsInfoResult;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.bean.QuotationResult;
import com.meishe.yangquan.divider.RecycleViewDivider;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ScreenUtils;
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
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 15:32
 * @Description : 商版-商品管理子碎片
 */
public class BUHomeGoodsFragment extends BaseRecyclerFragment{

    /*发布状态 1已经发布  2等待发布*/
    private int mType;

    public static BUHomeGoodsFragment onInstance(int type){
        BUHomeGoodsFragment buHomeGoodsFragment=new BUHomeGoodsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.KEY_PUBLISH_GOODS_TYPE,type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!=null){
            mType=arguments.getInt(Constants.KEY_PUBLISH_GOODS_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home_goods, container, false);
        mRecyclerView=view.findViewById(R.id.recycler);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
//        getGoodsDataFromServer();
        List<BUGoodsInfo> data=new ArrayList<>();
        if (mType==Constants.TYPE_ALREADY_PUBLISH_GOODS_TYPE){
            data.add(new BUGoodsInfo());
            data.add(new BUGoodsInfo());
            data.add(new BUGoodsInfo());
            data.add(new BUGoodsInfo());
        }else if (mType==Constants.TYPE_KEY_WAIT_PUBLISH_GOODS_TYPE){
            data.add(new BUGoodsInfo());
            data.add(new BUGoodsInfo());
        }
        mAdapter.addAll(data);
    }

    private void getGoodsDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", 1);
        param.put("pageSize", 300);
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)){
            return;
        }
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_GOODS_INFO, new BaseCallBack<BUGoodsInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                mLoading.hide();
            }

            @Override
            protected void onSuccess(Call call, Response response, BUGoodsInfoResult result) {
                mLoading.hide();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<BUGoodsInfo> data = result.getData();
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

    @Override
    protected void initListener() {

    }
}
