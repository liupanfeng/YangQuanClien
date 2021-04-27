package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUManagerCommentInfo;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUGoodsInfoResult;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.ShowBigPictureView;
import com.meishe.yangquan.utils.CommonUtils;
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
 * @Description : 商版-商品平价管理 子碎片
 */
public class BUHomeGoodsCommentFragment extends BaseRecyclerFragment {

    /*类型 0 好评 1 中评 2 差评  这里是接口定义的不能随便修改*/
    private int mType;
    private IosDialog mIosDialog;

    public static BUHomeGoodsCommentFragment onInstance(int type) {
        BUHomeGoodsCommentFragment buHomeGoodsFragment = new BUHomeGoodsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_COMMENT_STATE_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_COMMENT_STATE_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home_goods_comment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {
        List<BUManagerCommentInfo> datas=new ArrayList<>();

//        BUManagerCommentInfo buOrderInfo=new BUManagerCommentInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);
//
//        buOrderInfo=new BUManagerCommentInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);
//
//        buOrderInfo = new BUManagerCommentInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);
//
//        mAdapter.addAll(datas);

    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }



    /**
     * 获取评论列表
     * 0 好评
     * 1中评
     * 2差评
     */
    private void getCommentDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", 1);
        param.put("pageSize", 300);
        param.put("listType", 300);
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
//        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_ORDER_EVALUATION_LIST, new BaseCallBack<BUGoodsInfoResult>() {
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

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUGoodsInfo) {
                    int isPublic = ((BUGoodsInfo) baseInfo).getIsPublic();
                    if (view.getId() == R.id.btn_bu_edit_goods) {
                        //编辑商品
                    } else if (view.getId() == R.id.btn_bu_shelves_goods) {
                        //上架商品 或者下架商品  根据商品是否已经上架
                        if (isPublic == 0) {
                            //上架
                        } else if ((isPublic == 1)) {
                            //下架
                        }
                    } else if (view.getId() == R.id.btn_bu_delete_goods) {
                        if (isPublic == 0) {
                            //删除

                            int id = ((BUGoodsInfo) baseInfo).getId();
                        } else if ((isPublic == 1)) {
                            //分享
                        }
                    } else if (view.getId() == R.id.riv_bu_goods_cover) {
                        //查看大图
                        List<String> goodsImageUrls = ((BUGoodsInfo) baseInfo).getGoodsImageUrls();
                        if (CommonUtils.isEmpty(goodsImageUrls)) {
                            return;
                        }
                        String filePath = goodsImageUrls.get(0);
                        ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, filePath);
                        if (showBigPictureView != null && showBigPictureView.isDismiss()) {
                            showBigPictureView.show();
                        }
                    }
                }
            }
        });
    }


}
