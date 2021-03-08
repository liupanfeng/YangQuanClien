package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUGoodsInfoResult;
import com.meishe.yangquan.bean.BUManagerRefundInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.IosDialog;


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
 * @Description : 商版-商品退货管理 子碎片
 */
public class BUHomeGoodsRefundFragment extends BaseRecyclerFragment {

    /*退货状态 1 进行中 2 已经完成  */
    private int mType;
    private IosDialog mIosDialog;

    public static BUHomeGoodsRefundFragment onInstance(int type) {
        BUHomeGoodsRefundFragment buHomeGoodsFragment = new BUHomeGoodsRefundFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_REFUND_STATE_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_REFUND_STATE_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home_goods_refund, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        List<BUManagerRefundInfo> datas = new ArrayList<>();

        BUManagerRefundInfo buOrderInfo = new BUManagerRefundInfo();
        buOrderInfo.setState(mType);
        datas.add(buOrderInfo);

        buOrderInfo = new BUManagerRefundInfo();
        buOrderInfo.setState(mType);
        datas.add(buOrderInfo);

        buOrderInfo = new BUManagerRefundInfo();
        buOrderInfo.setState(mType);
        datas.add(buOrderInfo);

        mAdapter.addAll(datas);

    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }


    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (view.getId() == R.id.btn_bu_refund && baseInfo instanceof BUManagerRefundInfo) {
                    //同意退货
                    showInputIncomingDialog(0);
                }
            }
        });
    }


    /**
     *提示框
     * @param id
     */
    private void showInputIncomingDialog(final int id) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("退货原因:\n");
        stringBuilder.append("退款人:\n");
        stringBuilder.append("退货电话:\n");
        stringBuilder.append("单号:");

        final IosDialog.DialogBuilder builder = new IosDialog.DialogBuilder(mContext);
        builder.setTitle("确认信息");
        builder.setText(stringBuilder.toString());
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                mIosDialog.hide();

            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
            }
        });
        mIosDialog = builder.create();
        mIosDialog. setContentViewLineSpace(1.2f);
        mIosDialog.setContentViewLayoutGravity(Gravity.START);
        mIosDialog.show();
    }



    /**
     * 获取退货数据列表
     */
    private void getGoodsRefundDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", 1);
        param.put("pageSize", 300);
        if (mType == 2) {
            param.put("isPublic", 0);
        } else if (mType == 1) {
            param.put("isPublic", 1);
        }
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
//        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_GOODS_INFO, new BaseCallBack<BUGoodsInfoResult>() {
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
            protected void onSuccess(Call call, Response response, BUGoodsInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<BUGoodsInfo> data = result.getData();
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
